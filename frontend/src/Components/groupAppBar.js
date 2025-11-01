import { AppBar, Button, Container, Stack, Toolbar, Box, Avatar, Menu, Typography, MenuItem, ListItemIcon, ListItemText, Divider, IconButton } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { groupCache } from './groupCache'; 
import { LayoutDashboard, SquareArrowOutUpRight, User } from 'lucide-react';
import { Logout } from '@mui/icons-material';

function GroupAppBar() {
    const { groupId } = useParams();
    const [cached, setCached] = useState(null);
    const [activeTab, setActiveTab] = useState("Dashboard");
    const navigate = useNavigate();
    const location = useLocation();
    const [user , setUser] = useState(null) ;
    const [anchorEl, setAnchorEl] = useState(null);

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };


    const handleLogout = async () => {
    handleMenuClose();
        
    const response = await fetch("http://localhost:8082/auth/logout" , {
      method : "POST" ,
      headers : {
        "content-type" : "application/json"
      },
      credentials : "include"
    })
    navigate("") ;
  

        
    };

    const handleDashboard = () => {
        navigate('/u/dashboard');
        handleMenuClose();
    };

    const handleProfile = () => {
        navigate('/u/profile');
        handleMenuClose();
    }; 

    useEffect(() => {
        const getUser = async () => {
            try {
                const response = await fetch(`http://localhost:8082/get/user`, {
                    method: "GET",
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error("Could not fetch user Please try again later");
                }

                const data = await response.json();
                if (data) {
                    setUser(data);
                }
            } catch (error) {
                console.error("Error fetching user:", error);
            }
        }
        getUser() ;
    }, []) // Added empty dependency array to run only once

    useEffect(() => {
        const cachedGroup = groupCache.get(groupId);
        setCached(cachedGroup);
    }, [groupId]);

    const buildPages = () => {
        const pages = new Map();
        
        if (!cached) return pages;

        pages.set("Dashboard", `/group/${groupId}`);
        pages.set("Files", `/group/${groupId}/files`);
        pages.set("Members", `/group/${groupId}/members/`);
        
        if (cached.role === "OWNER" || cached.invitePermission) {
            pages.set("Links", `/group/${groupId}/links`);
        }
        
        if (["OWNER", "ADMIN", "MODERATOR"].includes(cached.role)) {
            pages.set("Reviews", `/group/${groupId}/reviews/`);
        }
        
        pages.set("Insights", `/group/${groupId}/insights`);
        
        if (cached.role === "OWNER") {
            pages.set("Settings", `/group/${groupId}/settings`);
        }

        return pages;
    };

    const pages = buildPages();

    useEffect(() => {
        const currentPath = location.pathname;
        for (let [pageName, path] of pages.entries()) {
            // Check if the current path starts with the page's path for better matching
            // Especially for nested routes like /members/settings
            if (currentPath == path) { 
                setActiveTab(pageName);
                break;
            }
        }
    }, [location.pathname, groupId, pages]);

    const handleTabClick = (page) => {
        setActiveTab(page);
        navigate(pages.get(page));
    };

    return (
        <AppBar 
            position="fixed"
            sx={{
                height: "72px",
                backgroundColor: "#fafafa",
                borderBottom: "1px solid #e8e8e8",
                boxShadow: "none",
                zIndex: (theme) => theme.zIndex.drawer + 1
            }} 
            elevation={0}
        >
            <Container maxWidth="xl" sx={{ height: "100%" }}>
                <Toolbar sx={{ 
                    height: "100%", 
                    // Changed to space-between to push items to the edges
                    justifyContent: "space-between", 
                    minHeight: "72px !important",
                    padding: 0
                }}>
                    
                    {/* 1. Navigation Tabs (Left Side) */}
                    <Stack 
                        direction="row" 
                        spacing={6}
                        sx={{ 
                            alignItems: 'center' ,
                            height: "100%"
                        }}
                    >
                        {Array.from(pages.keys()).map((page) => (
                            <Box key={page} sx={{ position: "relative" }}>
                                <Button
                                    onClick={() => handleTabClick(page)}
                                    variant="text"
                                    disableRipple
                                    sx={{
                                        color: activeTab === page ? "#000000" : "#666666",
                                        backgroundColor: "transparent",
                                        fontFamily: "'Inter', -apple-system, BlinkMacSystemFont, sans-serif",
                                        fontWeight: activeTab === page ? 500 : 400,
                                        fontSize: "15px",
                                        letterSpacing: "-0.01em",
                                        textTransform: "none",
                                        minWidth: "auto",
                                        padding: "12px 0",
                                        margin: 0,
                                        border: "none",
                                        borderRadius: 0,
                                        transition: "color 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94)",
                                        "&:hover": {
                                            backgroundColor: "transparent",
                                            color: "#000000",
                                        },
                                        "&:focus": {
                                            outline: "none"
                                        }
                                    }}
                                >
                                    {page}
                                </Button>
                                {activeTab === page && (
                                    <Box
                                        sx={{
                                            position: "absolute",
                                            bottom: 0,
                                            left: 0,
                                            right: 0,
                                            height: "1px",
                                            backgroundColor: "#000000",
                                            animation: "slideIn 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94)",
                                            "@keyframes slideIn": {
                                                "0%": {
                                                    transform: "scaleX(0)",
                                                    transformOrigin: "center"
                                                },
                                                "100%": {
                                                    transform: "scaleX(1)",
                                                    transformOrigin: "center"
                                                }
                                            }
                                        }}
                                    />
                                )}
                            </Box>
                        ))}
                        <IconButton onClick={() => { navigate(`/profile/group/${groupId}`)}}>
                        <SquareArrowOutUpRight />
                        </IconButton>
                    </Stack>
                    
                    {/* 2. User Avatar and Menu (Right Side) */}
                    {/* The `ml: 'auto'` is the key to pushing this Box to the far right. */}
                    <Box sx={{ ml: 'auto', display: 'flex', alignItems: 'center' }}>
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
                                    width: 40,
                                    height: 40,
                                    border: '2px solid #e2e8f0',
                                    cursor: 'pointer',
                                    transition: 'all 0.2s',
                                    '&:hover': {
                                        borderColor: '#3b82f6',
                                        boxShadow: '0 0 0 4px rgba(59, 130, 246, 0.1)'
                                    }
                                }}
                            >
                                {user?.username.charAt(0)}
                            </Avatar>
                        </IconButton>
                        
                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                            PaperProps={{
                                sx: {
                                    mt: 1.5,
                                    minWidth: 220,
                                    borderRadius: '12px',
                                    boxShadow: '0 4px 20px rgba(0, 0, 0, 0.1)',
                                    border: '1px solid #e5e7eb'
                                }
                            }}
                            transformOrigin={{ horizontal: 'right', vertical: 'top' }}
                            anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
                        >
                            <Box sx={{ px: 2, py: 1.5, borderBottom: '1px solid #e5e7eb' }}>
                                <Typography variant="subtitle2" sx={{ fontWeight: 600, color: '#1e293b' }}>
                                    {user?.username}
                                </Typography>
                                <Typography variant="caption" sx={{ color: '#64748b' }}>
                                    {user?.email}
                                </Typography>
                            </Box>
                            
                            <MenuItem onClick={handleProfile} sx={{ py: 1.5, px: 2 }}>
                                <ListItemIcon>
                                    <User size={18} />
                                </ListItemIcon>
                                <ListItemText>Profile</ListItemText>
                            </MenuItem>
                            
                            <MenuItem onClick={handleDashboard} sx={{ py: 1.5, px: 2 }}>
                                <ListItemIcon>
                                    <LayoutDashboard size={18} />
                                </ListItemIcon>
                                <ListItemText>Dashboard</ListItemText>
                            </MenuItem>
                            
                            <Divider sx={{ my: 0.5 }} />
                            
                            <MenuItem 
                                onClick={handleLogout} 
                                sx={{ 
                                    py: 1.5, 
                                    px: 2,
                                    color: '#ef4444',
                                    '&:hover': {
                                        backgroundColor: '#fef2f2'
                                    }
                                }}
                            >
                                <ListItemIcon>
                                    <Logout sx={{ color: '#ef4444' }} /> {/* Changed lucide-react to MUI icon for consistency in color */}
                                </ListItemIcon>
                                <ListItemText>Logout</ListItemText>
                            </MenuItem>
                        </Menu>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default GroupAppBar;