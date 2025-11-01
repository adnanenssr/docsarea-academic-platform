import React, { useEffect, useState, useCallback } from "react"
import {
  Box,
  Card,
  CardContent,
  IconButton,
  Stack,
  Typography,
  CircularProgress,
  Pagination,
  Container,
} from "@mui/material"
import { Download, Save, FileText } from "lucide-react"
import { useNavigate, useSearchParams } from "react-router-dom"

function Search() {
  const [files, setFiles] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [totalPages, setTotalPages] = useState(1)
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()

  const search = useCallback(async () => {
    const query = searchParams.get("q") || ""
    const page = searchParams.get("page") || "0"

    if (!query.trim()) {
      setFiles([])
      return
    }

    setLoading(true)
    setError(null)

    try {
      const url = `http://localhost:8082/search?q=${encodeURIComponent(query)}&page=${page}`

      const response = await fetch(url, {
        method: "GET",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
      })

      if (!response.ok) {
        throw new Error(`Search failed: ${response.status}`)
      }

      const data = await response.json()
      setFiles(data.files || [])
      setTotalPages(data.totalPages || 1)
    } catch (error) {
      setError(error.message)
      setFiles([])
    } finally {
      setLoading(false)
    }
  }, [searchParams])

  useEffect(() => {
    search()
  }, [search])

  const handlePageChange = (event, page) => {
    const query = searchParams.get("q")
    navigate(`/search?q=${encodeURIComponent(query)}&page=${page - 1}`)
  }

  const currentPage = parseInt(searchParams.get("page") || "0") + 1

  return (
    <Box
      sx={{
        width: "100%",
        minHeight: "100vh",
        background: "#ffffff",
        paddingY: "40px",
      }}
    >
      <Container maxWidth="lg">
        {/* Header */}
        <Box sx={{ marginBottom: "40px" }}>
          <Typography
            variant="h4"
            sx={{
              fontWeight: 700,
              color: "#1f2937",
              marginBottom: "8px",
              fontSize: { xs: "1.5rem", md: "1.875rem" },
            }}
          >
            Search Results
          </Typography>
          <Typography
            variant="body1"
            sx={{
              color: "#6b7280",
              fontSize: "0.95rem",
            }}
          >
            {files.length} results for "<span style={{ fontWeight: 500, color: "#1f2937" }}>{searchParams.get("q")}</span>"
          </Typography>
        </Box>

        {/* Loading State */}
        {loading && (
          <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", minHeight: "400px" }}>
            <CircularProgress sx={{ color: "#1f2937" }} />
            <Typography sx={{ ml: 2, color: "#6b7280", fontWeight: 400 }}>
              Searching...
            </Typography>
          </Box>
        )}

        {/* Error State */}
        {error && !loading && (
          <Card
            sx={{
              padding: "20px",
              background: "#fef2f2",
              border: "1px solid #fee2e2",
              borderRadius: "8px",
              marginBottom: "24px",
              boxShadow: "none",
            }}
          >
            <Typography
              variant="body1"
              sx={{
                color: "#991b1b",
                fontWeight: 500,
                fontSize: "0.95rem",
              }}
            >
              Error: {error}
            </Typography>
          </Card>
        )}

        {/* No Query State */}
        {!loading && !searchParams.get("q") && (
          <Card
            sx={{
              padding: "60px 24px",
              textAlign: "center",
              background: "#f9fafb",
              borderRadius: "8px",
              boxShadow: "none",
              border: "1px solid #e5e7eb",
            }}
          >
            <FileText size={48} style={{ color: "#d1d5db", marginBottom: "16px", margin: "0 auto 16px" }} />
            <Typography
              variant="h6"
              sx={{
                color: "#6b7280",
                fontWeight: 500,
                fontSize: "1rem",
              }}
            >
              Enter a search query to get started
            </Typography>
          </Card>
        )}

        {/* No Results State */}
        {!loading && !error && files.length === 0 && searchParams.get("q") && (
          <Card
            sx={{
              padding: "60px 24px",
              textAlign: "center",
              background: "#f9fafb",
              borderRadius: "8px",
              boxShadow: "none",
              border: "1px solid #e5e7eb",
            }}
          >
            <FileText size={48} style={{ color: "#d1d5db", marginBottom: "16px", margin: "0 auto 16px" }} />
            <Typography
              variant="h6"
              sx={{
                color: "#6b7280",
                fontWeight: 500,
                fontSize: "1rem",
              }}
            >
              No results found for "{searchParams.get("q")}"
            </Typography>
          </Card>
        )}

        {/* Results List */}
        {!loading && files.length > 0 && (
          <>
            <Stack spacing={2} sx={{ marginBottom: "40px" }}>
              {files.map((file) => (
                <Card
                  key={file.id}
                  onClick={() => navigate(`/file/${file.id}`)}
                  sx={{
                    display: "flex",
                    borderRadius: "8px",
                    overflow: "hidden",
                    transition: "all 0.2s ease-in-out",
                    cursor: "pointer",
                    background: "#ffffff",
                    boxShadow: "none",
                    border: "1px solid #e5e7eb",
                    "&:hover": {
                      borderColor: "#d1d5db",
                      boxShadow: "0 4px 12px rgba(0, 0, 0, 0.08)",
                    },
                    height: "140px",
                  }}
                >
                  {/* Thumbnail */}
                  <Box
                    sx={{
                      width: "120px",
                      height: "140px",
                      flexShrink: 0,
                      background: "#f3f4f6",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      overflow: "hidden",
                    }}
                  >
                    <Box
                      component="img"
                      src={`http://localhost:8082/thumbnail/${file.id}` || "/image.png"}
                      alt={file.title}
                      sx={{
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                      }}
                    />
                  </Box>

                  {/* Content */}
                  <CardContent
                    sx={{
                      flex: 1,
                      display: "flex",
                      flexDirection: "column",
                      justifyContent: "space-between",
                      padding: "20px 24px",
                      textAlign: "left",
                    }}
                  >
                    <Box>
                      <Typography
                        variant="h6"
                        sx={{
                          fontWeight: 600,
                          color: "#1f2937",
                          marginBottom: "6px",
                          fontSize: "1rem",
                          display: "-webkit-box",
                          WebkitLineClamp: 2,
                          WebkitBoxOrient: "vertical",
                          overflow: "hidden",
                        }}
                      >
                        {file.title}
                      </Typography>
                      <Typography
                        variant="body2"
                        sx={{
                          color: "#6b7280",
                          marginBottom: "8px",
                          display: "-webkit-box",
                          WebkitLineClamp: 2,
                          WebkitBoxOrient: "vertical",
                          overflow: "hidden",
                          lineHeight: 1.5,
                          fontSize: "0.9rem",
                        }}
                      >
                        {file.description}
                      </Typography>
                    </Box>

                    {/* Action Buttons */}
                    <Box sx={{ display: "flex", gap: "6px" }}>
                      <IconButton
                        size="small"
                        onClick={(e) => {
                          e.stopPropagation()
                        }}
                        sx={{
                          background: "#f3f4f6",
                          color: "#6b7280",
                          width: 32,
                          height: 32,
                          transition: "all 0.2s",
                          "&:hover": {
                            background: "#1f2937",
                            color: "white",
                          },
                        }}
                      >
                        <Save size={16} />
                      </IconButton>
                      <IconButton
                        size="small"
                        onClick={(e) => {
                          e.stopPropagation()
                        }}
                        sx={{
                          background: "#f3f4f6",
                          color: "#6b7280",
                          width: 32,
                          height: 32,
                          transition: "all 0.2s",
                          "&:hover": {
                            background: "#1f2937",
                            color: "white",
                          },
                        }}
                      >
                        <Download size={16} />
                      </IconButton>
                    </Box>
                  </CardContent>
                </Card>
              ))}
            </Stack>

            {/* Pagination */}
            {totalPages > 1 && (
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  padding: "32px 0",
                }}
              >
                <Pagination
                  count={totalPages}
                  page={currentPage}
                  onChange={handlePageChange}
                  size="large"
                  sx={{
                    "& .MuiPaginationItem-root": {
                      color: "#6b7280",
                      fontWeight: 500,
                      border: "1px solid #e5e7eb",
                      borderRadius: "6px",
                      fontSize: "0.9rem",
                    },
                    "& .MuiPaginationItem-page.Mui-selected": {
                      background: "#1f2937",
                      color: "white",
                      border: "1px solid #1f2937",
                    },
                    "& .MuiPaginationItem-ellipsis": {
                      color: "#d1d5db",
                    },
                  }}
                />
              </Box>
            )}
          </>
        )}
      </Container>
    </Box>
  )
}

export default Search