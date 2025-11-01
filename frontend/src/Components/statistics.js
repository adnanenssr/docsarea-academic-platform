import React, { useState } from 'react'
import CallViews from './callViews'
import { useParams } from 'react-router-dom'
import CallDownloads from './callDownloads'
import { Box, Container, Paper, Stack, Typography } from '@mui/material'
import { Eye, Download } from 'lucide-react'

function Statistics() {
  const { groupId } = useParams()
  const [metric, setMetric] = useState(0)

  const metrics = [
    { id: 0, label: 'Views', icon: Eye },
    { id: 1, label: 'Downloads', icon: Download }
  ]

  return (
    <Box sx={{ width: '100%', bgcolor: '#ffffff', minHeight: '100vh', py: 6 }}>
      <Container maxWidth="lg">
        <Stack direction={{ xs: 'column', lg: 'row' }} spacing={4}>
          {/* Sidebar Navigation */}
          <Paper
            sx={{
              width: { xs: '100%', lg: 240 },
              height: 'fit-content',
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              boxShadow: 'none',
              position: { lg: 'sticky' },
              top: { lg: 88 },
              overflow: 'hidden',
            }}
          >
            {/* Header */}
            <Box
              sx={{
                p: 3,
                borderBottom: '1px solid #e5e7eb',
                backgroundColor: '#ffffff',
              }}
            >
              <Typography
                variant="h6"
                sx={{
                  fontWeight: 700,
                  color: '#1f2937',
                  fontSize: '1rem',
                  mb: 0.5,
                }}
              >
                Metrics
              </Typography>
              <Typography
                variant="caption"
                sx={{
                  color: '#6b7280',
                  fontSize: '0.8rem',
                }}
              >
                Track performance data
              </Typography>
            </Box>

            {/* Menu Items */}
            <Stack spacing={1} sx={{ p: 2 }}>
              {metrics.map((item) => {
                const Icon = item.icon
                const isActive = metric === item.id

                return (
                  <Box
                    key={item.id}
                    onClick={() => setMetric(item.id)}
                    sx={{
                      display: 'flex',
                      alignItems: 'center',
                      gap: 2,
                      px: 2.5,
                      py: 2,
                      borderRadius: '6px',
                      cursor: 'pointer',
                      transition: 'all 0.2s',
                      backgroundColor: isActive ? '#f3f4f6' : 'transparent',
                      borderLeft: isActive ? '3px solid #1f2937' : '3px solid transparent',
                      color: isActive ? '#1f2937' : '#6b7280',
                      '&:hover': {
                        backgroundColor: isActive ? '#f3f4f6' : '#f9fafb',
                        color: '#1f2937',
                      }
                    }}
                  >
                    <Icon size={20} color={isActive ? '#1f2937' : '#9ca3af'} />
                    <Typography
                      sx={{
                        fontSize: '0.9rem',
                        fontWeight: isActive ? 600 : 500,
                        color: 'inherit',
                      }}
                    >
                      {item.label}
                    </Typography>
                  </Box>
                )
              })}
            </Stack>
          </Paper>

          {/* Main Content */}
          <Box sx={{ flex: 1, minWidth: 0 }}>
            {metric === 0 ? (
              <CallViews groupId={groupId} />
            ) : (
              <CallDownloads groupId={groupId} />
            )}
          </Box>
        </Stack>
      </Container>
    </Box>
  )
}

export default Statistics