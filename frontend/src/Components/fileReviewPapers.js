import { 
  Box, 
  Button, 
  Card, 
  CardActions, 
  CardContent, 
  CardMedia, 
  Typography,
  Chip,
  Tooltip
} from '@mui/material'
import React, { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'

function FileReviewPapers({ files }) {
  const {groupId} = useParams();
  const [likedFiles, setLikedFiles] = useState(new Set());
  const navigate = useNavigate();

  const toggleLike = (fileId) => {
    const newLiked = new Set(likedFiles)
    if (newLiked.has(fileId)) {
      newLiked.delete(fileId)
    } else {
      newLiked.add(fileId)
    }
    setLikedFiles(newLiked)
  }

  return (
    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 3, pb: 4, alignItems: 'stretch' }}>
      {files.map((file) => (
        <Card 
          key={file.id}
          sx={{
            width: 200,
            display: 'flex',
            flexDirection: 'column',
            transition: 'all 0.25s ease',
            border: '1px solid #e5e7eb',
            borderRadius: '12px',
            backgroundColor: '#ffffff',
            boxShadow: 'none',
            overflow: 'hidden',
            '&:hover': {
              transform: 'translateY(-4px)',
              boxShadow: '0 12px 24px rgba(0, 0, 0, 0.06)',
              borderColor: '#d1d5db'
            }
          }}
        >
          {/* Image Container */}
          <Box 
            sx={{
              position: 'relative',
              width: '100%',
              height: 240,
              backgroundColor: '#fafbfc',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              overflow: 'hidden',
              borderBottom: '1px solid #e5e7eb'
            }}
          >
            <CardMedia
              sx={{
                height: '100%',
                width: '100%',
                objectFit: 'cover',
                transition: 'transform 0.3s ease'
              }}
              image={`http://localhost:8082/thumbnail/${file.id}`}
              component="img"
            />
          </Box>

          {/* Content */}
          <CardContent sx={{ 
            flexGrow: 1, 
            display: 'flex', 
            flexDirection: 'column', 
            p: 2,
            pb: 1.5,
            borderBottom: '1px solid #e5e7eb'
          }}>
            <Tooltip title={file.title} arrow>
              <Typography
                variant="body2"
                sx={{
                  fontWeight: 600,
                  color: '#1f2937',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  lineHeight: 1.4,
                  mb: 0.75,
                  cursor: 'pointer',
                  fontSize: '0.875rem'
                }}
              >
                {file.title}
              </Typography>
            </Tooltip>

            {file.description && (
              <Tooltip title={file.description} arrow>
                <Typography
                  variant="caption"
                  sx={{
                    color: '#6b7280',
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    display: '-webkit-box',
                    WebkitLineClamp: 2,
                    WebkitBoxOrient: 'vertical',
                    lineHeight: 1.3,
                    mb: 1,
                    fontSize: '0.75rem'
                  }}
                >
                  {file.description}
                </Typography>
              </Tooltip>
            )}

            {file?.moderator && (
              <Tooltip title="Review By" arrow>
                <Typography
                  variant="caption"
                  sx={{
                    color: '#1f2937',
                    fontWeight: 600,
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    whiteSpace: 'nowrap',
                    mb: 1.5,
                    fontSize: '0.75rem'
                  }}
                >
                  Review by: {file.moderator}
                </Typography>
              </Tooltip>
            )}

            <Box sx={{ display: 'flex', gap: 0.5, flexWrap: 'wrap', mt: 'auto' }}>
              <Chip
                label={file.extension || 'PDF'}
                size="small"
                sx={{
                  height: 22,
                  fontSize: '0.7rem',
                  fontWeight: 500,
                  backgroundColor: '#f9fafb',
                  color: '#6b7280',
                  border: '1px solid #e5e7eb'
                }}
              />
            </Box>
          </CardContent>

          {/* Actions */}
          <CardActions 
            sx={{
              justifyContent: 'center',
              pt: 0,
              pb: 0,
              px: 2
            }}
          >
            <Button
              component={Link}
              to={`/${groupId}/review/${file.id}`}
              size="small"
              fullWidth
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                color: '#1f2937',
                fontSize: '0.8rem',
                padding: '6px 16px',
                borderRadius: '6px',
                backgroundColor: '#ffffff',
                border: 'none',
                minHeight: '32px',
                '&:hover': {
                  backgroundColor: '#f9fafb',
                  textDecoration: 'none'
                }
              }}
            >
              Review
            </Button>
          </CardActions>
        </Card>
      ))}
    </Box>
  )
}

export default FileReviewPapers