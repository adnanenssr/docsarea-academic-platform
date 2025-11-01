import { Box, Button, Pagination, Stack, Typography, Container, CircularProgress, Paper } from '@mui/material'
import React, { useEffect, useState } from 'react'
import GroupList from './group'
import { UserPlus, Folder } from 'lucide-react'

function UserJoinedGroups() {
  const [page, setPage] = useState(0)
  const [groups, setGroups] = useState([])
  const [numPages, setNumPages] = useState(1)
  const [numElements, setNumElements] = useState(0)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    const getGroups = async () => {
      try {
        setLoading(true)
        setError(null)
        const response = await fetch(`http://localhost:8082/get/user/joined/groups?page=${page}`, {
          method: "GET",
          credentials: "include"
        })
        if (!response.ok) {
          throw new Error("Could not fetch groups! Please try again later")
        }
        const data = await response.json()
        if (data) {
          setGroups(data.groups)
          setNumPages(data.numPages)
          setNumElements(data.numElements)
        }
      } catch (err) {
        setError(err.message)
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    getGroups()
  }, [page])

  return (
    <Box sx={{ width: '100%', bgcolor: '#ffffff', minHeight: '100vh', py: 6 }}>
      <Container maxWidth="lg">
        {/* Header */}
        <Box sx={{ mb: 6 }}>
          <Typography
            variant="h3"
            sx={{
              fontWeight: 800,
              color: '#1f2937',
              fontSize: { xs: '2rem', md: '2.5rem' },
              mb: 0.5,
            }}
          >
            Joined Groups
          </Typography>
          <Typography sx={{ color: '#6b7280', fontSize: '1rem' }}>
            {numElements} group{numElements !== 1 ? 's' : ''} joined
          </Typography>
        </Box>

        {/* Error State */}
        {error && (
          <Paper
            sx={{
              p: 3,
              mb: 4,
              backgroundColor: '#fef2f2',
              border: '1px solid #fee2e2',
              borderRadius: '8px',
              boxShadow: 'none',
            }}
          >
            <Typography sx={{ color: '#991b1b', fontWeight: 600, fontSize: '0.95rem' }}>
              {error}
            </Typography>
          </Paper>
        )}

        {/* Loading State */}
        {loading && (
          <Stack alignItems="center" spacing={3} sx={{ py: 12 }}>
            <CircularProgress sx={{ color: '#1f2937' }} size={48} />
            <Typography sx={{ color: '#6b7280', fontSize: '1rem' }}>Loading groups...</Typography>
          </Stack>
        )}

        {/* Content */}
        {!loading && (
          <>
            {numElements > 0 && groups && Array.isArray(groups) ? (
              <Box>
                {/* Groups List */}
                <Box sx={{ mb: 6 }}>
                  <GroupList groups={groups} />
                </Box>

                {/* Pagination */}
                {numPages > 1 && (
                  <Stack alignItems="center" sx={{ mt: 8 }}>
                    <Pagination
                      count={numPages}
                      page={page + 1}
                      onChange={(e, value) => {
                        setPage(value - 1)
                        window.scrollTo({ top: 0, behavior: 'smooth' })
                      }}
                      size="large"
                      sx={{
                        '& .MuiPaginationItem-root': {
                          color: '#6b7280',
                          fontWeight: 600,
                          border: '1px solid #e5e7eb',
                          borderRadius: '6px',
                          fontSize: '0.95rem',
                          transition: 'all 0.2s',
                          '&:hover': {
                            borderColor: '#1f2937',
                            backgroundColor: '#f9fafb',
                          }
                        },
                        '& .MuiPaginationItem-page.Mui-selected': {
                          background: '#1f2937',
                          color: 'white',
                          border: '1px solid #1f2937',
                        },
                        '& .MuiPaginationItem-ellipsis': {
                          color: '#d1d5db',
                        },
                      }}
                    />
                  </Stack>
                )}
              </Box>
            ) : (
              <Box
                sx={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  minHeight: '500px',
                }}
              >
                <Paper
                  sx={{
                    p: 8,
                    textAlign: 'center',
                    backgroundColor: '#f9fafb',
                    border: '2px dashed #e5e7eb',
                    borderRadius: '12px',
                    boxShadow: 'none',
                    maxWidth: '500px',
                  }}
                >
                  <Stack alignItems="center" spacing={3}>
                    <Box
                      sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        width: 80,
                        height: 80,
                        backgroundColor: '#ffffff',
                        borderRadius: '16px',
                        border: '2px solid #e5e7eb',
                      }}
                    >
                      <Folder size={40} color="#9ca3af" strokeWidth={1.5} />
                    </Box>
                    <Box>
                      <Typography
                        variant="h5"
                        sx={{
                          color: '#1f2937',
                          fontWeight: 700,
                          mb: 1,
                        }}
                      >
                        No Groups Joined
                      </Typography>
                      <Typography
                        sx={{
                          color: '#6b7280',
                          fontSize: '1rem',
                          mb: 4,
                          maxWidth: '350px',
                        }}
                      >
                        You haven't joined any groups yet. Browse and join groups to start collaborating
                      </Typography>
                    </Box>
                  </Stack>
                </Paper>
              </Box>
            )}
          </>
        )}
      </Container>
    </Box>
  )
}

export default UserJoinedGroups