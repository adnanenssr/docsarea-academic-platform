import { Box } from '@mui/material'
import React from 'react'
import SideMenu from './sideMenue';

const drawerWidth = 240;


function SideMenuWrapper({children}) {
  return (
    <Box  sx={{ display: 'flex' }}> {/* 64px is typical AppBar height */}
        <SideMenu />
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            p: 3,
            width: { sm: `calc(100% - ${drawerWidth}px)` },
          }}
        >
            {children}

        </Box>
        </Box>
  )
}

export default SideMenuWrapper
