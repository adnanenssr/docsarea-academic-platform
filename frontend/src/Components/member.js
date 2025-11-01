import { Launch } from '@mui/icons-material'
import { Avatar, Box, IconButton, Paper, Stack, Typography } from '@mui/material'
import { Delete, Edit, ExternalLink, SquareArrowOutUpRight, Trash } from 'lucide-react'
import React, { useState } from 'react'
import UpdateMember from './updateMember'
import { useNavigate } from 'react-router-dom'

function Member({username , groupId }) {
    const [mock , setMock] = useState(0) ;
    const navigate = useNavigate() ;

      const deleteMember = async () => {
    const response = await fetch(`http://localhost:8082/member/delete/${username}/${groupId}` , {
        method : "DELETE" ,
        credentials : "include"
    })
}




  return (
    <Paper sx={{ }}>
        <Stack sx={{alignItems : "center" }}>
            <Box>
                <Stack sx={{alignItems : "center"}}>
                {<Avatar sx={{margin : "15px" , width : "100px" , height : "100px"}} variant="rounded" src={`http://localhost:8082/api/get/avatar/${username}`}  >{username.slice(0,1)}</Avatar>}
                <Typography>@{username}</Typography>
                </Stack>
            </Box>
            <Stack direction={'row'}  spacing={1}>
                <IconButton onClick={() => {
                    navigate(`/profile/${username}`)
                }}>
                    <Box>
                    <SquareArrowOutUpRight/>
                    </Box>
                </IconButton>
                
                    <UpdateMember groupId = {groupId} username={username}/>
                
                <IconButton onClick={() => {deleteMember()}}>
                    <Box>
                    <Trash/></Box>
                </IconButton>
            </Stack>
        </Stack>
    </Paper>
  )
}

export default Member
