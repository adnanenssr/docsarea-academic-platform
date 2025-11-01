import { Box, Tabs, Tab, TextField } from "@mui/material";
import { useState } from "react";
import UploadDialog from "./upload";
import { useParams } from "react-router-dom";
import ReviewsPublished from './reviewsPublished';
import ReviewsInReview from './reviewsInReview';
import ReviewsRejected from './reviewsRejected';



function GroupReviewsPage() {
   const {groupId} = useParams() ;
  const [value, setValue] = useState(0);
  const pages = ["Published" , "In Review" , "Rejected"] ;

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{margin :"20px" , marginTop : "5px"}}>
      {/* Top bar with flex layout */}
      <Box display="flex" alignItems="center" gap={2} mb={2}>
        {/* Left: Tabs */}
        <Tabs value={value} onChange={handleChange} >
          {pages.map((page, index) => (
            <Tab sx={{marginRight : '20px'}} key={page} label={page} value={index} />
          ))}
        </Tabs>

        
      </Box>

      {/* Panels */}
      {value === 0 && <ReviewsPublished groupId={groupId} />}
      {value === 1 && <ReviewsInReview groupId={groupId} />}
      {value === 2 && <ReviewsRejected groupId={groupId} />}
    </Box>
  );
}

export default GroupReviewsPage
