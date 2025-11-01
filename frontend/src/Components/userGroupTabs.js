import React, { useState } from 'react'
import { Box, Tabs, Tab, Paper, Container } from '@mui/material'
import UserGroups from './userGroups'
import UserJoinedGroups from './userJoinedGroups'

function UserGroupsTabs() {
  const [activeTab, setActiveTab] = useState(0)

  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue)
  }

  return (
    <Box sx={{ width: '100%', minHeight: '100vh', background: '#f8f9fa', paddingY: '24px' }}>
      <Container maxWidth="lg">
        {/* Tabs Section */}
        <Paper 
          elevation={0}
          sx={{ 
            background: 'white',
            borderRadius: '12px',
            marginBottom: '24px',
            border: '1px solid #e0e0e0'
          }}
        >
          <Tabs
            value={activeTab}
            onChange={
                handleTabChange
            }
            sx={{
              '& .MuiTabs-indicator': {
                background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                height: '4px',
                borderRadius: '2px',
              },
              '& .MuiTab-root': {
                textTransform: 'none',
                fontSize: '1rem',
                fontWeight: 600,
                color: '#718096',
                transition: 'all 0.3s ease',
                padding: '16px 32px',
                '&:hover': {
                  color: '#667eea',
                  background: 'rgba(102, 126, 234, 0.05)',
                },
                '&.Mui-selected': {
                  color: '#667eea',
                },
              }
            }}
          >
            <Tab label="My Groups" value={0} />
            <Tab label="Joined Groups" value={1} />
          </Tabs>
        </Paper>

        {/* Content Section */}
        <Box
          sx={{
            animation: 'fadeIn 0.3s ease-in-out',
            '@keyframes fadeIn': {
              from: {
                opacity: 0,
                transform: 'translateY(10px)',
              },
              to: {
                opacity: 1,
                transform: 'translateY(0)',
              },
            },
          }}
        >
          {activeTab === 0 && <UserGroups />}
          {activeTab === 1 && <UserJoinedGroups />}
        </Box>
      </Container>
    </Box>
  )
}

export default UserGroupsTabs