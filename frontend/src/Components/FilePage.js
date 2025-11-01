import { Box, Stack, Typography, Paper, Divider, Container, CircularProgress } from '@mui/material'
import React, { useEffect, useState } from 'react'
import PdfViewer from './PdfViewer'
import ModernActionIcons from "./Icons"
import PaperDetails from './paperDetails'
import PublishedBy from './publishedBy'
import PaperSideBarCard from './paperSideBarCard'
import { useParams } from 'react-router-dom'

function FilePage() {
  const { fileId } = useParams()
  const [fileData, setFileData] = useState(null)
  const [publisherName, setPublisherName] = useState(null)
  const [publisherPic, setPublisherPic] = useState(null)
  const [theme, setTheme] = useState(null)
  const [groupId, setGroupId] = useState("")
  const [username, setUsername] = useState("")
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const getPublisher = async (fileData) => {
    try {
      if (fileData.groupId != null) {
        const response = await fetch(`http://localhost:8082/group/${fileData.groupId}`, {
          credentials: "include"
        })
        const group = await response.json()
        setPublisherName(group.name)
        setPublisherPic(null)
        setTheme(group.theme)
        setGroupId(fileData.groupId)
      } else if (fileData.groupId == null && fileData.owner != null) {
        const response = await fetch(`http://localhost:8082/user/${fileData.owner}`, {
          credentials: "include"
        })
        const user = await response.json()
        setPublisherName(user.username)
        setPublisherPic(null)
        setTheme(null)
        setUsername(fileData.owner)
      }
    } catch (err) {
      console.error("Error fetching publisher:", err)
    }
  }

  const getFileDetails = async (fileId) => {
    try {
      const response = await fetch(`http://localhost:8082/file/details/${fileId}`, {
        credentials: "include"
      })
      if (!response.ok) throw new Error("Failed to fetch file details")
      const data = await response.json()
      return data
    } catch (err) {
      setError(err.message)
      return null
    }
  }

  useEffect(() => {
    if (fileId) {
      setLoading(true)
      getFileDetails(fileId)
        .then(data => {
          if (data) {
            setFileData(data)
            getPublisher(data)
          }
          setLoading(false)
        })
    }
  }, [fileId])

  if (loading) {
    return (
      <Box
        sx={{
          width: '100%',
          minHeight: '100vh',
          background: '#ffffff',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <Stack alignItems="center" spacing={2}>
          <CircularProgress sx={{ color: '#1f2937' }} />
          <Typography sx={{ color: '#6b7280' }}>Loading document...</Typography>
        </Stack>
      </Box>
    )
  }

  if (error) {
    return (
      <Box
        sx={{
          width: '100%',
          minHeight: '100vh',
          background: '#ffffff',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          p: 4,
        }}
      >
        <Stack alignItems="center" spacing={2}>
          <Typography variant="h6" sx={{ color: '#991b1b' }}>
            Error loading document
          </Typography>
          <Typography sx={{ color: '#6b7280' }}>{error}</Typography>
        </Stack>
      </Box>
    )
  }

  return (
    <Box sx={{ backgroundColor: '#ffffff', minHeight: '100vh' }}>
      <Container maxWidth="xl" sx={{ py: 4 }}>
        <Box
          display="flex"
          gap={4}
          sx={{
            flexDirection: { xs: 'column', lg: 'row' },
          }}
        >
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

            {/* PDF Viewer & Actions */}
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
                  <ModernActionIcons fileId={fileId} />
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
              width: { xs: '100%', lg: '360px' },
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
              {/* Published By Section */}
              <Box sx={{ mb: 3 }}>
                <Typography
                  variant="caption"
                  sx={{
                    color: '#6b7280',
                    fontWeight: 600,
                    fontSize: '0.8rem',
                    textTransform: 'uppercase',
                    letterSpacing: '0.5px',
                  }}
                >
                  Published By
                </Typography>
                <Box sx={{ mt: 2 }}>
                  <PublishedBy
                    name={publisherName}
                    groupId={groupId}
                    username={username}
                  />
                </Box>
              </Box>

              <Divider sx={{ my: 3 }} />

              {/* You May Also Like Section */}
              <Box>
                <Typography
                  variant="subtitle2"
                  sx={{
                    color: '#1f2937',
                    fontWeight: 600,
                    fontSize: '0.95rem',
                    mb: 2,
                  }}
                >
                  You May Also Like
                </Typography>
                <Stack spacing={2}>
                  {fileData?.title && (
                    <PaperSideBarCard title={fileData.title} fileId={fileId} />
                  )}
                </Stack>
              </Box>
            </Paper>
          </Box>
        </Box>
      </Container>
    </Box>
  )
}

export default FilePage