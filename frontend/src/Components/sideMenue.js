import React from "react"
import {
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Box,
  Divider
} from "@mui/material"
import {
  Home,
  LogOut,
  User,
  FolderOpen,
  Users,
  Bookmark,
  Download,
  BarChart3,
} from "lucide-react"
import { useLocation, useNavigate } from "react-router-dom"

const drawerWidth = 240

export default function SideMenu() {
  const location = useLocation()
  const navigate = useNavigate()

  const menuItems = [
    { text: "Home", icon: Home, path: "/u/dashboard" },
    { text: "Files", icon: FolderOpen, path: "/u/files" },
    { text: "Groups", icon: Users, path: "/u/groups" },
    { text: "Bookmarks", icon: Bookmark, path: "/u/bookmarks" },
    { text: "Downloads", icon: Download, path: "/u/downloads" },
    { text: "Insights", icon: BarChart3, path: "/u/insights" },
  ]

  const bottomItems = [
    { text: "Profile", icon: User, path: "/u/profile" },
    { text: "Logout", icon: LogOut, isLogout: true },
  ]

  const logout = async () => {
    try {
      const response = await fetch("http://localhost:8082/auth/logout", {
        method: "POST",
        headers: {
          "content-type": "application/json"
        },
        credentials: "include"
      })
      navigate("/")
    } catch (err) {
      console.error("Logout failed:", err)
    }
  }

  return (
    <Drawer
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          width: drawerWidth,
          boxSizing: "border-box",
          display: "flex",
          flexDirection: "column",
          top: 64,
          height: "calc(100vh - 64px)",
          overflow: "hidden",
          backgroundColor: "#ffffff",
          border: "none",
          borderRight: "1px solid #e5e7eb",
        },
      }}
      variant="permanent"
      anchor="left"
    >
      {/* Main Menu Items */}
      <List sx={{ flexGrow: 1, pt: 2, pb: 0, overflow: "auto", px: 1 }}>
        {menuItems.map((item) => {
          const IconComponent = item.icon
          const isActive = location.pathname === item.path

          return (
            <ListItem key={item.text} disablePadding sx={{ mb: 0.5 }}>
              <ListItemButton
                onClick={() => navigate(item.path)}
                sx={{
                  borderRadius: "6px",
                  px: 2,
                  py: 1.25,
                  backgroundColor: isActive ? "#f3f4f6" : "transparent",
                  borderLeft: isActive ? "3px solid #1f2937" : "3px solid transparent",
                  color: isActive ? "#1f2937" : "#6b7280",
                  transition: "all 0.2s",
                  "& .MuiListItemIcon-root": {
                    minWidth: 36,
                    color: "inherit",
                  },
                  "&:hover": {
                    backgroundColor: isActive ? "#f3f4f6" : "#f9fafb",
                    color: "#1f2937",
                  },
                }}
              >
                <ListItemIcon sx={{ color: "inherit", minWidth: 36 }}>
                  <IconComponent size={20} />
                </ListItemIcon>
                <ListItemText
                  primary={item.text}
                  primaryTypographyProps={{
                    fontWeight: isActive ? 600 : 500,
                    fontSize: "0.9rem",
                    color: "inherit",
                  }}
                />
              </ListItemButton>
            </ListItem>
          )
        })}
      </List>

      {/* Bottom Items */}
      <Box sx={{ p: 1, borderTop: "1px solid #e5e7eb" }}>
        <List sx={{ p: 0 }}>
          {bottomItems.map((item) => {
            const IconComponent = item.icon
            const isActive = location.pathname === item.path

            return (
              <ListItem key={item.text} disablePadding sx={{ mb: 0.5 }}>
                <ListItemButton
                  onClick={() => (item.isLogout ? logout() : navigate(item.path))}
                  sx={{
                    borderRadius: "6px",
                    px: 2,
                    py: 1.25,
                    backgroundColor:
                      item.isLogout
                        ? "transparent"
                        : isActive
                        ? "#f3f4f6"
                        : "transparent",
                    borderLeft:
                      item.isLogout
                        ? "3px solid transparent"
                        : isActive
                        ? "3px solid #1f2937"
                        : "3px solid transparent",
                    color: item.isLogout ? "#991b1b" : isActive ? "#1f2937" : "#6b7280",
                    transition: "all 0.2s",
                    "& .MuiListItemIcon-root": {
                      minWidth: 36,
                      color: "inherit",
                    },
                    "&:hover": {
                      backgroundColor:
                        item.isLogout
                          ? "#fef2f2"
                          : isActive
                          ? "#f3f4f6"
                          : "#f9fafb",
                      color: item.isLogout ? "#991b1b" : "#1f2937",
                    },
                  }}
                >
                  <ListItemIcon sx={{ color: "inherit", minWidth: 36 }}>
                    <IconComponent size={20} />
                  </ListItemIcon>
                  <ListItemText
                    primary={item.text}
                    primaryTypographyProps={{
                      fontWeight: 500,
                      fontSize: "0.9rem",
                      color: "inherit",
                    }}
                  />
                </ListItemButton>
              </ListItem>
            )
          })}
        </List>
      </Box>
    </Drawer>
  )
}