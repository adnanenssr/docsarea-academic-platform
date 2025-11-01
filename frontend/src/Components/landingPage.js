import React, { useState, useEffect } from 'react'
import { Box, Button, InputBase, Typography } from '@mui/material'
import { Search, FileText, LayoutDashboard } from 'lucide-react'
import { useNavigate } from 'react-router-dom'

function LandingPage() {
  const [searchQuery, setSearchQuery] = useState('')
  const navigate = useNavigate()
  const [isAuthenticated, setIsAuthenticated] = useState(false)

  useEffect(() => {
    const authenticated = async () => {
      const response = await fetch(`http://localhost:8082/authenticated`, {
        method: 'GET',
        credentials: 'include',
      })
      if (response.ok) {
        setIsAuthenticated(true)
      } else {
        setIsAuthenticated(false)
      }
    }
    authenticated()
  }, [])

  const handleSearch = (e) => {
    e.preventDefault()
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery)}&page=0`)
    }
  }

  const handleSearchKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch(e)
    }
  }

  const handleLogin = () => {
    navigate('/login')
  }

  const handleRegister = () => {
    navigate('/register')
  }

  const handleDashboard = () => {
    navigate('/u/dashboard')
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

      {/* Header */}
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          padding: '24px 48px',
          position: 'relative',
          zIndex: 10,
        }}
      >
        {/* Logo */}
        <Box
          sx={{
            display: 'flex',
            alignItems: 'center',
            cursor: 'pointer',
            gap: 2,
          }}
          onClick={() => navigate('/')}
        >
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              width: 50,
              height: 50,
              background: 'rgba(255, 255, 255, 0.95)',
              borderRadius: '12px',
              boxShadow: '0 8px 24px rgba(0, 0, 0, 0.2)',
              transition: 'transform 0.3s',
              '&:hover': {
                transform: 'scale(1.08)',
              },
            }}
          >
            <FileText size={28} color="#667eea" />
          </Box>
          <Typography
            variant="h5"
            sx={{
              fontWeight: 800,
              color: 'white',
              fontSize: '1.8rem',
              letterSpacing: '-1px',
              textShadow: '0 2px 8px rgba(0, 0, 0, 0.2)',
            }}
          >
            DocsArea
          </Typography>
        </Box>

        {/* Auth Buttons */}
        <Box sx={{ display: 'flex', gap: 2 }}>
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
                px: 3,
                py: 1.2,
                borderRadius: '10px',
                boxShadow: '0 8px 24px rgba(255, 255, 255, 0.3)',
                transition: 'all 0.3s',
                '&:hover': {
                  transform: 'translateY(-3px)',
                  boxShadow: '0 12px 32px rgba(255, 255, 255, 0.4)',
                },
              }}
            >
              Dashboard
            </Button>
          ) : (
            <>
              <Button
                onClick={handleLogin}
                sx={{
                  color: 'white',
                  textTransform: 'none',
                  fontWeight: 700,
                  fontSize: '1rem',
                  px: 3,
                  py: 1.2,
                  borderRadius: '10px',
                  border: '2px solid rgba(255, 255, 255, 0.5)',
                  transition: 'all 0.3s',
                  backdropFilter: 'blur(10px)',
                  '&:hover': {
                    backgroundColor: 'rgba(255, 255, 255, 0.15)',
                    borderColor: 'white',
                  },
                }}
              >
                Login
              </Button>
              <Button
                onClick={handleRegister}
                variant="contained"
                sx={{
                  background: 'linear-gradient(135deg, #ffffff 0%, #f0f0f0 100%)',
                  color: '#667eea',
                  textTransform: 'none',
                  fontWeight: 700,
                  fontSize: '1rem',
                  px: 3,
                  py: 1.2,
                  borderRadius: '10px',
                  boxShadow: '0 8px 24px rgba(255, 255, 255, 0.3)',
                  transition: 'all 0.3s',
                  '&:hover': {
                    transform: 'translateY(-3px)',
                    boxShadow: '0 12px 32px rgba(255, 255, 255, 0.4)',
                  },
                }}
              >
                Register
              </Button>
            </>
          )}
        </Box>
      </Box>

      {/* Main Content */}
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: 'calc(100vh - 100px)',
          position: 'relative',
          zIndex: 5,
          px: 2,
        }}
      >
        {/* Heading */}
        <Typography
          variant="h2"
          sx={{
            color: 'white',
            fontWeight: 900,
            fontSize: { xs: '2.5rem', sm: '3.5rem', md: '4.5rem' },
            textAlign: 'center',
            marginBottom: '16px',
            textShadow: '0 4px 12px rgba(0, 0, 0, 0.2)',
            lineHeight: 1.2,
            letterSpacing: '-2px',
          }}
        >
          Discover Excellence in Research
        </Typography>

        {/* Subtitle */}
        <Typography
          variant="h6"
          sx={{
            color: 'rgba(255, 255, 255, 0.9)',
            fontSize: { xs: '1rem', md: '1.3rem' },
            textAlign: 'center',
            marginBottom: '48px',
            maxWidth: '600px',
            fontWeight: 400,
            textShadow: '0 2px 8px rgba(0, 0, 0, 0.15)',
          }}
        >
          Search through millions of research papers, documents, and scholarly articles
        </Typography>

        {/* Search Bar */}
        <Box
          component="form"
          onSubmit={handleSearch}
          sx={{
            display: 'flex',
            justifyContent: 'center',
            width: '100%',
            maxWidth: '700px',
            marginBottom: '32px',
          }}
        >
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              backgroundColor: 'rgba(255, 255, 255, 0.95)',
              borderRadius: '16px',
              px: 3,
              py: 1.5,
              width: '100%',
              transition: 'all 0.3s ease-in-out',
              boxShadow: '0 12px 40px rgba(0, 0, 0, 0.15)',
              '&:hover': {
                boxShadow: '0 16px 50px rgba(0, 0, 0, 0.2)',
              },
              '&:focus-within': {
                transform: 'translateY(-4px)',
                boxShadow: '0 20px 60px rgba(0, 0, 0, 0.25)',
              },
            }}
          >
            <Search size={24} style={{ color: '#667eea', marginRight: '16px', flexShrink: 0 }} />
            <InputBase
              placeholder="Search papers, authors, keywords..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              onKeyPress={handleSearchKeyPress}
              sx={{
                flex: 1,
                color: '#1e293b',
                fontSize: '1.05rem',
                fontWeight: 500,
                '& ::placeholder': {
                  color: '#94a3b8',
                  opacity: 1,
                  fontWeight: 400,
                },
              }}
            />
            {searchQuery && (
              <Box
                onClick={handleSearch}
                sx={{
                  ml: 2,
                  backgroundColor: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                  color: 'white',
                  width: 44,
                  height: 44,
                  borderRadius: '10px',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  cursor: 'pointer',
                  transition: 'all 0.3s',
                  '&:hover': {
                    transform: 'scale(1.05)',
                  },
                }}
              >
                <Search size={20} />
              </Box>
            )}
          </Box>
        </Box>

        {/* Quick Search Tags */}
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', justifyContent: 'center' }}>
          {['Machine Learning', 'Quantum Physics', 'Biotechnology'].map((tag) => (
            <Button
              key={tag}
              onClick={() => {
                setSearchQuery(tag)
                navigate(`/search?q=${encodeURIComponent(tag)}&page=0`)
              }}
              sx={{
                color: 'white',
                border: '1.5px solid rgba(255, 255, 255, 0.5)',
                textTransform: 'none',
                fontWeight: 600,
                fontSize: '0.9rem',
                px: 2.5,
                py: 0.8,
                borderRadius: '20px',
                backdropFilter: 'blur(10px)',
                transition: 'all 0.3s',
                '&:hover': {
                  backgroundColor: 'rgba(255, 255, 255, 0.15)',
                  borderColor: 'white',
                },
              }}
            >
              {tag}
            </Button>
          ))}
        </Box>
      </Box>
    </Box>
  )
}

export default LandingPage