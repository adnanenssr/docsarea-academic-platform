import React, { useState } from "react"
import {
  Box,
  Container,
  Paper,
  Stack,
  Tabs,
  Tab,
  Typography,
} from "@mui/material"
import { Eye, Download } from "lucide-react"
import CallUserDownloads from "./callUserDownloads"
import CallUserViews from "./callUserViews"

function UserInsights() {
  const [metric, setMetric] = useState(0)

  const handleChange = (event, newValue) => {
    setMetric(newValue)
  }

  return (
    <Box sx={{ width: '100%', minHeight: "100vh", backgroundColor: "#ffffff" }}>
      <Container maxWidth="lg">
        

        {/* Tabs */}
        <Paper
          elevation={0}
          sx={{
            mb: 4,
            borderRadius: "8px",
            border: "1px solid #e5e7eb",
            backgroundColor: "white",
            boxShadow: 'none',
          }}
        >
          <Tabs
            value={metric}
            onChange={handleChange}
            variant="fullWidth"
            sx={{
              "& .MuiTabs-indicator": {
                backgroundColor: '#1f2937',
                height: 2.5,
              },
              "& .MuiTab-root": {
                fontWeight: 600,
                textTransform: "none",
                fontSize: "0.95rem",
                py: 2,
                color: '#6b7280',
                transition: 'all 0.2s',
                '&.Mui-selected': {
                  color: '#1f2937',
                },
                '&:hover': {
                  color: '#1f2937',
                  backgroundColor: '#f9fafb',
                }
              },
            }}
          >
            <Tab
              icon={<Eye size={20} />}
              iconPosition="start"
              label="Views"
              sx={{ gap: 1 }}
            />
            <Tab
              icon={<Download size={20} />}
              iconPosition="start"
              label="Downloads"
              sx={{ gap: 1 }}
            />
          </Tabs>
        </Paper>

        {/* Content */}
        <Box>
          {metric === 0 ? <CallUserViews /> : <CallUserDownloads />}
        </Box>
      </Container>
    </Box>
  )
}

export default UserInsights