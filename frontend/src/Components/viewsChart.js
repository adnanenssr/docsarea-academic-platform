import { Box } from '@mui/material'
import React, { useEffect, useState, useRef } from 'react'
import Chart from "chart.js/auto"

function ViewsChart({ views }) {
  const chartRef = useRef(null)
  const chartInstance = useRef(null)

  useEffect(() => {
    if (chartInstance.current) {
      chartInstance.current.destroy()
    }

    if (!chartRef.current) return

    const ctx = chartRef.current.getContext("2d")

    // Subtle gradient
    const gradient = ctx.createLinearGradient(0, 0, 0, 300)
    gradient.addColorStop(0, 'rgba(31, 41, 55, 0.1)')
    gradient.addColorStop(1, 'rgba(31, 41, 55, 0.01)')

    chartInstance.current = new Chart(ctx, {
      type: "line",
      data: {
        labels: Array.from(views.keys()),
        datasets: [
          {
            label: "Views",
            data: Array.from(views.values()),
            backgroundColor: gradient,
            borderColor: "#1f2937",
            borderWidth: 3.5,
            fill: true,
            tension: 0.4,
            pointRadius: 4,
            pointHoverRadius: 6,
            pointBackgroundColor: "#1f2937",
            pointBorderColor: "#ffffff",
            pointBorderWidth: 2,
            pointHoverBackgroundColor: "#1f2937",
            pointHoverBorderColor: "#ffffff",
            pointHoverBorderWidth: 2,
            borderCapStyle: 'round',
            borderJoinStyle: 'round',
            shadowColor: 'rgba(31, 41, 55, 0.3)',
            shadowBlur: 6,
            shadowOffsetX: 0,
            shadowOffsetY: 2,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        interaction: {
          intersect: false,
          mode: 'index',
        },
        plugins: {
          legend: {
            display: true,
            position: 'top',
            align: 'end',
            labels: {
              usePointStyle: true,
              padding: 12,
              font: {
                size: 13,
                weight: 600,
                family: "'Inter', sans-serif"
              },
              color: '#6b7280',
              boxWidth: 6,
              boxHeight: 6
            }
          },
          tooltip: {
            enabled: true,
            backgroundColor: 'rgba(31, 41, 55, 0.9)',
            padding: 10,
            borderRadius: 6,
            titleFont: {
              size: 13,
              weight: 600,
              family: "'Inter', sans-serif"
            },
            bodyFont: {
              size: 12,
              family: "'Inter', sans-serif"
            },
            displayColors: true,
            borderColor: '#e5e7eb',
            borderWidth: 1
          }
        },
        scales: {
          x: {
            grid: {
              display: false,
              drawBorder: false
            },
            ticks: {
              font: {
                size: 12,
                family: "'Inter', sans-serif"
              },
              color: '#9ca3af',
              padding: 8
            }
          },
          y: {
            beginAtZero: true,
            grid: {
              color: '#e5e7eb',
              drawBorder: false,
              lineWidth: 0.5
            },
            ticks: {
              font: {
                size: 12,
                family: "'Inter', sans-serif"
              },
              color: '#9ca3af',
              padding: 8,
              callback: function(value) {
                return value.toLocaleString()
              }
            }
          },
        },
      },
    })

    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy()
      }
    }
  }, [views])

  return (
    <Box
      sx={{
        height: 380,
        width: "100%",
        position: 'relative',
      }}
    >
      <canvas ref={chartRef}></canvas>
    </Box>
  )
}

export default ViewsChart