import { Avatar, Box, Button, Stack, Typography } from '@mui/material'
import React from 'react'
import { useNavigate } from 'react-router-dom'

function PublishedBy(props) {
  const url = props.username
    ? `http://localhost:8082/api/get/avatar/${props.username}`
    : `http://localhost:8082/group/${props.groupId}/get/avatar`

  const url1 = props.username
    ? `/profile/${props.username}`
    : `/profile/group/${props.groupId}`

  const navigate = useNavigate()

  return (
    <Box>
      <Stack spacing={2} sx={{ alignItems: 'center', textAlign: 'center' }}>
        {props.name && (
          <>
            <Avatar
              sx={{
                width: 80,
                height: 80,
                backgroundColor: '#8b8fa8',
                fontSize: '1.75rem',
                fontWeight: 700,
                border: '2px solid #e5e7eb',
              }}
              variant="rounded"
              src={url}
            >
              {props.name.slice(0, 1).toUpperCase()}
            </Avatar>

            <Box>
              <Typography
                sx={{
                  fontWeight: 600,
                  color: '#1f2937',
                  fontSize: '1rem',
                }}
              >
                {props.name}
              </Typography>
              <Typography
                sx={{
                  color: '#9ca3af',
                  fontSize: '0.875rem',
                  fontWeight: 400,
                }}
              >
                @{props.name.toLowerCase()}
              </Typography>
            </Box>

            <Button
              onClick={() => {
                navigate(url1)
              }}
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                fontSize: '0.9rem',
                px: 3,
                py: 1,
                borderRadius: '6px',
                backgroundColor: '#1f2937',
                color: 'white',
                transition: 'all 0.2s',
                '&:hover': {
                  backgroundColor: '#374151',
                },
              }}
            >
              View Profile
            </Button>
          </>
        )}
      </Stack>
    </Box>
  )
}

export default PublishedBy