import { Box, Card, Stack, Typography, CircularProgress, Container } from '@mui/material'
import React, { useEffect, useState } from 'react'
import DashboardChart from './dashboardChart'
import DashboardFiles from './dashboardFiles'
import { useParams } from 'react-router-dom'
import { groupCache } from './groupCache'

function GroupDashboard() {
  const [stats, setStats] = useState([])
  const [metrics, setMetrics] = useState([])
  const [views, setViews] = useState(new Map())
  const [downloads, setDownloads] = useState(new Map())
  const [files, setFiles] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const { groupId } = useParams()
  const group = groupCache.get(groupId)

  useEffect(() => {
    const publisher = ["ADMIN", "OWNER"].includes(group?.role) ? "group" : "member"

    const start = new Date()
    const end = new Date()
    start.setMonth(end.getMonth() - 3)

    const fetchAllData = async (beginning, ending) => {
      try {
        setLoading(true)

        const [statsRes, metricsRes, viewsRes, downloadsRes, filesRes] = await Promise.all([
          fetch(`http://localhost:8082/get/group/stats/${groupId}`, {
            method: "GET",
            credentials: "include"
          }),
          fetch(`http://localhost:8082/get/total/group/${groupId}`, {
            method: "GET",
            credentials: "include"
          }),
          fetch(`http://localhost:8082/get/views/group/${groupId}?start=${beginning}&end=${ending}&page=0`, {
            method: "GET",
            credentials: "include"
          }),
          fetch(`http://localhost:8082/get/downloads/group/${groupId}?start=${beginning}&end=${ending}&page=0`, {
            method: "GET",
            credentials: "include"
          }),
          fetch(`http://localhost:8082/get/recent/group/${groupId}`, {
            method: "GET",
            credentials: "include"
          })
        ])

        if (!statsRes.ok || !metricsRes.ok || !viewsRes.ok || !downloadsRes.ok || !filesRes.ok) {
          throw new Error("Could not fetch data, please try again later")
        }

        const [statsData, metricsData, viewsData, downloadsData, filesData] = await Promise.all([
          statsRes.json(),
          metricsRes.json(),
          viewsRes.json(),
          downloadsRes.json(),
          filesRes.json()
        ])

        if (statsData && metricsData && viewsData && downloadsData && filesData) {
          setStats(statsData)
          setMetrics(metricsData)
          setViews(new Map(Object.entries(viewsData)))
          setDownloads(new Map(Object.entries(downloadsData)))
          setFiles(filesData)
        }
      } catch (err) {
        setError(err.message)
        console.error("Error fetching dashboard data:", err)
      } finally {
        setLoading(false)
      }
    }

    fetchAllData(start.toISOString().split("T")[0], end.toISOString().split("T")[0])
  }, [groupId, group?.role])

  if (loading) {
    return (
      <Box sx={{ width: '100%', minHeight: '100vh', bgcolor: '#ffffff', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <CircularProgress sx={{ color: '#1f2937' }} />
      </Box>
    )
  }

  if (error) {
    return (
      <Box sx={{ width: '100%', minHeight: '100vh', bgcolor: '#ffffff', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <Typography variant="h6" sx={{ color: '#991b1b' }}>{error}</Typography>
      </Box>
    )
  }

  const statCards = stats[1] !== "?" ? [
    { label: 'Storage', value: stats[1] },
    { label: 'Files', value: stats[0] },
    { label: 'Views', value: metrics[0] },
    { label: 'Downloads', value: metrics[1] }
  ] : [
    { label: 'Files', value: stats[0] },
    { label: 'Views', value: metrics[0] },
    { label: 'Downloads', value: metrics[1] }
  ]

  return (
    <Box sx={{ width: '100%', bgcolor: '#ffffff', minHeight: '100vh', py: 6 }}>
      <Container maxWidth="lg">
        

        {/* Stats Cards */}
        <Box sx={{ display: 'flex', justifyContent: 'center', mb: 6, gap: 2, flexWrap: 'wrap' }}>
          {statCards.map((card) => {
            return (
              <Card
                key={card.label}
                sx={{
                  width: '120px',
                  height: '120px',
                  backgroundColor: '#ffffff',
                  border: '2px solid #d1d5db',
                  boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
                  borderRadius: '8px',
                  p: 3,
                  transition: 'all 0.3s',
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                  textAlign: 'center',
                  '&:hover': {
                    borderColor: '#9ca3af',
                    boxShadow: '0 8px 20px rgba(0, 0, 0, 0.15)',
                    transform: 'translateY(-2px)',
                  },
                }}
              >
                <Typography
                  sx={{
                    fontWeight: 600,
                    color: '#6b7280',
                    fontSize: '0.8rem',
                    textTransform: 'uppercase',
                    letterSpacing: '0.5px',
                    mb: 1.5,
                  }}
                >
                  {card.label}
                </Typography>
                <Typography
                  sx={{
                    fontWeight: 800,
                    color: '#1f2937',
                    fontSize: '2rem',
                    fontFamily: "'Inter', sans-serif",
                    letterSpacing: '-0.02em',
                    lineHeight: 1,
                  }}
                >
                  {card.value || '0'}
                </Typography>
              </Card>
            )
          })}
        </Box>

        {/* Chart and Files */}
        <Stack spacing={4}>
          <Box>
            <DashboardChart views={views} downloads={downloads} />
          </Box>
          <Box>
            <DashboardFiles files={files} />
          </Box>
        </Stack>
      </Container>
    </Box>
  )
}

export default GroupDashboard