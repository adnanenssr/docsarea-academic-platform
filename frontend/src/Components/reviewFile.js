import { Box, Stack, Typography, Paper, Button, Dialog, DialogTitle, DialogContent, TextField, DialogActions, CircularProgress, Container } from '@mui/material'
import React, { useEffect, useState } from 'react'
import PdfViewer from './PdfViewer'
import ModernActionIcons from "./Icons"
import PaperDetails from './paperDetails'
import { useParams } from 'react-router-dom'
import { Check, X } from 'lucide-react'

function ReviewFile() {
  const { groupId, fileId } = useParams()
  const [fileData, setFileData] = useState(null)
  const [theme, setTheme] = useState(null)
  const [comment, setComment] = useState("")
  const [open, setOpen] = useState(false)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const getFileDetails = async (fileId) => {
    try {
      const response = await fetch(`http://localhost:8082/file/details/${fileId}`, {
        credentials: "include"
      })
      const data = await response.json()
      return data
    } catch (err) {
      setError(err.message)
      return null
    }
  }

  useEffect(() => {
    if (fileId) {
      getFileDetails(fileId).then(data => {
        if (data) {
          setFileData(data)
        }
      })
    }
  }, [fileId])

  const onSubmit = async (status) => {
    setLoading(true)
    setError(null)
    try {
      const response = await fetch(`http://localhost:8082/${groupId}/review/${fileId}`, {
        method: "PUT",
        credentials: "include",
        headers: {
          "content-type": "application/json"
        },
        body: JSON.stringify({
          "comment": comment,
          "status": status
        })
      })
      if (!response.ok) {
        throw new Error("Could not submit the review, please try again later")
      }
      setComment("")
      setOpen(false)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Box sx={{ width: '100%', bgcolor: '#ffffff', minHeight: '100vh', py: 4 }}>
      <Container maxWidth="lg">
        {/* Error State */}
        {error && (
          <Paper
            sx={{
              p: 2.5,
              mb: 3,
              backgroundColor: '#fef2f2',
              border: '1px solid #fee2e2',
              borderRadius: '8px',
              boxShadow: 'none',
            }}
          >
            <Typography sx={{ color: '#991b1b', fontWeight: 500, fontSize: '0.95rem' }}>
              Error: {error}
            </Typography>
          </Paper>
        )}

        <Box display="flex" gap={4} sx={{ flexDirection: { xs: 'column', lg: 'row' } }}>
          {/* Main Content - Left Side */}
          <Box sx={{ flex: 1, minWidth: 0 }}>
            {/* Paper Details */}
            {fileData && (
              <Paper
                sx={{
                  borderRadius: '8px',
                  marginBottom: 3,
                  backgroundColor: '#ffffff',
                  border: '1px solid #e5e7eb',
                  boxShadow: 'none',
                  p: 0,
                  overflow: 'hidden',
                }}
              >
                <PaperDetails
                  title={fileData.title}
                  description={fileData.description}
                  theme={theme}
                  authors={fileData.authors}
                />
              </Paper>
            )}

            {/* PDF Viewer */}
            <Paper
              sx={{
                borderRadius: '8px',
                backgroundColor: '#ffffff',
                border: '1px solid #e5e7eb',
                boxShadow: 'none',
                overflow: 'hidden',
              }}
            >
              <Box sx={{ p: 3 }}>
                <Box sx={{ mb: 2, pb: 2, borderBottom: '1px solid #e5e7eb' }}>
                  <ModernActionIcons />
                </Box>
                <Box sx={{ background: '#f9fafb', borderRadius: '6px', overflow: 'hidden' }}>
                  <PdfViewer fileId={fileId} />
                </Box>
              </Box>
            </Paper>
          </Box>

          {/* Sidebar - Right Side */}
          <Box
            sx={{
              width: { xs: '100%', lg: '300px' },
              flexShrink: 0,
            }}
          >
            <Paper
              sx={{
                borderRadius: '8px',
                backgroundColor: '#ffffff',
                border: '1px solid #e5e7eb',
                boxShadow: 'none',
                p: 3,
                height: 'fit-content',
                position: { lg: 'sticky' },
                top: { lg: 80 },
              }}
            >
              <Box sx={{ mb: 3 }}>
                <Typography
                  sx={{
                    fontWeight: 700,
                    color: '#1f2937',
                    fontSize: '1rem',
                    mb: 2,
                  }}
                >
                  Review File
                </Typography>

                <Stack spacing={2}>
                  <Button
                    variant="contained"
                    fullWidth
                    startIcon={<Check size={20} />}
                    onClick={() => onSubmit("ACCEPTED")}
                    disabled={loading}
                    sx={{
                      backgroundColor: '#059669',
                      color: 'white',
                      textTransform: 'none',
                      fontWeight: 600,
                      fontSize: '0.9rem',
                      py: 1.2,
                      borderRadius: '6px',
                      transition: 'all 0.2s',
                      '&:hover': {
                        backgroundColor: '#047857',
                      },
                      '&:disabled': {
                        backgroundColor: '#9ca3af',
                      }
                    }}
                  >
                    {loading ? <CircularProgress size={20} sx={{ color: 'white' }} /> : 'Accept'}
                  </Button>

                  <Button
                    variant="contained"
                    fullWidth
                    startIcon={<X size={20} />}
                    onClick={() => setOpen(true)}
                    disabled={loading}
                    sx={{
                      backgroundColor: '#dc2626',
                      color: 'white',
                      textTransform: 'none',
                      fontWeight: 600,
                      fontSize: '0.9rem',
                      py: 1.2,
                      borderRadius: '6px',
                      transition: 'all 0.2s',
                      '&:hover': {
                        backgroundColor: '#b91c1c',
                      },
                      '&:disabled': {
                        backgroundColor: '#9ca3af',
                      }
                    }}
                  >
                    Reject
                  </Button>
                </Stack>
              </Box>
            </Paper>
          </Box>
        </Box>

        {/* Rejection Dialog */}
        <Dialog
          open={open}
          onClose={() => !loading && setOpen(false)}
          maxWidth="sm"
          fullWidth
          PaperProps={{
            sx: {
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)'
            }
          }}
        >
          <DialogTitle sx={{ fontWeight: 700, color: '#1f2937', pt: 3 }}>
            Rejection Reason
          </DialogTitle>
          <DialogContent sx={{ pt: 2 }}>
            <TextField
              fullWidth
              multiline
              rows={4}
              placeholder="Add rejection clarifications..."
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: '6px',
                  fontSize: '0.9rem',
                },
              }}
            />
          </DialogContent>
          <DialogActions sx={{ px: 3, pb: 3, pt: 2 }}>
            <Button
              onClick={() => {
                setComment("")
                setOpen(false)
              }}
              disabled={loading}
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                borderRadius: '6px',
                color: '#6b7280',
                '&:hover': {
                  backgroundColor: '#f9fafb'
                }
              }}
            >
              Cancel
            </Button>
            <Button
              variant="contained"
              onClick={() => onSubmit("REJECTED")}
              disabled={loading}
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                borderRadius: '6px',
                backgroundColor: '#dc2626',
                color: 'white',
                minWidth: 100,
                '&:hover': {
                  backgroundColor: '#b91c1c'
                }
              }}
            >
              {loading ? <CircularProgress size={20} sx={{ color: 'white' }} /> : 'Reject'}
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Box>
  )
}

export default ReviewFile