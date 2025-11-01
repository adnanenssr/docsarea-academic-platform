import { Box, Button, Container, Paper, Stack, Typography, CircularProgress } from '@mui/material'
import React, { useEffect, useState } from 'react'
import DownloadsChart from './downloadsChart'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import FilesDownloads from './filesDownloads'
import dayjs from 'dayjs'
import { useForm, Controller } from 'react-hook-form'
import { groupCache } from './groupCache'

function CallDownloads({ groupId }) {
  const group = groupCache.get(groupId)

  const [downloads, setDownloads] = useState(new Map())
  const [files, setFiles] = useState([])
  const [page, setPage] = useState(0)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const { control, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      start: dayjs().subtract(3, 'month'),
      end: dayjs()
    },
  })

  const getDownloadsByWeek = async (beginning, ending) => {
    try {
      const response = await fetch(`http://localhost:8082/get/downloads/group/${groupId}?start=${beginning}&end=${ending}`, {
        method: "GET",
        credentials: "include"
      })

      if (!response.ok) {
        throw new Error("Could not fetch downloads data")
      }

      const data = await response.json()
      if (data) {
        setDownloads(new Map(Object.entries(data)))
      }
    } catch (err) {
      setError(err.message)
      console.error(err)
    }
  }

  const getDownloadsByFiles = async (beginning, ending) => {
    try {
      const response = await fetch(`http://localhost:8082/get/downloads/files/${groupId}?start=${beginning}&end=${ending}&page=0`, {
        method: "GET",
        credentials: "include"
      })

      if (!response.ok) {
        throw new Error("Could not fetch files data")
      }

      const data = await response.json()
      if (data) {
        setFiles(data.files)
      }
    } catch (err) {
      setError(err.message)
      console.error(err)
    }
  }

  const onSubmit = (data) => {
    setLoading(true)
    setError(null)
    const startDate = dayjs(data.start).format('YYYY-MM-DD')
    const endDate = dayjs(data.end).format('YYYY-MM-DD')

    Promise.all([
      getDownloadsByWeek(startDate, endDate),
      getDownloadsByFiles(startDate, endDate)
    ]).finally(() => setLoading(false))
  }

  useEffect(() => {
    const ending = new Date()
    const beginning = new Date()
    beginning.setMonth(beginning.getMonth() - 3)

    getDownloadsByWeek(beginning.toISOString().split("T")[0], ending.toISOString().split("T")[0])
    getDownloadsByFiles(beginning.toISOString().split("T")[0], ending.toISOString().split("T")[0])
  }, [groupId])

  return (
    <Box sx={{ width: '100%', bgcolor: '#ffffff', minHeight: '100vh' }}>
      <Container maxWidth="lg">
        {/* Error State */}
        {error && (
          <Paper
            sx={{
              p: 2.5,
              mb: 3,
              backgroundColor: '#fef2f2',
              border: '1px solid #fee2e2',
              borderRadius: '8px',
              boxShadow: 'none',
            }}
          >
            <Typography sx={{ color: '#991b1b', fontWeight: 500, fontSize: '0.95rem' }}>
              Error: {error}
            </Typography>
          </Paper>
        )}

        {/* Date Range Filter */}
        <Paper
          sx={{
            p: 3,
            mb: 4,
            borderRadius: '8px',
            border: '1px solid #e5e7eb',
            boxShadow: 'none',
          }}
        >
          <Typography
            variant="subtitle2"
            sx={{
              fontWeight: 600,
              color: '#6b7280',
              fontSize: '0.85rem',
              textTransform: 'uppercase',
              letterSpacing: '0.5px',
              mb: 2,
            }}
          >
            Date Range
          </Typography>

          <Box component="form" onSubmit={handleSubmit(onSubmit)}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <Stack
                direction={{ xs: 'column', sm: 'row' }}
                spacing={2}
                alignItems={{ xs: 'stretch', sm: 'center' }}
              >
                <Controller
                  name="start"
                  control={control}
                  render={({ field }) => (
                    <DatePicker
                      {...field}
                      label="Start Date"
                      slotProps={{
                        textField: {
                          fullWidth: true,
                          size: 'small',
                          sx: {
                            '& .MuiOutlinedInput-root': {
                              borderRadius: '6px',
                              fontSize: '0.9rem',
                            },
                          },
                        },
                      }}
                    />
                  )}
                />

                <Controller
                  name="end"
                  control={control}
                  render={({ field }) => (
                    <DatePicker
                      {...field}
                      label="End Date"
                      slotProps={{
                        textField: {
                          fullWidth: true,
                          size: 'small',
                          sx: {
                            '& .MuiOutlinedInput-root': {
                              borderRadius: '6px',
                              fontSize: '0.9rem',
                            },
                          },
                        },
                      }}
                    />
                  )}
                />

                <Button
                  variant="contained"
                  type="submit"
                  disabled={loading}
                  sx={{
                    textTransform: 'none',
                    fontWeight: 600,
                    fontSize: '0.9rem',
                    px: 3,
                    py: 1,
                    borderRadius: '6px',
                    backgroundColor: '#1f2937',
                    color: 'white',
                    transition: 'all 0.2s',
                    whiteSpace: 'nowrap',
                    '&:hover': {
                      backgroundColor: '#374151',
                    },
                    '&:disabled': {
                      backgroundColor: '#9ca3af',
                    },
                  }}
                >
                  {loading ? <CircularProgress size={20} sx={{ color: 'white' }} /> : 'Apply'}
                </Button>
              </Stack>
            </LocalizationProvider>
          </Box>
        </Paper>

        {/* Downloads Chart */}
        {downloads.size !== 0 && (
          <Paper
            sx={{
              p: 3,
              mb: 4,
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              boxShadow: 'none',
            }}
          >
            <Typography
              variant="subtitle2"
              sx={{
                fontWeight: 600,
                color: '#1f2937',
                fontSize: '0.95rem',
                mb: 2.5,
              }}
            >
              Downloads Overview
            </Typography>
            <DownloadsChart downloads={downloads} />
          </Paper>
        )}

        {/* Files Downloads */}
        {files && files.length > 0 && (
          <Paper
            sx={{
              p: 3,
              borderRadius: '8px',
              border: '1px solid #e5e7eb',
              boxShadow: 'none',
            }}
          >
            <Typography
              variant="subtitle2"
              sx={{
                fontWeight: 600,
                color: '#1f2937',
                fontSize: '0.95rem',
                mb: 2.5,
              }}
            >
              Top Downloaded Files
            </Typography>
            <FilesDownloads files={files} />
          </Paper>
        )}
      </Container>
    </Box>
  )
}

export default CallDownloads