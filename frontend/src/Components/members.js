import { Box, Button, Grid, Pagination, Stack, CircularProgress, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import Member from './member';
import { useParams, useNavigate } from 'react-router-dom';
import { groupCache } from './groupCache';
import SeeMember from './seeMember';
import AddMember from './addMember';

function Members() {
    const { groupId } = useParams();
    const navigate = useNavigate();
    const [page, setPage] = useState(0);
    const [members, setMembers] = useState([]);
    const [numPages, setNumPages] = useState(1);
    const [numElements, setNumElements] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const group = groupCache.get(groupId);

    useEffect(() => {
        const getMembers = async () => {
            try {
                setLoading(true);
                setError(null);

                const response = await fetch(`http://localhost:8082/get/members/${groupId}?page=${page}`, {
                    method: "GET",
                    credentials: "include"
                });

                if (response.status === 401) {
                    window.location.reload();
                    return;
                }

                if (!response.ok) {
                    throw new Error("Could not fetch members. Please try again later");
                }

                const data = await response.json();

                if (data && data.members) {
                    setMembers(data.members);
                    setNumPages(data.numPages || 1);
                    setNumElements(data.numElements || 0);
                } else {
                    setMembers([]);
                    setError("No members data received");
                }
            } catch (err) {
                setError(err.message || "An error occurred while fetching members");
                setMembers([]);
            } finally {
                setLoading(false);
            }
        };

        getMembers();
    }, [page, groupId]);

    if (loading) {
        return (
            <Stack 
                alignItems="center" 
                justifyContent="center" 
                sx={{ marginTop: "50px", minHeight: "400px" }}
            >
                <CircularProgress sx={{ color: '#3b82f6' }} />
                <Typography variant="body1" sx={{ marginTop: "16px", color: '#64748b' }}>
                    Loading members...
                </Typography>
            </Stack>
        );
    }

    if (error) {
        return (
            <Stack 
                alignItems="center" 
                justifyContent="center" 
                sx={{ marginTop: "50px", minHeight: "400px" }}
            >
                <Typography 
                    variant="h6" 
                    sx={{ color: '#e74c3c', marginBottom: "16px" }}
                >
                    {error}
                </Typography>
                <Button 
                    variant="contained" 
                    sx={{
                        backgroundColor: '#3b82f6',
                        textTransform: 'none',
                        fontWeight: 600,
                        '&:hover': {
                            backgroundColor: '#2563eb'
                        }
                    }}
                    onClick={() => window.location.reload()}
                >
                    Try Again
                </Button>
            </Stack>
        );
    }

    if (!members || members.length === 0) {
        return (
            <Stack 
                alignItems="center" 
                justifyContent="center" 
                sx={{ marginTop: "50px", minHeight: "400px" }}
            >
                <Typography variant="h6" sx={{ color: '#64748b' }}>
                    No members found
                </Typography>
            </Stack>
        );
    }

    return (
        <Stack  py={"15px"}>
            {group?.invitePermission && <Box marginLeft="200px" marginBottom={"15px"}>
            <AddMember groupId={groupId} />
            </Box>}
            <Stack alignItems="center" sx={{ marginTop: "15px" }}>
            <Grid container width="70vw" spacing={4}>
                {members.map((member) => (
                    <Grid size={2.4} key={member.id}>
                        {["OWNER" , "ADMIN"].includes(group?.role) ? <Member groupId={groupId} username={member.username} /> : <SeeMember groupId={groupId} username={member.username} /> }
                    </Grid>
                ))}
            </Grid>
            <Stack alignContent="center" margin={4}>
                <Pagination 
                    count={numPages} 
                    page={page + 1} 
                    onChange={(e, value) => {
                        setPage(value - 1);
                    }}
                    sx={{
                         margin: '24px' ,
                        '& .MuiPaginationItem-root': {
                            color: '#3b82f6'
                        },
                        '& .MuiPaginationItem-page.Mui-selected': {
                            backgroundColor: '#3b82f6',
                            color: 'white'
                        }
                    }}
                />
            </Stack>
            </Stack>
        </Stack>
    );
}

export default Members;