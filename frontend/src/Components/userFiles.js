import { Box, Button, Pagination, Stack, CircularProgress } from '@mui/material'
import React, { useEffect, useState } from 'react'
import PaperList from './paper';
import SideMenu from './sideMenue';
import SideMenuWrapper from './sideMenuWrapper';
import UploadDialog, { UploadForm } from './upload';

function UserFiles() {
    const [page, setPage] = useState(0);
    const [files, setFiles] = useState(null);
    const [numPages, setNumPages] = useState(1);
    const [numElements, setNumElements] = useState(0);
    const [loading, setLoading] = useState(true);
    const [r , setR] = useState(0) ;

    useEffect(() => {
        const getFiles = async () => {
            setLoading(true);
            try {
                const response = await fetch(`http://localhost:8082/file/page?page=${page}`, {
                    method: "GET",
                    credentials: "include"
                });
                if (!response.ok) {
                    throw Error("could not fetch files! Please try again later");
                }
                const data = await response.json();
                if (data) {
                    setFiles(data.files);
                    setNumPages(data.numPages);
                    setNumElements(data.numElements);
                }
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        }
        getFiles();
    }, [page , setR , r])

    return (
        <Box>
            <Box sx={{ padding: "15px" }}>
                <UploadDialog r = {setR} />
            </Box>
            <Stack justifyContent={'flex-start'} padding={"15px"}>
                {loading ? (
                    <Box 
                        display={'flex'} 
                        width={'100%'} 
                        justifyContent={'center'} 
                        alignItems={'center'} 
                        minHeight={'400px'}
                    >
                        <CircularProgress 
                            size={48} 
                            thickness={4}
                            sx={{ 
                                color: '#1f2937'
                            }} 
                        />
                    </Box>
                ) : (numElements > 0) && files ? (
                    <PaperList files={files} />
                ) : (
                    <Box 
                        display={'flex'} 
                        width={'100%'} 
                        justifyContent={'center'} 
                        alignItems={'center'} 
                        minHeight={'400px'}
                    >
                        <Button
                            sx={{
                                textTransform: 'none',
                                fontWeight: 600,
                                color: '#1f2937',
                                fontSize: '0.9rem',
                                padding: '10px 24px',
                                borderRadius: '8px',
                                border: '1px solid #e5e7eb',
                                backgroundColor: '#ffffff',
                                '&:hover': {
                                    backgroundColor: '#f9fafb',
                                    borderColor: '#d1d5db'
                                }
                            }}
                        >
                            Add Your First File
                        </Button>
                    </Box>
                )}
                {!loading && numPages > 1 && (
                    <Stack alignItems={'center'}>
                        <Pagination 
                            sx={{ margin: '24px' }} 
                            count={numPages} 
                            page={page + 1} 
                            onChange={(e, value) => {
                                setPage(value - 1);
                            }} 
                        />
                    </Stack>
                )}
            </Stack>
        </Box>
    )
}

export default UserFiles