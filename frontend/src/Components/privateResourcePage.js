import React, { useState, useEffect } from 'react'
import { Box, Button, Typography, Container } from '@mui/material'
import { Lock, Home, LayoutDashboard } from 'lucide-react'
import { useNavigate } from 'react-router-dom'

function PrivateResourcePage() {
  const navigate = useNavigate()
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const authenticated = async () => {
      try {
        const response = await fetch(`http://localhost:8082/authenticated`, {
          method: 'GET',
          credentials: 'include',
        })
        if (response.ok) {
          setIsAuthenticated(true)
        } else {
          setIsAuthenticated(false)
        }
      } catch (error) {
        console.error('Auth check failed:', error)
        setIsAuthenticated(false)
      } finally {
        setLoading(false)
      }
    }
    authenticated()
  }, [])

  const handleHome = () => {
    navigate('/')
  }

  const handleDashboard = () => {
    navigate('/u/dashboard')
  }

  if (loading) {
    return (
      <Box
        sx={{
          width: '100%',
          minHeight: '100vh',
          background: 'linear-gradient(135deg, #667eea 0%, #764ba2 25%, #f093fb 50%, #4facfe 75%, #00f2fe 100%)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <Typography variant="h6" sx={{ color: 'white', fontWeight: 600 }}>
          Loading...
        </Typography>
      </Box>
    )
  }

  return (
    <Box
      sx={{
        width: '100%',
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 25%, #f093fb 50%, #4facfe 75%, #00f2fe 100%)',
        backgroundSize: '400% 400%',
        animation: 'gradientShift 15s ease infinite',
        '@keyframes gradientShift': {
          '0%': { backgroundPosition: '0% 50%' },
          '50%': { backgroundPosition: '100% 50%' },
          '100%': { backgroundPosition: '0% 50%' },
        },
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'relative',
        overflow: 'hidden',
      }}
    >
      {/* Animated Background Elements */}
      <Box
        sx={{
          position: 'absolute',
          width: '300px',
          height: '300px',
          background: 'rgba(255, 255, 255, 0.1)',
          borderRadius: '50%',
          top: '-100px',
          left: '-100px',
          animation: 'float 20s ease-in-out infinite',
          '@keyframes float': {
            '0%, 100%': { transform: 'translate(0, 0)' },
            '50%': { transform: 'translate(30px, 30px)' },
          },
        }}
      />
      <Box
        sx={{
          position: 'absolute',
          width: '200px',
          height: '200px',
          background: 'rgba(255, 255, 255, 0.1)',
          borderRadius: '50%',
          bottom: '-50px',
          right: '-50px',
          animation: 'float 25s ease-in-out infinite reverse',
        }}
      />

      {/* Content */}
      <Container maxWidth="sm">
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            textAlign: 'center',
            position: 'relative',
            zIndex: 10,
            animation: 'slideUp 0.6s ease-out',
            '@keyframes slideUp': {
              from: {
                opacity: 0,
                transform: 'translateY(30px)',
              },
              to: {
                opacity: 1,
                transform: 'translateY(0)',
              },
            },
          }}
        >
          {/* Lock Icon */}
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              width: 120,
              height: 120,
              background: 'rgba(255, 255, 255, 0.95)',
              borderRadius: '50%',
              marginBottom: '32px',
              boxShadow: '0 20px 60px rgba(0, 0, 0, 0.2)',
              animation: 'pulse 2s ease-in-out infinite',
              '@keyframes pulse': {
                '0%, 100%': { boxShadow: '0 20px 60px rgba(0, 0, 0, 0.2)' },
                '50%': { boxShadow: '0 20px 60px rgba(0, 0, 0, 0.35)' },
              },
            }}
          >
            <Lock size={60} color="#667eea" />
          </Box>

          {/* Main Title */}
          <Typography
            variant="h3"
            sx={{
              color: 'white',
              fontWeight: 900,
              marginBottom: '16px',
              textShadow: '0 4px 12px rgba(0, 0, 0, 0.2)',
              fontSize: { xs: '2rem', sm: '2.5rem', md: '3rem' },
              letterSpacing: '-1px',
            }}
          >
            This Resource is Private
          </Typography>

          {/* Subtitle */}
          <Typography
            variant="h6"
            sx={{
              color: 'rgba(255, 255, 255, 0.9)',
              marginBottom: '48px',
              fontWeight: 400,
              fontSize: { xs: '1rem', sm: '1.1rem' },
              textShadow: '0 2px 8px rgba(0, 0, 0, 0.15)',
              maxWidth: '400px',
            }}
          >
            {isAuthenticated
              ? "You don't have access to this resource yet. Head to your dashboard to explore what's available."
              : 'You need to log in to access this resource. Please authenticate to continue.'}
          </Typography>

          {/* Action Buttons */}
          <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', justifyContent: 'center' }}>
            {isAuthenticated ? (
              <Button
                onClick={handleDashboard}
                startIcon={<LayoutDashboard size={20} />}
                variant="contained"
                sx={{
                  background: 'linear-gradient(135deg, #ffffff 0%, #f0f0f0 100%)',
                  color: '#667eea',
                  textTransform: 'none',
                  fontWeight: 700,
                  fontSize: '1rem',
                  px: 4,
                  py: 1.5,
                  borderRadius: '12px',
                  boxShadow: '0 8px 24px rgba(255, 255, 255, 0.3)',
                  transition: 'all 0.3s',
                  '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: '0 12px 32px rgba(255, 255, 255, 0.4)',
                  },
                }}
              >
                Go to Dashboard
              </Button>
            ) : (
              <Button
                onClick={handleHome}
                startIcon={<Home size={20} />}
                variant="contained"
                sx={{
                  background: 'linear-gradient(135deg, #ffffff 0%, #f0f0f0 100%)',
                  color: '#667eea',
                  textTransform: 'none',
                  fontWeight: 700,
                  fontSize: '1rem',
                  px: 4,
                  py: 1.5,
                  borderRadius: '12px',
                  boxShadow: '0 8px 24px rgba(255, 255, 255, 0.3)',
                  transition: 'all 0.3s',
                  '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: '0 12px 32px rgba(255, 255, 255, 0.4)',
                  },
                }}
              >
                Go to Home
              </Button>
            )}

            <Button
              onClick={() => navigate('/')}
              sx={{
                color: 'white',
                textTransform: 'none',
                fontWeight: 700,
                fontSize: '1rem',
                px: 4,
                py: 1.5,
                borderRadius: '12px',
                border: '2px solid rgba(255, 255, 255, 0.5)',
                transition: 'all 0.3s',
                backdropFilter: 'blur(10px)',
                '&:hover': {
                  backgroundColor: 'rgba(255, 255, 255, 0.15)',
                  borderColor: 'white',
                },
              }}
            >
              Back to Home
            </Button>
          </Box>
        </Box>
      </Container>
    </Box>
  )
}

export default PrivateResourcePage