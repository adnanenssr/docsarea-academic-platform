import { Box, Button, Checkbox, IconButton, Pagination, Stack, Switch, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, ToggleButton, ToggleButtonGroup, Typography, CircularProgress } from '@mui/material'
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers'
import { DemoContainer } from '@mui/x-date-pickers/internals/demo'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from "dayjs";

import { LocalizationContext } from '@react-pdf-viewer/core'
import React, { useEffect, useState } from 'react'
import { CheckIcon, CopyIcon } from 'lucide-react';
import { useParams } from 'react-router-dom';
import CreateLink from './createLink';

function Link() {
    const { groupId } = useParams();
    const [links, setLinks] = useState([]);
    const [page, setPage] = useState(0);
    const [numPages, setNumPages] = useState(1);
    const [id, setId] = useState('');
    const [copy, setCopy] = useState(id);
    const [link, setLink] = useState('');
    const [isActive, setIsActive] = useState(false);
    const [expiration, setExpiration] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleCopy = async (id) => {
        try {
            setId(id);
            setLink(`http://localhost:3000/invitation/${id}`);
            await navigator.clipboard.writeText(`http://localhost:3000/invitation/${id}`);
            setCopy(id);
            setTimeout(() => {
                setCopy(false);
            }, 2000);
        } catch (err) {
            console.error("Failed to copy: ", err);
        }
    };

    const updateOne = async (isActive, expiration, linkId) => {
        try {
            const response = await fetch(`http://localhost:8082/link/update/${linkId}`, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    active: isActive,
                    expiresAt: expiration
                })
            });
            if (!response.ok) {
                throw new Error("Could not update this link");
            }
            setLinks((prev) =>
                prev.map((link) =>
                    link.id === linkId ? { ...link, active: isActive } : link
                )
            );
        } catch (err) {
            console.error("Error updating link:", err);
            setError("Failed to update link. Please try again.");
        }
    };

    useEffect(() => {
        const getLinks = async () => {
            try {
                setLoading(true);
                setError(null);

                const response = await fetch(`http://localhost:8082/get/links/${groupId}?page=${page}`, {
                    method: "GET",
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error("Could not fetch the links. Please try again later");
                }

                const data = await response.json();
                if (data && data.links) {
                    setLinks(data.links);
                    setNumPages(data.numPages || 1);
                } else {
                    setLinks([]);
                    setError("No links data received");
                }
            } catch (err) {
                console.error("Error fetching links:", err);
                setError(err.message || "An error occurred while fetching links");
                setLinks([]);
            } finally {
                setLoading(false);
            }
        };

        getLinks();
    }, [groupId, page]);

    if (loading) {
        return (
            <Stack
                display="flex"
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                sx={{ minHeight: "400px" }}
            >
                <CircularProgress sx={{ color: '#3b82f6' }} />
                <Typography variant="body1" sx={{ marginTop: "16px", color: '#64748b' }}>
                    Loading links...
                </Typography>
            </Stack>
        );
    }

    if (error) {
        return (
            <Stack
                display="flex"
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                sx={{ minHeight: "400px" }}
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

    if (!links || links.length === 0) {
        return (
            <Stack
                display="flex"
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                sx={{ minHeight: "400px" }}
            >
                <Typography variant="h6" sx={{ color: '#64748b' }}>
                    No links found
                </Typography>
            </Stack>
        );
    }

    return (
        <Box >
            <Box margin={4}>
            <CreateLink groupId={groupId} />
            </Box>
            <Box display="flex" sx={{ flexDirection: 'column', alignItems: "center" }}>
            <TableContainer component="paper">
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell width={30} sx={{ textAlign: "center" }}></TableCell>
                            <TableCell sx={{ textAlign: "center" }}>Active</TableCell>
                            <TableCell width="25%" sx={{ textAlign: "center" }}>Description</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>ROLE</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>File Review</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>Join Review</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>Invite Permission</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>Upload Permission</TableCell>
                            <TableCell sx={{ textAlign: "center" }}>Expiration Date</TableCell>
                            <TableCell width={30} sx={{ textAlign: "center" }}></TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {links.map((row) => (
                            <TableRow key={row.id}>
                                <TableCell><Checkbox /></TableCell>
                                <TableCell>
                                    <Switch
                                        checked={row.active}
                                        onChange={(e) => {
                                            updateOne(e.target.checked, row.expiresAt, row.id);
                                        }}
                                    />
                                </TableCell>
                                <TableCell>{row.description}</TableCell>
                                <TableCell>{row.role}</TableCell>
                                <TableCell><Checkbox checked={row.reviewFile} readOnly /></TableCell>
                                <TableCell><Checkbox checked={row.reviewJoinRequest} readOnly /></TableCell>
                                <TableCell><Checkbox checked={row.invitePermission} readOnly /></TableCell>
                                <TableCell>{row.uploadPermission}</TableCell>
                                <TableCell>
                                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <DemoContainer components={['DatePicker']}>
                                            <DatePicker value={dayjs(row.expiresAt)}  />
                                        </DemoContainer>
                                    </LocalizationProvider>
                                </TableCell>
                                <TableCell>
                                    <IconButton onClick={() => handleCopy(row.id)}>
                                        {(copy === row.id) ? <CheckIcon /> : <CopyIcon />}
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Pagination
                sx={{ margin: '24px' ,
                    '& .MuiPaginationItem-root': {
                        color: '#3b82f6'
                    },
                    '& .MuiPaginationItem-page.Mui-selected': {
                        backgroundColor: '#3b82f6',
                        color: 'white'
                    }
                 }}
                count={numPages}
                page={page + 1}
                onChange={(e, value) => {
                    setPage(value - 1);
                }}
            />
        </Box>
        </Box>
    );
}

export default Link;