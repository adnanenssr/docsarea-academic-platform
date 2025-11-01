import { Box, Chip, Stack, Typography, Avatar, IconButton } from '@mui/material'
import React from 'react'
import { useNavigate } from 'react-router-dom'

function PaperDetails(props) {
  const navigate = useNavigate()
  
  const colors = [
    '#7c8aa2',
    '#9b8fa8',
    '#8b9ca3',
    '#9b8fa8',
    '#7c9aa2',
    '#8b8fa8'
  ]

  return (
    <Stack spacing={4} sx={{ padding: '32px' }}>
      {/* Theme Chip */}
      {props.theme && (
        <Box sx={{ alignSelf: 'flex-start' }}>
          <Chip
            label={props.theme}
            sx={{
              backgroundColor: '#f0f4ff',
              color: '#3730a3',
              fontSize: '0.75rem',
              fontWeight: 600,
              letterSpacing: '0.5px',
              textTransform: 'uppercase',
              height: '28px',
              borderRadius: '6px',
              border: '1px solid #e0e7ff',
            }}
          />
        </Box>
      )}

      {/* Title */}
      <Typography
        variant="h2"
        sx={{
          fontFamily: "'Inter', sans-serif",
          fontWeight: 700,
          color: '#0f172a',
          fontSize: { xs: '1.875rem', sm: '2.25rem', md: '2.5rem' },
          lineHeight: 1.2,
          letterSpacing: '-0.02em',
        }}
      >
        {props.title}
      </Typography>

      {/* Description */}
      {props.description && (
        <Typography
          variant="body1"
          sx={{
            color: '#4b5563',
            lineHeight: 1.8,
            fontSize: '1rem',
            fontWeight: 400,
          }}
        >
          {props.description}
        </Typography>
      )}

      {/* Authors Section */}
      {props.authors && props.authors?.length > 0 && (
        <Box>
          <Typography
            variant="caption"
            sx={{
              color: '#64748b',
              fontSize: '0.8rem',
              fontWeight: 600,
              textTransform: 'uppercase',
              letterSpacing: '0.5px',
              display: 'block',
              marginBottom: '12px',
            }}
          >
            Authors
          </Typography>

          <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', gap: 1 }}>
            {props.authors.map((author, index) => (
              <IconButton
                key={author}
                onClick={() => navigate(`/profile/${author}`)}
                sx={{
                  p: 0.5,
                  transition: 'all 0.3s ease',
                  '&:hover': {
                    transform: 'translateY(-3px)',
                  },
                }}
              >
                <Avatar
                  sx={{
                    width: 44,
                    height: 44,
                    backgroundColor: colors[index % colors.length],
                    color: '#ffffff',
                    fontWeight: 700,
                    fontSize: '1rem',
                    border: '1.5px solid #e5e7eb',
                    boxShadow: '0 1px 3px rgba(0, 0, 0, 0.08)',
                  }}
                >
                  {author.slice(0, 1).toUpperCase()}
                </Avatar>
              </IconButton>
            ))}
          </Stack>
        </Box>
      )}
    </Stack>
  )
}

export default PaperDetails