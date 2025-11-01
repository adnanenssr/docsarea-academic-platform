import {
  Avatar,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Paper,
  Stack,
  Typography,
  Alert
} from '@mui/material'
import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { AlertCircle, CheckCircle2, Users } from 'lucide-react'

function Invitation() {
  const [group, setGroup] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [joining, setJoining] = useState(false)
  const [open, setOpen] = useState(false)
  const { id } = useParams()
  const navigate = useNavigate()

  useEffect(() => {
    const getGroup = async () => {
      setLoading(true)
      setError(null)
      try {
        const response = await fetch(`http://localhost:8082/invitation/${id}`, {
          method: "GET",
          credentials: "include"
        })

        if (!response.ok) {
          throw new Error("Could not find the group!")
        }

        const data = await response.json()
        if (data) {
          setGroup(data)
        }
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    getGroup()
  }, [id])

  const onSubmit = async () => {
    setJoining(true)
    setError(null)
    try {
      const response = await fetch(`http://localhost:8082/invitation/${id}/join`, {
        method: "POST",
        credentials: "include"
      })

      if (!response.ok) {
        throw new Error("Invitation error, please try again later")
      }

      // Navigate to group page after successful join
      // navigate(`/group/${id}`);
    } catch (err) {
      setError(err.message)
    } finally {
      setJoining(false)
    }
  }

  return (
    <Box
      sx={{
        width: '100vw',
        height: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        background: '#ffffff',
        p: 2
      }}
    >
      <Paper
        sx={{
          width: '100%',
          maxWidth: 500,
          borderRadius: '12px',
          border: '1px solid #e5e7eb',
          boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)',
          overflow: 'hidden'
        }}
      >
        {loading ? (
          <Stack
            spacing={3}
            alignItems="center"
            sx={{ py: 8 }}
          >
            <CircularProgress sx={{ color: '#1f2937' }} />
            <Typography color="text.secondary">
              Loading invitation...
            </Typography>
          </Stack>
        ) : error ? (
          <Stack
            spacing={3}
            alignItems="center"
            sx={{ p: 4 }}
          >
            <AlertCircle size={56} color="#991b1b" />
            <Typography variant="h6" sx={{ color: '#991b1b', textAlign: 'center' }}>
              {error}
            </Typography>
            <Button
              variant="outlined"
              onClick={() => window.location.reload()}
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                borderRadius: '6px',
                borderColor: '#d1d5db',
                color: '#6b7280',
                '&:hover': {
                  borderColor: '#9ca3af',
                  backgroundColor: '#f9fafb',
                }
              }}
            >
              Try Again
            </Button>
          </Stack>
        ) : (
          <>
            {/* Header Section */}
            <Box
              sx={{
                background: '#ffffff',
                borderBottom: '1px solid #e5e7eb',
                py: 6,
                px: 4,
                textAlign: 'center'
              }}
            >
              <Avatar
                variant='rounded'
                src={`http://localhost:8082/group/${group?.id}/get/avatar`}
                sx={{
                  width: 100,
                  height: 100,
                  margin: '0 auto',
                  border: '2px solid #e5e7eb',
                  borderRadius: '12px',
                  boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
                  mb: 3,
                  backgroundColor: '#8b8fa8',
                  fontSize: '2.5rem',
                  fontWeight: 700,
                }}
              >
                {group?.name.charAt(0).toUpperCase()}
              </Avatar>
              <Typography
                variant='h5'
                sx={{
                  fontWeight: 700,
                  color: '#1f2937',
                  mb: 1
                }}
              >
                {group?.name}
              </Typography>
              <Typography
                variant="body2"
                sx={{ color: '#6b7280' }}
              >
                You've been invited to join this group
              </Typography>
            </Box>

            {/* Content Section */}
            <Stack spacing={3} sx={{ p: 4 }}>
              {group?.description && (
                <Box
                  sx={{
                    p: 3,
                    background: '#f9fafb',
                    borderRadius: '8px',
                    border: '1px solid #e5e7eb'
                  }}
                >
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{ lineHeight: 1.7, color: '#6b7280' }}
                  >
                    {group.description}
                  </Typography>
                </Box>
              )}

              {group?.theme && (
                <Box>
                  <Typography
                    variant="caption"
                    color="text.secondary"
                    sx={{
                      mb: 1,
                      display: 'block',
                      fontWeight: 600,
                      color: '#6b7280',
                      textTransform: 'uppercase',
                      letterSpacing: '0.05em'
                    }}
                  >
                    Category
                  </Typography>
                  <Box
                    sx={{
                      display: 'inline-block',
                      px: 2.5,
                      py: 0.75,
                      borderRadius: '6px',
                      background: '#f3f4f6',
                      border: '1px solid #e5e7eb',
                      color: '#1f2937',
                      fontWeight: 600,
                      fontSize: '0.9rem'
                    }}
                  >
                    {group.theme}
                  </Box>
                </Box>
              )}

              <Button
                variant='contained'
                size="large"
                fullWidth
                disabled={joining}
                onClick={() => {
                  if (group.strategy !== "TRANSFER_FILES_TO_OWNER") {
                    setOpen(true)
                  } else {
                    onSubmit()
                  }
                }}
                startIcon={joining ? <CircularProgress size={20} color="inherit" /> : <CheckCircle2 size={20} />}
                sx={{
                  py: 1.5,
                  borderRadius: '8px',
                  textTransform: 'none',
                  fontWeight: 600,
                  fontSize: '0.95rem',
                  backgroundColor: '#1f2937',
                  color: 'white',
                  boxShadow: 'none',
                  border: 'none',
                  transition: 'all 0.2s',
                  '&:hover': {
                    backgroundColor: '#374151',
                  },
                  '&:disabled': {
                    backgroundColor: '#9ca3af',
                  }
                }}
              >
                {joining ? 'Joining...' : 'Join Group'}
              </Button>
            </Stack>
          </>
        )}
      </Paper>

      {/* Warning Dialog */}
      <Dialog
        open={open}
        onClose={() => !joining && setOpen(false)}
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
        <DialogTitle sx={{ pb: 1, pt: 3 }}>
          <Stack direction="row" spacing={1.5} alignItems="center">
            <AlertCircle size={24} color="#d97706" />
            <Typography variant="h6" sx={{ fontWeight: 700, color: '#1f2937' }}>
              Important Notice
            </Typography>
          </Stack>
        </DialogTitle>
        <DialogContent sx={{ pt: 2 }}>
          <Alert
            severity="warning"
            sx={{
              mb: 2,
              borderRadius: '8px',
              backgroundColor: '#fef3c7',
              border: '1px solid #fcd34d',
              '& .MuiAlert-icon': {
                color: '#d97706'
              }
            }}
          >
            <Typography variant="body2" sx={{ mb: 1, fontWeight: 600, color: '#92400e' }}>
              File Ownership Policy
            </Typography>
            <Typography variant="body2" sx={{ color: '#92400e' }}>
              Once you publish a paper in this group, you cannot unpublish or retrieve it. It will belong to the group permanently.
            </Typography>
          </Alert>
          <Typography variant="body2" color="text.secondary" sx={{ color: '#6b7280' }}>
            Do you want to proceed with joining this group?
          </Typography>
        </DialogContent>
        <DialogActions sx={{ px: 3, pb: 3, pt: 2 }}>
          <Button
            onClick={() => setOpen(false)}
            disabled={joining}
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
            onClick={() => {
              onSubmit()
              setOpen(false)
            }}
            variant="contained"
            disabled={joining}
            sx={{
              textTransform: 'none',
              fontWeight: 600,
              borderRadius: '6px',
              backgroundColor: '#1f2937',
              color: 'white',
              minWidth: 100,
              '&:hover': {
                backgroundColor: '#374151'
              }
            }}
          >
            {joining ? <CircularProgress size={20} /> : 'Proceed'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}

export default Invitation