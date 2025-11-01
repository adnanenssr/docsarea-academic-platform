import { Avatar, Box, Button, Paper, Stack, Tab, Tabs, Typography, CircularProgress, Pagination, Chip, Container } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import FileViewPapers from './fileViewPapers';

function GroupProfileDetails() {
    const [page, setPage] = useState(0);
    const [files, setFiles] = useState(null);
    const [numPages, setNumPages] = useState(1);
    const [numElements, setNumElements] = useState(0);
    const [tab, setActiveTab] = useState(0);
    const [group, setGroup] = useState(null);
    const [loading, setLoading] = useState(true);
    const [filesLoading, setFilesLoading] = useState(false);
    const [error, setError] = useState(null);
    const { groupId } = useParams();
    const [profile , setProfile] = useState(null) ;
    const [coverPic , setCoverPic] = useState(null) ;



    useEffect(() => {
        const getGroup = async () => {
            try {
                setLoading(true);
                setError(null);

                const response = await fetch(`http://localhost:8082/group/${groupId}`, {
                    method: "GET",
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error("Could not fetch group. Please try again later");
                }

                const data = await response.json();
                if (data) {
                    setGroup(data);
                    setProfile(data.profilePicUrl) ;
                    setCoverPic(data.coverImg) ;
                }
            } catch (err) {
                console.error(err);
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        if (groupId) {
            getGroup();
        }
    }, [groupId]);

    useEffect(() => {
        const getFiles = async () => {
            try {
                setFilesLoading(true);
                setError(null);

                const response = await fetch(`http://localhost:8082/get/group/${groupId}/files/published?page=${page}`, {
                    method: "GET",
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error("Could not fetch files. Please try again later");
                }

                const data = await response.json();
                if (data) {
                    setFiles(data.files);
                    setNumPages(data.numPages);
                    setNumElements(data.numElements);
                    

                }
            } catch (err) {
                console.error(err);
                setError(err.message);
                setFiles([]);
            } finally {
                setFilesLoading(false);
            }
        };

        if (groupId) {
            getFiles();
        }
    }, [page, groupId]);

    if (loading) {
        return (
            <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <CircularProgress sx={{ color: '#3b82f6' }} />
            </Box>
        );
    }

    if (error && !group) {
        return (
            <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Typography color="error" variant="h6">{error}</Typography>
            </Box>
        );
    }

    return (
        <Box sx={{
            
            minHeight: '100vh',
            backgroundColor: '#fafafa',
            py: 4
        }}>
            {/* Header Cover */}
            <Container maxWidth="lg">
            <Paper 
                sx={{ 
                    overflow: "hidden",
                    mb: 3
                }} 
                elevation={2}
            >    
                <Box sx={{
                    backgroundImage: !coverPic ? 'url("/background.jpg")' : `url("http://localhost:8082/group/${groupId}/get/cover")`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    position: "relative",
                    height: 240,
                    width: "100%"
                }}>
                    <Avatar variant='rounded' src={`http://localhost:8082/group/${groupId}/get/avatar`} sx={{
                        position: "absolute",
                        bottom: 0,
                        left: 32,
                        transform: "translateY(50%)",
                        width: 140,
                        height: 140,
                        border: "5px solid white",
                        borderRadius: "16px",
                        boxShadow: "0 4px 12px rgba(0,0,0,0.15)"
                    }}>
                        {group.name.slice(0,1)}
                    </Avatar>

                    <Button 
                        variant='contained' 
                        sx={{ 
                            position: "absolute", 
                            bottom: 24, 
                            right: 32,
                            borderRadius: "8px",
                            textTransform: "none",
                            px: 3,
                            py: 1.2,
                            fontWeight: 600,
                            boxShadow: "0 2px 8px rgba(0,0,0,0.15)"
                        }}
                    >
                        Request to Join
                    </Button>
                </Box>
                
                <Box sx={{ pt: 9, pb: 3, px: 4 }}>
                    <Typography variant='h4' sx={{ fontWeight: 700, mb: 1.5, color: '#1a1a1a' }}>
                        {group && group.name}
                    </Typography>
                    {group && (
                        <Chip 
                            label={group.theme} 
                            sx={{
                                fontWeight: 600,
                                px: 1.5,
                                height: 32,
                                borderRadius: "8px"
                            }}
                        />
                    )}
                </Box>
            </Paper>
                

            {/* Content Card */}
            <Box sx={{ maxWidth: 'lg', marginX: 'auto' }}>
                <Paper
                    sx={{
                        borderRadius: "12px",
                        overflow: "hidden",
                        border: '1px solid #e5e7eb'
                    }}
                    elevation={1}
                >
                    <Tabs
                        value={tab}
                        sx={{
                            borderBottom: "1px solid #e5e7eb",
                            px: 3,
                            '& .MuiTab-root': {
                                textTransform: 'none',
                                fontWeight: 600,
                                fontSize: '15px',
                                minHeight: 56,
                                color: '#64748b',
                                '&.Mui-selected': {
                                    color: '#3b82f6'
                                }
                            },
                            '& .MuiTabs-indicator': {
                                backgroundColor: '#3b82f6'
                            }
                        }}
                        onChange={(e, newValue) => {
                            setActiveTab(newValue);
                        }}
                    >
                        <Tab label="Papers" value={0} />
                        <Tab label="About" value={1} />
                    </Tabs>

                    <Box sx={{ p: 4 }}>
                        {tab === 0 && (
                            <>
                                {filesLoading ? (
                                    <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
                                        <CircularProgress sx={{ color: '#3b82f6' }} />
                                    </Box>
                                ) : files && files.length > 0 ? (
                                    <>
                                        <FileViewPapers files={files} />
                                        {numPages > 1 && (
                                            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                                                <Pagination
                                                    count={numPages}
                                                    page={page + 1}
                                                    onChange={(e, value) => setPage(value - 1)}
                                                    sx={{
                                                        '& .MuiPaginationItem-root': {
                                                            color: '#3b82f6'
                                                        },
                                                        '& .MuiPaginationItem-page.Mui-selected': {
                                                            backgroundColor: '#3b82f6',
                                                            color: 'white'
                                                        }
                                                    }}
                                                />
                                            </Box>
                                        )}
                                    </>
                                ) : (
                                    <Typography variant="body1" color="text.secondary">
                                        No files published yet
                                    </Typography>
                                )}
                            </>
                        )}

                        {tab === 1 && (
                            <Typography
                                variant="body1"
                                sx={{
                                    lineHeight: 1.8,
                                    color: '#4a4a4a',
                                    fontSize: '15px'
                                }}
                            >
                                {group?.description || "No description available"}
                            </Typography>
                        )}
                    </Box>
                </Paper>
            </Box>
            </Container>
        </Box>
    );
}

export default GroupProfileDetails;