import { Button, Card, CardActions, CardContent, Grid, Typography, Box, Avatar } from '@mui/material'
import React from 'react'
import { Link } from 'react-router-dom'
import { ArrowRight } from 'lucide-react'

function GroupList({ groups }) {
  const CARD_WIDTH = 280
  const CARD_HEIGHT = 320

  return (
    <Box sx={{
      width: '100%',
      background: '#ffffff',
      padding: 0
    }}>
      <Grid container spacing={2.5} sx={{ justifyContent: 'flex-start' }}>
        {groups.map((group) => (
          <Grid item key={group.id}>
            <Link to={`/group/${group.id}`} style={{ textDecoration: 'none' }}>
              <Card
                sx={{
                  width: CARD_WIDTH,
                  height: CARD_HEIGHT,
                  background: '#ffffff',
                  border: '1px solid #e5e7eb',
                  borderRadius: '8px',
                  overflow: 'hidden',
                  transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                  cursor: 'pointer',
                  position: 'relative',
                  display: 'flex',
                  flexDirection: 'column',
                  '&:hover': {
                    borderColor: '#d1d5db',
                    boxShadow: '0 8px 16px rgba(0, 0, 0, 0.1)',
                    transform: 'translateY(-4px)',
                  }
                }}
              >
                {/* Cover Image Container */}
                <Box
                  sx={{
                    position: 'relative',
                    height: 100,
                    background: '#f3f4f6',
                    backgroundImage: !group?.coverImg ? 'url("/background.jpg")' : `url("http://localhost:8082/group/${group.id}/get/cover")`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    overflow: 'visible',
                    flexShrink: 0,
                    '&::after': {
                      content: '""',
                      position: 'absolute',
                      inset: 0,
                      background: 'linear-gradient(180deg, transparent 0%, rgba(0,0,0,0.08) 100%)',
                    }
                  }}
                />

                {/* Profile Avatar - Absolutely Positioned */}
                <Box
                  sx={{
                    position: 'absolute',
                    top: 70,
                    left: '50%',
                    transform: 'translateX(-50%)',
                    zIndex: 10,
                  }}
                >
                  <Avatar
                    src={!group?.profileImg ? '/background.jpg' : `http://localhost:8082/group/${group.id}/get/avatar`}
                    sx={{
                      width: 80,
                      height: 80,
                      border: '3px solid white',
                      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.12)',
                      background: '#8b8fa8',
                      fontSize: '2rem',
                      fontWeight: 700,
                      color: '#ffffff',
                    }}
                  />
                </Box>

                {/* Content Section */}
                <CardContent sx={{
                  paddingTop: '60px',
                  paddingBottom: '12px',
                  flex: 1,
                  textAlign: 'center',
                  px: 2,
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'flex-start',
                }}>
                  <Typography
                    sx={{
                      fontWeight: 700,
                      fontSize: '0.95rem',
                      color: '#1f2937',
                      marginBottom: '6px',
                      display: '-webkit-box',
                      WebkitLineClamp: 2,
                      WebkitBoxOrient: 'vertical',
                      overflow: 'hidden',
                      letterSpacing: '-0.3px',
                      lineHeight: 1.3,
                    }}
                  >
                    {group.name}
                  </Typography>

                  {group.description && (
                    <Typography
                      variant="body2"
                      sx={{
                        color: '#6b7280',
                        marginBottom: '4px',
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical',
                        overflow: 'hidden',
                        fontSize: '0.75rem',
                        lineHeight: 1.3,
                      }}
                    >
                      {group.description}
                    </Typography>
                  )}
                </CardContent>

                {/* Action Section */}
                <CardActions sx={{
                  paddingBottom: '16px',
                  paddingTop: 0,
                  justifyContent: 'center',
                  flexShrink: 0,
                  px: 2,
                }}>
                  <Button
                    endIcon={<ArrowRight size={16} />}
                    sx={{
                      background: '#1f2937',
                      color: 'white',
                      textTransform: 'none',
                      fontWeight: 600,
                      fontSize: '0.85rem',
                      padding: '6px 16px',
                      borderRadius: '6px',
                      transition: 'all 0.2s ease',
                      boxShadow: 'none',
                      border: 'none',
                      '&:hover': {
                        background: '#374151',
                        transform: 'translateY(-1px)',
                      }
                    }}
                  >
                    View
                  </Button>
                </CardActions>
              </Card>
            </Link>
          </Grid>
        ))}
      </Grid>
    </Box>
  )
}

export default GroupList