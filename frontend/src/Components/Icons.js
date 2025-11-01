import React, { useEffect, useState } from 'react';
import { IconButton, Box } from '@mui/material';
import { useParams } from 'react-router-dom';

// Custom Download Icon
const DownloadIcon = ({ color = "currentColor", size = 20 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <path 
      d="M12 3V16M12 16L16 12M12 16L8 12" 
      stroke={color} 
      strokeWidth="2" 
      strokeLinecap="round" 
      strokeLinejoin="round"
    />
    <path 
      d="M3 20H21" 
      stroke={color} 
      strokeWidth="2" 
      strokeLinecap="round"
    />
  </svg>
);

// Custom Print Icon
const PrintIcon = ({ color = "currentColor", size = 20 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <path 
      d="M18 8V5C18 4.44772 17.5523 4 17 4H7C6.44772 4 6 4.44772 6 5V8M18 8H20C21.1046 8 22 8.89543 22 10V16C22 17.1046 21.1046 18 20 18H18M18 8H6M6 8H4C2.89543 8 2 8.89543 2 10V16C2 17.1046 2.89543 18 4 18H6M6 18V20C6 20.5523 6.44772 21 7 21H17C17.5523 21 18 20.5523 18 20V18M6 18H18" 
      stroke={color} 
      strokeWidth="1.5" 
      strokeLinecap="round"
    />
    <circle cx="18" cy="12" r="1" fill={color} />
  </svg>
);

// Custom Share Icon
const ShareIcon = ({ color = "currentColor", size = 20 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <circle cx="18" cy="5" r="3" stroke={color} strokeWidth="1.5"/>
    <circle cx="6" cy="12" r="3" stroke={color} strokeWidth="1.5"/>
    <circle cx="18" cy="19" r="3" stroke={color} strokeWidth="1.5"/>
    <path 
      d="M8.59 13.51L15.42 17.49M15.41 6.51L8.59 10.49" 
      stroke={color} 
      strokeWidth="1.5" 
      strokeLinecap="round"
    />
  </svg>
);

// Custom Bookmark Icon
const BookmarkIcon = ({ color = "currentColor", size = 20, filled = false }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <path 
      d="M19 21L12 16L5 21V5C5 3.89543 5.89543 3 7 3H17C18.1046 3 19 3.89543 19 5V21Z" 
      stroke={color} 
      strokeWidth="1.5" 
      fill={filled ? color : "none"}
      strokeLinecap="round" 
      strokeLinejoin="round"
    />
  </svg>
);

// Custom Star Icon with Fill Animation
const StarIcon = ({ color = "currentColor", size = 20, filled = false }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <path 
      d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" 
      stroke={color} 
      strokeWidth="1.5" 
      fill={filled ? "#FFD700" : "none"}
      strokeLinecap="round" 
      strokeLinejoin="round"
      style={{
        transition: 'fill 0.2s ease-in-out'
      }}
    />
  </svg>
);

// Custom Flag Icon
const FlagIcon = ({ color = "currentColor", size = 20 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <path 
      d="M4 21V4M4 4L7 7L13 4L20 7V14L13 11L7 14L4 11" 
      stroke={color} 
      strokeWidth="1.5" 
      strokeLinecap="round" 
      strokeLinejoin="round"
    />
  </svg>
);

// Custom Copyright Icon
const CopyrightIcon = ({ color = "currentColor", size = 20 }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none">
    <circle 
      cx="12" 
      cy="12" 
      r="10" 
      stroke={color} 
      strokeWidth="1.5"
    />
    <path 
      d="M15 9.354C14.298 8.522 13.207 8 12 8C9.791 8 8 9.791 8 12C8 14.209 9.791 16 12 16C13.207 16 14.298 15.478 15 14.646" 
      stroke={color} 
      strokeWidth="1.5" 
      strokeLinecap="round"
    />
  </svg>
);

// Main Component with all icons
const ModernActionIcons = ({fileId}) => {




  const [starFilled, setStarFilled] = useState(false);
  const [bookmarkFilled, setBookmarkFilled] = useState(false);

  const onDownload = async () => {
    window.location.href = `http://localhost:8082/file/download/${fileId}`;
  }
  

  const onBookmark = async () => {
    if(!bookmarkFilled){
    const response = await fetch(`http://localhost:8082/add/bookmark/${fileId}` , {
      method : "POST" , 
      credentials : "include"
    })
    if(response.ok){
      setBookmarkFilled(true) ;
    }
    }
    else {
      const response = await fetch(`http://localhost:8082/remove/bookmark/${fileId}` , {
      method : "DELETE" , 
      credentials : "include"
    })
    if(response.ok){
      setBookmarkFilled(false) ;
    }

    }
  }

  useEffect(() => {
    const isBookmarked = async () => {
      const response = await fetch(`http://localhost:8082/get/exist/bookmark/${fileId}` , {
        method : "GET" , 
        credentials : "include"
      })
      if(!response.ok){
        throw new Error("could not fetch bookmark") ;
      }
      const data = await response.json() ;
      if(data){
        setBookmarkFilled(data) ;
      }
    } 
    isBookmarked() ;
  },[])

  const iconButtonStyle = {
    width: 40,
    height: 40,
    borderRadius: '8px',
    border: '1px solid #E2E8F0',
    backgroundColor: '#FFFFFF',
    color: '#64748B',
    transition: 'all 0.2s ease-in-out',
    '&:hover': {
      backgroundColor: '#F8FAFC',
      borderColor: '#CBD5E1',
      color: '#334155',
      transform: 'translateY(-1px)'
    },
    '&:active': {
      transform: 'translateY(0px)'
    }
  };

  const starButtonStyle = {
    ...iconButtonStyle,
    backgroundColor: starFilled ? '#FEF3C7' : '#FFFFFF',
    borderColor: starFilled ? '#F59E0B' : '#E2E8F0',
    color: starFilled ? '#D97706' : '#64748B',
    '&:hover': {
      backgroundColor: starFilled ? '#FDE68A' : '#FFFBEB',
      borderColor: '#F59E0B',
      color: '#D97706',
      transform: 'translateY(-1px)'
    }
  };

  return (
    <Box sx={{ display: 'flex', gap: 1.5, flexWrap: 'wrap', p: 0.5 }}>
      <IconButton sx={iconButtonStyle} onClick={() => onDownload()}>
        <DownloadIcon size={20} />
      </IconButton>
      
      <IconButton sx={iconButtonStyle}>
        <PrintIcon size={20} />
      </IconButton>
      
      <IconButton sx={iconButtonStyle}>
        <ShareIcon size={20} />
      </IconButton>
      
      <IconButton 
        sx={{
          ...iconButtonStyle,
          backgroundColor: bookmarkFilled ? '#EFF6FF' : '#FFFFFF',
          borderColor: bookmarkFilled ? '#3B82F6' : '#E2E8F0',
          color: bookmarkFilled ? '#2563EB' : '#64748B',
          '&:hover': {
            backgroundColor: bookmarkFilled ? '#DBEAFE' : '#F8FAFC',
            borderColor: '#3B82F6',
            color: '#2563EB',
            transform: 'translateY(-1px)'
          }
        }}
        onClick={() => {

          onBookmark() ;
          setBookmarkFilled(!bookmarkFilled)

        }}
      >
        <BookmarkIcon size={20} filled={bookmarkFilled} />
      </IconButton>
      
      <IconButton 
        sx={starButtonStyle}
        onClick={() => setStarFilled(!starFilled)}
      >
        <StarIcon size={20} filled={starFilled} />
      </IconButton>
      
      <IconButton sx={iconButtonStyle}>
        <FlagIcon size={20} />
      </IconButton>
      
      <IconButton sx={iconButtonStyle}>
        <CopyrightIcon size={20} />
      </IconButton>
    </Box>
  );
};

export default ModernActionIcons;