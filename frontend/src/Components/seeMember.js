import { Launch } from '@mui/icons-material'
import { Avatar, Box, Button, IconButton, Paper, Stack, Typography } from '@mui/material'
import { Delete, Edit, ExternalLink, SquareArrowOutUpRight, Trash } from 'lucide-react'
import React, { useState } from 'react'
import UpdateMember from './updateMember'
import { useNavigate } from 'react-router-dom'

function SeeMember({username , groupId }) {
  const [mock , setMock] = useState(0) ;
  const navigate = useNavigate() ;
    return (
      <Paper sx={{ }}>
          <Stack sx={{alignItems : "center" }}>
              <Box>
                  <Stack sx={{alignItems : "center"}}>
                    <Button onClick={() => {
                        navigate(`/profile/${username}`) ;
                    }} >
                  <Avatar sx={{margin : "15px" , width : "100px" , height : "100px"}} variant="rounded" src={`http://localhost:8082/api/get/avatar/${username}`}  >{username.slice(0,1)}</Avatar>
                  </Button>
                  <Typography>@{username}</Typography>
                  </Stack>
              </Box>
          </Stack>
      </Paper>
    )
}

export default SeeMember
