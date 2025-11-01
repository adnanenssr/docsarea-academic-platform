import { Box, Button, Pagination, Stack, Typography, Container, CircularProgress, Paper } from '@mui/material'
import React, { useEffect, useState } from 'react'
import GroupList from './group'
import AddGroup from './addGroup'
import { Folder } from 'lucide-react'

function UserGroups() {
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
        const response = await fetch(`http://localhost:8082/get/groups?page=${page}`, {
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
        {/* Header with Add Button */}
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 6 }}>
          <Box>
            <Typography
              variant="h3"
              sx={{
                fontWeight: 800,
                color: '#1f2937',
                fontSize: { xs: '2rem', md: '2.5rem' },
                mb: 0.5,
              }}
            >
              My Groups
            </Typography>
            <Typography sx={{ color: '#6b7280', fontSize: '1rem' }}>
              {numElements} group{numElements !== 1 ? 's' : ''} total
            </Typography>
          </Box>
          <AddGroup />
        </Box>

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

        {/* Loading State */}
        {loading && (
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              minHeight: '400px',
            }}
          >
            <CircularProgress 
              size={48} 
              thickness={4}
              sx={{ color: '#1f2937' }} 
            />
          </Box>
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
                  <Stack alignItems="center" sx={{ mt: 6 }}>
                    <Pagination
                      count={numPages}
                      page={page + 1}
                      onChange={(e, value) => {
                        setPage(value - 1)
                        window.scrollTo({ top: 0, behavior: 'smooth' })
                      }}
                      sx={{
                        '& .MuiPaginationItem-root': {
                          color: '#6b7280',
                          fontWeight: 500,
                          border: '1px solid #e5e7eb',
                          borderRadius: '6px',
                          fontSize: '0.9rem',
                          transition: 'all 0.2s',
                          '&:hover': {
                            borderColor: '#d1d5db',
                            backgroundColor: '#f9fafb',
                          }
                        },
                        '& .MuiPaginationItem-page.Mui-selected': {
                          backgroundColor: '#1f2937',
                          color: 'white',
                          border: '1px solid #1f2937',
                          '&:hover': {
                            backgroundColor: '#374151',
                          }
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
                  minHeight: '400px',
                }}
              >
                <Paper
                  sx={{
                    p: 6,
                    textAlign: 'center',
                    backgroundColor: '#fafbfc',
                    border: '1px solid #e5e7eb',
                    borderRadius: '12px',
                    boxShadow: 'none',
                    maxWidth: '450px',
                  }}
                >
                  <Stack alignItems="center" spacing={2.5}>
                    <Box
                      sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        width: 72,
                        height: 72,
                        backgroundColor: '#ffffff',
                        borderRadius: '12px',
                        border: '1px solid #e5e7eb',
                      }}
                    >
                      <Folder size={36} color="#9ca3af" strokeWidth={1.5} />
                    </Box>
                    <Box>
                      <Typography
                        variant="h6"
                        sx={{
                          color: '#1f2937',
                          fontWeight: 700,
                          mb: 0.75,
                          fontSize: '1.1rem'
                        }}
                      >
                        No Groups Yet
                      </Typography>
                      <Typography
                        sx={{
                          color: '#6b7280',
                          fontSize: '0.9rem',
                          lineHeight: 1.5,
                          maxWidth: '320px',
                        }}
                      >
                        Create your first group to start organizing and sharing your content
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

export default UserGroups