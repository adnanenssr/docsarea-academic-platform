import { Box, Button, Card, CardContent, CardMedia, Typography, CircularProgress } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';

function PaperSideBarCard({ title, fileId }) {
  const [files, setFiles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const getRecommendations = async () => {
      try {
        setLoading(true);
        setError(null);

        const response = await fetch(`http://localhost:8082/get/recommendations?q=${encodeURIComponent(title)}&fileId=${fileId}`, {
          method: "GET",
          credentials: "include"
        });

        if (!response.ok) {
          throw new Error("Could not fetch recommendations");
        }

        const data = await response.json();
        if (data) {
          setFiles(data);
        }
      } catch (err) {
        console.error(err);
        setError(err.message);
        setFiles([]);
      } finally {
        setLoading(false);
      }
    };

    if (title && fileId) {
      getRecommendations();
    }
  }, [title, fileId]);

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', py: 3 }}>
        <CircularProgress sx={{ color: '#3b82f6' }} />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ p: 2 }}>
        <Typography variant="body2" color="error">
          {error}
        </Typography>
      </Box>
    );
  }

  if (!files || files.length === 0) {
    return (
      <Box sx={{ p: 2 }}>
        <Typography variant="body2" color="text.secondary">
          No recommendations available
        </Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
      {files.map((file) => (
        <Button
          key={file.id}
          onClick={() => navigate(`/file/${file.id}`)}
          sx={{
            textTransform: 'none',
            padding: 0,
            display: 'flex',
            justifyContent: 'flex-start',
            '&:hover': {
              opacity: 0.9
            }
          }}
        >
          <Card
            sx={{
              display: 'flex',
              width: '100%',
              height: '150px',
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              background: 'linear-gradient(135deg, #ffffff 0%, #f8fafc 100%)',
              transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
              '&:hover': {
                boxShadow: '0 8px 16px rgba(59, 130, 246, 0.12)',
                borderColor: '#3b82f6'
              }
            }}
            elevation={0}
          >
            {/* Fixed width thumbnail container */}
            <Box
              sx={{
                width: 120,
                height: '100%',
                flexShrink: 0,
                backgroundColor: '#f1f5f9',
                backgroundImage: `url(http://localhost:8082/thumbnail/${file.id})`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat'
              }}
            />

            <Box
              sx={{
                flex: 1,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'flex-start',
                justifyContent: 'flex-start',
                p: 2
              }}
            >
              <Typography
                variant='h6'
                sx={{
                  fontWeight: 600,
                  fontSize: '14px',
                  color: '#1e293b',
                  mb: 1,
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  overflow: 'hidden',
                  textAlign: 'left'
                }}
              >
                {file.title}
              </Typography>
              <Typography
                variant='h6'
                sx={{
                  fontWeight: 400,
                  fontSize: '14px',
                  color: '#1e293b',
                  mb: 1,
                  display: '-webkit-box',
                  WebkitLineClamp: 2,
                  WebkitBoxOrient: 'vertical',
                  overflow: 'hidden',
                  textAlign: 'left'
                }}
              >
                {file.description}
              </Typography>

              <Typography
                variant='caption'
                sx={{
                  color: '#64748b',
                  fontSize: '12px',
                  textAlign: 'left'
                }}
              >
                by {file.groupId ? file.groupId : file.owner}
              </Typography>
            </Box>
          </Card>
        </Button>
      ))}
    </Box>
  );
}

export default PaperSideBarCard;