import { 
  Box, 
  Button, 
  Card, 
  CardActions, 
  CardContent, 
  CardMedia, 
  Grid, 
  Typography,
  Chip,
  IconButton,
  Tooltip
} from '@mui/material'
import React, { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { Heart, Download, Eye, Delete, SquarePen, Trash } from 'lucide-react'
import { Update } from '@mui/icons-material'

function PapersWithDelete({ files }) {
  const {groupId} = useParams() ;
  const [likedFiles, setLikedFiles] = useState(new Set()) ;
  const navigate = useNavigate() ;

  const toggleLike = (fileId) => {
    const newLiked = new Set(likedFiles)
    if (newLiked.has(fileId)) {
      newLiked.delete(fileId)
    } else {
      newLiked.add(fileId)
    }
    setLikedFiles(newLiked)
  }
  const deleteFile = async (fileId) => {
    const response = await fetch(`http://localhost:8082/file/delete/${groupId}/${fileId}` , {
        method : "DELETE" ,
        credentials : "include"
    })
    

  }

  return (
    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 3, pb: 4 }}>
      {files.map((file) => (
        <Card 
          key={file.id}
          sx={{
            width: 180,
            height: '100%',
            display: 'flex',
            flexDirection: 'column',
            transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
            border: '1px solid #e5e7eb',
            background: 'linear-gradient(135deg, #ffffff 0%, #f8fafc 100%)',
            '&:hover': {
              transform: 'translateY(-8px)',
              boxShadow: '0 12px 24px rgba(0, 0, 0, 0.12)',
              borderColor: '#3b82f6'
            }
          }}
        >
          {/* Image Container */}
          <Box 
  sx={{
    position: 'relative',
    width: '100%',
    height: 250,  // Changed from 200. If card width is 180, then 180 * (1000/800) = 225, round to 250
    backgroundColor: '#f1f5f9',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    overflow: 'hidden',
    background: 'linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%)'
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
            
            {/* Overlay Actions */}
            <Box
              sx={{
                position: 'absolute',
                top: 0,
                left: 0,
                right: 0,
                bottom: 0,
                backgroundColor: 'rgba(0, 0, 0, 0)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                gap: 1,
                transition: 'all 0.3s ease',
                '&:hover': {
                  backgroundColor: 'rgba(0, 0, 0, 0.4)'
                }
              }}
            >
              <Tooltip title="Delete">
                <IconButton
                onClick={() => {
                  deleteFile(file.id) ;
                }}
                
                  sx={{
                    backgroundColor: 'rgba(255, 255, 255, 0)',
                    color: 'white',
                    transition: 'all 0.3s',
                    '&:hover': {
                      backgroundColor: 'rgba(222, 49, 5, 0.8)'
                    }
                  }}
                >
                  <Trash size={20} />
                </IconButton>
              </Tooltip>
            </Box>

            {/* Like Button */}
            

            {/* Moderator Badge */}
            {file.moderator && (
              <Box sx={{ position: 'absolute', top: 8, left: 8 }}>
                <Chip
                  label="Moderator"
                  size="small"
                  sx={{
                    backgroundColor: '#3b82f6',
                    color: 'white',
                    fontWeight: 600,
                    fontSize: '0.7rem'
                  }}
                />
              </Box>
            )}
          </Box>

          {/* Content */}
          <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', p: 1.5 }}>
            <Tooltip title={file.title} arrow>
              <Typography
                variant="body2"
                sx={{
                  fontWeight: 600,
                  color: '#1e293b',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  lineHeight: 1.3,
                  mb: 1,
                  cursor: 'pointer'
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
                    color: '#64748b',
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    display: '-webkit-box',
                    WebkitLineClamp: 1,
                    WebkitBoxOrient: 'vertical',
                    lineHeight: 1.2,
                    mb: 1
                  }}
                >
                  {file.description}
                </Typography>
              </Tooltip>
            )}

            <Box sx={{ display: 'flex', gap: 0.5, flexWrap: 'wrap', mt: 'auto' }}>
              <Chip
                label={file.extension || 'PDF'}
                size="small"
                sx={{
                  height: 22,
                  fontSize: '0.65rem',
                  borderColor: '#cbd5e1',
                  color: '#64748b'
                }}
                variant="outlined"
              />
            </Box>
          </CardContent>

          {/* Actions */}
          <CardActions 
            sx={{
              justifyContent: 'center',
              pt: 0.5,
              pb: 1.5,
              px: 1,
              borderTop: '1px solid #e5e7eb',
              gap: 0.5
            }}
          >
            <Button
              component={Link}
              to={`/file/`+ file.id}
              size="small"
              fullWidth
              sx={{
                textTransform: 'none',
                fontWeight: 600,
                color: '#3b82f6',
                fontSize: '0.8rem',
                padding: '4px 8px',
                '&:hover': {
                  backgroundColor: '#eff6ff',
                  textDecoration: 'none'
                }
              }}
            >
              View
            </Button>
          </CardActions>
        </Card>
      ))}
    </Box>
  )
}

export default PapersWithDelete