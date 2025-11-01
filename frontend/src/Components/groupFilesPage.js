import { Box, Tabs, Tab, TextField } from "@mui/material";
import { useState } from "react";
import UploadDialog from "./upload";
import Published from "./published";
import { useParams } from "react-router-dom";
import InReview from "./inReview";
import Rejected from "./rejected";

export default function GroupFilesPage() {
    const {groupId} = useParams() ;
  const [value, setValue] = useState(0);
  const pages = ["Publications" , "In Review" , "Rejected"] ;

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

        {/* Center: Search (flex-grow) */}
        <Box flex={1} display="flex" justifyContent="center" sx={{ width: "60%" }}>
          
        </Box>

        {/* Right: Upload dialog */}
        <UploadDialog groupId={groupId} />
      </Box>

      {/* Panels */}
      {value === 0 && <Published groupId={groupId} />}
      {value === 1 && <InReview groupId={groupId} />}
      {value === 2 && <Rejected groupId={groupId} />}
    </Box>
  );
}
