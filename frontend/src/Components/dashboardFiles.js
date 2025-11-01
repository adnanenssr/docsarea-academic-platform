import { Box, Grid, Card, CardMedia, Stack, Typography } from '@mui/material'
import React from 'react'

function DashboardFiles({ files }) {
  return (
    <Box>
      {/* Header */}
      <Typography
        variant="h6"
        sx={{
          fontWeight: 600,
          color: '#1f2937',
          fontSize: '0.95rem',
          mb: 3,
        }}
      >
        Recent Files
      </Typography>

      {/* Files List */}
      <Stack spacing={2}>
        {files.map((file) => (
          <Box
            key={file.id}
            sx={{
              display: 'flex',
              gap: 3,
              p: 2.5,
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              backgroundColor: '#ffffff',
              transition: 'all 0.2s',
              cursor: 'pointer',
              '&:hover': {
                borderColor: '#d1d5db',
                boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)',
              }
            }}
          >
            {/* Thumbnail */}
            <Card
              sx={{
                width: '100px',
                height: '130px',
                flexShrink: 0,
                borderRadius: '6px',
                overflow: 'hidden',
                boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)',
                transition: 'transform 0.2s',
              }}
            >
              <CardMedia
                component="img"
                image={`http://localhost:8082/thumbnail/${file.id}`}
                sx={{
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover',
                  backgroundColor: '#f3f4f6'
                }}
              />
            </Card>

            {/* Content */}
            <Stack spacing={1} sx={{ flex: 1, justifyContent: 'center' }}>
              <Typography
                sx={{
                  fontWeight: 600,
                  color: '#1f2937',
                  fontSize: '0.95rem',
                  lineHeight: 1.4,
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  overflow: 'hidden',
                }}
              >
                {file.title}
              </Typography>
              <Typography
                sx={{
                  color: '#6b7280',
                  fontSize: '0.85rem',
                  lineHeight: 1.5,
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  overflow: 'hidden',
                }}
              >
                {file.description}
              </Typography>
            </Stack>
          </Box>
        ))}
      </Stack>
    </Box>
  )
}

export default DashboardFiles