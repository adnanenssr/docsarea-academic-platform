import { 
  AppBar, 
  IconButton, 
  Toolbar, 
  Box, 
  InputBase, 
  Typography,
  Avatar,
  Menu,
  MenuItem,
  Button,
  Divider,
  ListItemIcon,
  ListItemText
} from '@mui/material'
import { 
  Search, 
  FileText, 
  LogOut, 
  LayoutDashboard,
  User,
} from 'lucide-react'
import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'

function Appbar() {
  const navigate = useNavigate()
  const [searchQuery, setSearchQuery] = useState('')
  const [anchorEl, setAnchorEl] = useState(null)
  const [isAuthenticated, setIsAuthenticated] = useState(true)
  const [user, setUser] = useState(null)
  const location = useLocation()

  useEffect(() => {
    const getUser = async () => {
      try {
        const response = await fetch(`http://localhost:8082/get/user`, {
          method: "GET",
          credentials: "include"
        })

        if (!response.ok) {
          throw new Error("Could not fetch user")
        }

        const data = await response.json()
        if (data) {
          setUser(data)
        }
      } catch (error) {
        console.error("Error fetching user:", error)
      }
    }
    getUser()
  }, [])

  useEffect(() => {
    const authenticated = async () => {
      const response = await fetch(`http://localhost:8082/authenticated`, {
        method: "GET",
        credentials: "include"
      })
      if (response.ok) {
        setIsAuthenticated(true)
      } else {
        setIsAuthenticated(false)
      }
    }
    authenticated()
  }, [location])

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget)
  }

  const handleMenuClose = () => {
    setAnchorEl(null)
  }

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

  const handleLogout = async () => {
    const response = await fetch("http://localhost:8082/auth/logout", {
      method: "POST",
      headers: {
        "content-type": "application/json"
      },
      credentials: "include"
    })
    setIsAuthenticated(false)
    handleMenuClose()
    navigate("/")
  }

  const handleDashboard = () => {
    navigate('/u/dashboard')
    handleMenuClose()
  }

  const handleProfile = () => {
    navigate('/u/profile')
    handleMenuClose()
  }

  const handleLogin = () => {
    navigate('/login')
  }

  const handleRegister = () => {
    navigate('/register')
  }

  return (
    <AppBar 
      position="fixed"
      elevation={0}
      sx={{
        backgroundColor: '#ffffff',
        borderBottom: '1px solid #f0f0f0',
        zIndex: (theme) => theme.zIndex.drawer + 1,
        boxShadow: 'none'
      }}
    >
      <Toolbar sx={{ 
        px: { xs: 2, sm: 3, md: 4 }, 
        height: '64px',
        minHeight: '64px !important',
        display: 'flex',
        justifyContent: 'space-between',
        gap: 2
      }}>
        {/* Logo Section */}
        <Box sx={{ 
          display: 'flex',
          alignItems: 'center',
          cursor: 'pointer',
          minWidth: 'fit-content',
          flexShrink: 0
        }}
        onClick={() => navigate('/')}
        >
          <Box sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            width: 36,
            height: 36,
            background: '#1f2937',
            borderRadius: '8px',
            mr: 2,
            transition: 'opacity 0.2s',
            '&:hover': {
              opacity: 0.8
            }
          }}>
            <FileText size={20} color="white" />
          </Box>
          <Typography 
            variant="h6"
            sx={{
              fontWeight: 700,
              fontSize: '1.1rem',
              color: '#1f2937',
              fontFamily: "'Inter', -apple-system, sans-serif",
              letterSpacing: '-0.3px',
              display: { xs: 'none', sm: 'block' }
            }}
          >
            DocsArea
          </Typography>
        </Box>

        {/* Centered Search Bar */}
        <Box 
          component="form"
          onSubmit={handleSearch}
          sx={{
            display: 'flex',
            justifyContent: 'center',
            flexGrow: 1,
            mx: { xs: 1, md: 2 },
            maxWidth: '550px'
          }}
        >
          <Box sx={{
            display: 'flex',
            alignItems: 'center',
            backgroundColor: '#f9fafb',
            border: '1px solid #e5e7eb',
            borderRadius: '8px',
            px: 2.5,
            py: 1,
            width: '100%',
            transition: 'all 0.2s ease-in-out',
            '&:hover': {
              backgroundColor: '#ffffff',
              borderColor: '#d1d5db',
            },
            '&:focus-within': {
              backgroundColor: '#ffffff',
              borderColor: '#9ca3af',
              boxShadow: '0 0 0 3px rgba(31, 41, 55, 0.05)'
            }
          }}>
            <Search size={18} style={{ color: '#9ca3af', marginRight: '10px', flexShrink: 0 }} />
            <InputBase 
              placeholder="Search documents..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              onKeyPress={handleSearchKeyPress}
              sx={{
                flex: 1,
                color: '#1f2937',
                fontSize: '0.9rem',
                fontWeight: 400,
                '& ::placeholder': {
                  color: '#9ca3af',
                  opacity: 1,
                  fontWeight: 400
                }
              }}
            />
            {searchQuery && (
              <IconButton
                size="small"
                onClick={handleSearch}
                sx={{
                  ml: 1,
                  backgroundColor: '#1f2937',
                  color: 'white',
                  width: 28,
                  height: 28,
                  '&:hover': {
                    backgroundColor: '#374151'
                  }
                }}
              >
                <Search size={14} />
              </IconButton>
            )}
          </Box>
        </Box>

        {/* Right Section - Auth */}
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, minWidth: 'fit-content', flexShrink: 0 }}>
          {isAuthenticated ? (
            <>
              {/* User Avatar and Menu */}
              <IconButton
                onClick={handleMenuOpen}
                sx={{
                  padding: 0,
                  '&:hover': {
                    backgroundColor: 'transparent'
                  }
                }}
              >
                <Avatar 
                  src={`http://localhost:8082/api/get/avatar`}
                  alt={user?.username}
                  sx={{
                    width: 36,
                    height: 36,
                    border: '1px solid #e5e7eb',
                    cursor: 'pointer',
                    transition: 'all 0.2s',
                    backgroundColor: '#f3f4f6',
                    fontSize: '0.875rem',
                    fontWeight: 600,
                    color: '#1f2937',
                    '&:hover': {
                      borderColor: '#9ca3af',
                    }
                  }}
                >
                  {user?.username?.charAt(0).toUpperCase()}
                </Avatar>
              </IconButton>

              <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
                PaperProps={{
                  sx: {
                    mt: 1.5,
                    minWidth: 200,
                    borderRadius: '8px',
                    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
                    border: '1px solid #e5e7eb'
                  }
                }}
                transformOrigin={{ horizontal: 'right', vertical: 'top' }}
                anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
              >
                <Box sx={{ px: 2, py: 1.25, borderBottom: '1px solid #f0f0f0' }}>
                  <Typography variant="subtitle2" sx={{ fontWeight: 600, color: '#1f2937', fontSize: '0.9rem' }}>
                    {user?.username}
                  </Typography>
                  <Typography variant="caption" sx={{ color: '#9ca3af', fontSize: '0.8rem' }}>
                    {user?.email}
                  </Typography>
                </Box>

                <MenuItem onClick={handleProfile} sx={{ py: 1, px: 2, fontSize: '0.9rem', color: '#1f2937' }}>
                  <ListItemIcon sx={{ minWidth: 32 }}>
                    <User size={16} color="#6b7280" />
                  </ListItemIcon>
                  <ListItemText>Profile</ListItemText>
                </MenuItem>

                <MenuItem onClick={handleDashboard} sx={{ py: 1, px: 2, fontSize: '0.9rem', color: '#1f2937' }}>
                  <ListItemIcon sx={{ minWidth: 32 }}>
                    <LayoutDashboard size={16} color="#6b7280" />
                  </ListItemIcon>
                  <ListItemText>Dashboard</ListItemText>
                </MenuItem>

                <Divider sx={{ my: 0.5 }} />

                <MenuItem 
                  onClick={handleLogout} 
                  sx={{ 
                    py: 1, 
                    px: 2,
                    fontSize: '0.9rem',
                    color: '#6b7280',
                    '&:hover': {
                      backgroundColor: '#f9fafb'
                    }
                  }}
                >
                  <ListItemIcon sx={{ minWidth: 32 }}>
                    <LogOut size={16} color="#6b7280" />
                  </ListItemIcon>
                  <ListItemText>Logout</ListItemText>
                </MenuItem>
              </Menu>
            </>
          ) : (
            <>
              {/* Login/Register Buttons */}
              <Button
                onClick={handleLogin}
                sx={{
                  color: '#6b7280',
                  textTransform: 'none',
                  fontWeight: 500,
                  fontSize: '0.9rem',
                  px: 2,
                  py: 0.75,
                  borderRadius: '6px',
                  transition: 'all 0.2s',
                  '&:hover': {
                    backgroundColor: '#f9fafb',
                    color: '#1f2937'
                  }
                }}
              >
                Login
              </Button>
              <Button
                onClick={handleRegister}
                variant="contained"
                sx={{
                  backgroundColor: '#1f2937',
                  color: 'white',
                  textTransform: 'none',
                  fontWeight: 500,
                  fontSize: '0.9rem',
                  px: 2,
                  py: 0.75,
                  borderRadius: '6px',
                  transition: 'all 0.2s',
                  '&:hover': {
                    backgroundColor: '#374151',
                  }
                }}
              >
                Register
              </Button>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  )
}

export default Appbar