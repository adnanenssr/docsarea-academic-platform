import { Box, Card, CardMedia, Stack, Typography } from '@mui/material'
import React from 'react'

function FilesViews({ files }) {
  return (
    <Box sx={{ width: '100%' }}>
      {/* Header */}
      <Box
        sx={{
          display: 'flex',
          px: 2,
          py: 1.5,
          backgroundColor: '#ffffff',
          borderRadius: '6px',
          mb: 2,
          border: '1px solid #e5e7eb',
        }}
      >
        <Box sx={{ width: '16.67%', textAlign: 'center' }}>
          <Typography
            sx={{
              fontWeight: 600,
              color: '#6b7280',
              fontSize: '0.8rem',
              textTransform: 'uppercase',
              letterSpacing: '0.05em',
            }}
          >
            Paper
          </Typography>
        </Box>
        <Box sx={{ width: '66.67%' }} />
        <Box sx={{ width: '16.67%', textAlign: 'center' }}>
          <Typography
            sx={{
              fontWeight: 600,
              color: '#6b7280',
              fontSize: '0.8rem',
              textTransform: 'uppercase',
              letterSpacing: '0.05em',
            }}
          >
            Views
          </Typography>
        </Box>
      </Box>

      {/* File Rows */}
      <Stack spacing={1.5}>
        {files.map((file) => (
          <Box
            key={file.id}
            sx={{
              display: 'flex',
              alignItems: 'center',
              px: 2,
              py: 2,
              borderRadius: '6px',
              border: '1px solid #e5e7eb',
              backgroundColor: '#ffffff',
              transition: 'all 0.2s ease',
              '&:hover': {
                backgroundColor: '#f9fafb',
                borderColor: '#d1d5db',
                boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)',
              }
            }}
          >
            {/* Thumbnail */}
            <Box sx={{ width: '16.67%', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
              <Card
                sx={{
                  width: '100px',
                  height: '130px',
                  borderRadius: '6px',
                  overflow: 'hidden',
                  boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)',
                  transition: 'transform 0.2s',
                  '&:hover': {
                    transform: 'scale(1.02)',
                  }
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
            </Box>

            {/* Title & Description */}
            <Box sx={{ width: '66.67%', px: 3 }}>
              <Typography
                sx={{
                  fontWeight: 600,
                  color: '#1f2937',
                  fontSize: '0.95rem',
                  mb: 0.5,
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
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  overflow: 'hidden',
                }}
              >
                {file.description}
              </Typography>
            </Box>

            {/* Views */}
            <Box sx={{ width: '16.67%', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
              <Typography
                sx={{
                  fontWeight: 700,
                  color: '#1f2937',
                  fontSize: '1.1rem',
                  textAlign: 'center',
                }}
              >
                {file.metric}
              </Typography>
            </Box>
          </Box>
        ))}
      </Stack>
    </Box>
  )
}

export default FilesViews