import { Box, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import PaperList from './paper';
import FileViewPapers from './fileViewPapers';

function Bookmarks() {

    const [files , setFiles] = useState([]) ;

    useEffect(() => {
        const getBookmarks = async () => {
            const response = await fetch("http://localhost:8082/get/bookmarks?page=0" , {
                method : "GET" ,
                credentials : "include"
            })
            if(!response.ok){
                throw new Error("could not fetch bookmarks , please try again later") ;
            }
            const data = await response.json() ;
            if(data){
                setFiles(data.files) ;
            }

        }
        getBookmarks() ;
    },[])

  return (
    <Box sx={{paddingTop :"15px" ,paddingLeft : "25px"}}>
        { !files &&<Typography>this is this</Typography> }
    { files && <FileViewPapers files={files} />}
    </Box>
  )
}

export default Bookmarks
