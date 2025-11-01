import { Avatar, Box, Button, Chip, Container, Paper, Stack, Tab, Tabs, Typography, CircularProgress } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import FileViewPapers from './fileViewPapers'

function UserProfile() {
  const [page, setPage] = useState(0)
  const [files, setFiles] = useState(null)
  const [tab, setActiveTab] = useState(0)
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [filesLoading, setFilesLoading] = useState(false)
  const { username } = useParams()

  useEffect(() => {
    const getUser = async () => {
      try {
        setLoading(true)
        setError(null)

        const response = await fetch(`http://localhost:8082/user/${username}`, {
          method: "GET",
          credentials: "include"
        })

        if (!response.ok) {
          throw new Error("Could not fetch user")
        }

        const data = await response.json()
        if (data) {
          setUser(data)
        }
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    getUser()
  }, [username])

  useEffect(() => {
    const getFiles = async () => {
      try {
        setFilesLoading(true)
        setError(null)

        const response = await fetch(`http://localhost:8082/get/user/${username}/files/published?page=${page}`, {
          method: "GET",
          credentials: "include"
        })

        if (!response.ok) {
          throw new Error("Could not fetch files")
        }

        const data = await response.json()
        if (data) {
          setFiles(data.files)
        }
      } catch (err) {
        console.error(err)
        setError(err.message)
        setFiles([])
      } finally {
        setFilesLoading(false)
      }
    }

    if (username) {
      getFiles()
    }
  }, [page, username])

  if (loading) {
    return (
      <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', bgcolor: '#ffffff' }}>
        <CircularProgress sx={{ color: '#1f2937' }} />
      </Box>
    )
  }

  if (error && !user) {
    return (
      <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', bgcolor: '#ffffff' }}>
        <Typography color="error" variant="h6">{error}</Typography>
      </Box>
    )
  }

  return (
    <Box sx={{ minHeight: '100vh', backgroundColor: '#ffffff', py: 1 }}>
      <Container maxWidth="90vw">
        {/* Header Section with Cover and Avatar */}
        <Box sx={{ mb: 6 }}>
          <Paper
            sx={{
              overflow: "hidden",
              borderRadius: '12px',
              border: '1px solid #e5e7eb',
              boxShadow: 'none',
            }}
          >
            {/* Cover Image */}
            <Box
              sx={{
                backgroundImage: !user?.coverPic ? 'url("/background.jpg")' : 'url("http://localhost:8082/api/get/cover")',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                position: "relative",
                height: 240,
                width: "100%",
                bgcolor: '#f3f4f6',
              }}
            >
              {/* Gradient Overlay */}
              <Box
                sx={{
                  position: 'absolute',
                  inset: 0,
                  background: 'linear-gradient(to bottom, transparent 0%, rgba(0,0,0,0.1) 100%)',
                }}
              />
            </Box>

            {/* Profile Info Container */}
            <Box sx={{ px: 6, pt: 2, pb: 4 }}>
              <Stack direction={{ xs: 'column', sm: 'row' }} spacing={4} alignItems={{ xs: 'flex-start', sm: 'flex-end' }}>
                {/* Avatar */}
                <Avatar
                  variant='rounded'
                  src={`http://localhost:8082/api/get/avatar/${username}`}
                  sx={{
                    width: 120,
                    height: 120,
                    border: "3px solid white",
                    borderRadius: "12px",
                    boxShadow: "0 4px 16px rgba(0, 0, 0, 0.15)",
                    backgroundColor: '#8b8fa8',
                    fontSize: '3rem',
                    fontWeight: 700,
                    mt: -8,
                    flexShrink: 0,
                  }}
                >
                  {username && username.slice(0, 1).toUpperCase()}
                </Avatar>

                {/* Info and Button */}
                <Stack flex={1} spacing={1}>
                  <Box>
                    <Typography
                      variant='h4'
                      sx={{
                        fontWeight: 700,
                        color: '#1f2937',
                        fontSize: { xs: '1.5rem', md: '2rem' },
                        mb: 0.5,
                      }}
                    >
                      {user && `${user.firstname} ${user.lastname}`}
                    </Typography>
                    <Typography
                      sx={{
                        color: '#6b7280',
                        fontSize: '1rem',
                        fontWeight: 500,
                      }}
                    >
                      @{user?.username}
                    </Typography>
                  </Box>
                </Stack>
              </Stack>
            </Box>
          </Paper>
        </Box>

        {/* Content Card */}
        <Paper
          sx={{
            borderRadius: "12px",
            border: '1px solid #e5e7eb',
            boxShadow: 'none',
            overflow: "hidden",
          }}
        >
          {/* Tabs */}
          <Tabs
            value={tab}
            onChange={(e, newValue) => setActiveTab(newValue)}
            sx={{
              borderBottom: "1px solid #e5e7eb",
              px: 4,
              '& .MuiTab-root': {
                textTransform: 'none',
                fontWeight: 600,
                fontSize: '0.95rem',
                color: '#6b7280',
                minHeight: 56,
                transition: 'all 0.2s',
                '&.Mui-selected': {
                  color: '#1f2937',
                }
              },
              '& .MuiTabs-indicator': {
                backgroundColor: '#1f2937',
                height: 2.5,
              }
            }}
          >
            <Tab label="Papers" value={0} />
            <Tab label="About" value={1} />
          </Tabs>

          {/* Tab Content */}
          <Box sx={{ p: 6 }}>
            {tab === 0 && (
              <>
                {filesLoading ? (
                  <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
                    <CircularProgress sx={{ color: '#1f2937' }} />
                  </Box>
                ) : files && files.length > 0 ? (
                  <FileViewPapers files={files} />
                ) : (
                  <Typography sx={{ color: '#6b7280', fontSize: '1rem', textAlign: 'center', py: 4 }}>
                    No papers published yet
                  </Typography>
                )}
              </>
            )}

            {tab === 1 && (
              <Stack spacing={3}>
                <Typography
                  sx={{
                    lineHeight: 1.8,
                    color: '#4b5563',
                    fontSize: '1rem',
                  }}
                >
                  {user && user.bio ? user.bio : "No description available"}
                </Typography>
              </Stack>
            )}
          </Box>
        </Paper>
      </Container>
    </Box>
  )
}

export default UserProfile