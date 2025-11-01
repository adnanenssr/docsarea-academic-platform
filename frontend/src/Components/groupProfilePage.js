import {Avatar,Divider, Container, Paper,  IconButton, Alert, Box, Button,  FormControl, FormControlLabel, FormHelperText, FormLabel, Radio, RadioGroup,  Snackbar, Stack, TextField, ToggleButton, ToggleButtonGroup, Typography, Chip } from '@mui/material'
import React, { useState , useEffect} from 'react'
import { Controller, useForm } from 'react-hook-form'

import { styled } from '@mui/material/styles';
import { Camera, User, Mail, Lock, Edit, Trash } from 'lucide-react'
import { useNavigate, useParams } from 'react-router-dom';

const VisuallyHiddenInput = styled('input')({
  position: 'absolute',
  width: 1,
  height: 1,
  padding: 0,
  margin: -1,
  overflow: 'hidden',
  clip: 'rect(0, 0, 0, 0)',
  border: 0,
});


function GroupProfilePage() {
    const {groupId} = useParams() ;
    const [updateSuccess, setUpdateSuccess] = useState(false);
    const [profile , setProfile] = useState(null) ;
    const [coverPic , setCoverPic] = useState(null) ;
    const [snackbarOpen , setSnackbarOpen] = useState(false) ;
    const [success , setSuccess] = useState(false) ;
    const [error , setError] = useState(false) ;
    const [privacy, setPrivacy] = useState('PUBLIC');
    const [joinPolicy , setJoinPolicy] = useState('OPEN') ;
    const [a , setA] = useState(0) ;
    const navigate = useNavigate() ;
    const [newOwner , setNewOwner] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [showSuggestions, setShowSuggestions] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const {control , handleSubmit , reset , formState : {errors}} = useForm() ;

  

    useEffect(() => {
        let isMounted = true ;
        
        
        const fetchGroup = async () => {
    try {
      const response = await fetch(`http://localhost:8082/group/${groupId}`, {
        method: "GET",
        credentials: "include",
      });

      if (!response.ok) {
        throw new Error("could not fetch the group details");
      }

      const data = await response.json();
      if (data && isMounted) {
        reset({
          name: data.name || '',
          description: data.description || '',
          theme: data.theme || '',
        });
        setPrivacy(data.privacy);
        setJoinPolicy(data.joinPolicy) ;
        setProfile(data.profilePicUrl) ;
        setCoverPic(data.cover) ;
        setProfile(data.profileImg) ;
        setCoverPic(data.coverImg) ;

      }
    } catch (err) {
      console.error(err);
    }
  };

  fetchGroup();
         return () => {
      isMounted = false; // cleanup
    };

    } , [groupId , reset , a])
  
  

  
  
  
    const coverSubmit = async (file) => {
      if (!file) return;
  
      const formData = new FormData();
      formData.append("file", file); // File object directly
  
      try {
          const response = await fetch(`http://localhost:8082/group/${groupId}/save/cover`, {
              method: "PUT",
              credentials: "include",
              body: formData
          });
  
          if (!response.ok) {
              throw new Error("Could not update your cover photo, please try again later");
          }
  
      } catch (err) {
          console.error(err);
      }
  };
  
  const avatarSubmit = async (file) => {
      if (!file) return;
  
      const formData = new FormData();
      formData.append("file", file); // File object directly
  
      try {
          const response = await fetch(`http://localhost:8082/group/${groupId}/save/avatar`, {
              method: "PUT",
              credentials: "include",
              body: formData
          });
  
          if (!response.ok) {
              throw new Error("Could not update your cover photo, please try again later");
          }
  
      } catch (err) {
          console.error(err);
      }
  };
  
    const onSubmit = async (data) => {
        try{
        const response = await fetch(`http://localhost:8082/group/update/${groupId}` , {
            method : "PUT" ,
            credentials : "include" ,
            headers: {
                 "Content-Type": "application/json", // important for JSON payload
                  },
            body : JSON.stringify({
                name : data.name ,
                description : data.description ,
                theme : data.theme ,
                joinPolicy : joinPolicy ,
                privacy : privacy ,
            })
        }) ;
        if(!response.ok){
            throw new Error("could not update group! Please try again")
        }
        const result = await response.json() ;
        if(result){ 
        
            setError(false) ;
            setSuccess(true) ;
            setSnackbarOpen(true) ;
        setA(prevA => prevA + 1); // trigger state change if needed

            }
        }
        catch(e){
            setError(true) ;
            setSuccess(false) ;
            setSnackbarOpen(true) ;

        }
    }
    const transferOwnership = async () => {
        const response = await fetch(`http://localhost:8082/group/ownership/update/${groupId}?to=${newOwner}` , {
            method : "PUT" ,
            credentials : "include"
        })
        if(!response.ok){
            throw new Error("we could not transfer the ownership please try again later") ;
        }
        navigate(`/group/${groupId}`) ;
    }

    const handleSnackbarClose = () =>{
        setSnackbarOpen(false) ;
    }

      const handlePrivacy = (event, privacy) => {
     if (privacy !== null) {
    setPrivacy(privacy);
     }
  };

  const fetchSuggestions = async (value ) => {
    try {
      const response = await fetch(`http://localhost:8082/get/suggestions/${groupId}?value=${value}`, {
        method: "GET",
        credentials: 'include'
      });
      
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      
      const data = await response.json(); 
      setSuggestions(data);
      setShowSuggestions(true) 
    } catch (error) {
      console.error("Error fetching suggestions:", error);
      setSuggestions([]);
      setShowSuggestions(false);
    }
  };

  const deleteGroup = async () => {
          const response = fetch(`http://localhost:8082/group/delete/${groupId}` , {
              method : "DELETE" ,
              credentials : "include"
          })
          if(!response.ok){
            throw new Error("could not delete you account") ;
          }
          //in case of success force logout ;
      }


  
  
  
    return (
      <Box sx={{
        minHeight: '100vh',
        backgroundColor: '#fafafa',
        pb: 6
      }}>
        {/* Cover Photo Section */}
        <Box sx={{ position: 'relative', mb: 12 }}>
          <Paper
            elevation={0}
            sx={{
              position: 'relative',
              height: 280,
              backgroundImage: !coverPic ? 'url("/background.jpg")' : `url("http://localhost:8082/group/${groupId}/get/cover")` , 
              backgroundSize: 'cover',
              backgroundPosition: 'center',
              borderRadius: 0,
              overflow: 'hidden'
            }}
          >
            {/* Overlay */}
            <Box
              sx={{
                position: 'absolute',
                top: 0,
                left: 0,
                right: 0,
                bottom: 0,
                background: 'linear-gradient(180deg, rgba(0,0,0,0.1) 0%, rgba(0,0,0,0.3) 100%)'
              }}
            />
  
            {/* Edit Cover Button */}
            
                      <IconButton
                component = "label" 
                fullWidth 
                disableRipple
              sx={{
                position: 'absolute',
                right: 16,
                bottom: 16,
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                backdropFilter: 'blur(4px)',
                transition: 'all 0.2s',
                '&:hover': {
                  backgroundColor: 'white',
                  transform: 'scale(1.05)'
                } 
              }}
            >
  
  
  
              <Camera size={20} >
                  
                  </Camera>
                  <VisuallyHiddenInput type ="file" 
                  onChange={(e) => {
                     const file = e.target.files[0];
                     file && coverSubmit(file) ;
                  }}
                  
                  />
              
            </IconButton>
                    
            
          </Paper>
  
          {/* Profile Avatar */}
          <Container maxWidth="lg">
            <Box
              sx={{
                position: 'absolute',
                left: '50%',
                transform: 'translateX(-50%)',
                bottom: -80,
                zIndex: 10
              }}
            >
              <Box sx={{ position: 'relative' }}  >
                {profile ? <Avatar
                  variant="rounded"
                  src={`http://localhost:8082/group/${groupId}/get/avatar`}
                  sx={{
                    width: 160,
                    height: 160,
                    border: '6px solid white',
                    borderRadius: '20px',
                    boxShadow: '0 8px 24px rgba(0,0,0,0.15)',
                    fontSize: '3rem',
                    fontWeight: 700,
                    background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)'
                  }}
                /> : <Avatar
                  variant="rounded"
                  sx={{
                    width: 160,
                    height: 160,
                    border: '6px solid white',
                    borderRadius: '20px',
                    boxShadow: '0 8px 24px rgba(0,0,0,0.15)',
                    fontSize: '3rem',
                    fontWeight: 700,
                    background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)'
                  }}
                >
                  
                </Avatar>}
                
                      <IconButton
                component = "label" 
                fullWidth 
                disableRipple
                  sx={{
                    position: 'absolute',
                    right: -8,
                    bottom: -8,
                    width: 44,
                    height: 44,
                    backgroundColor: '#3b82f6',
                    color: 'white',
                    boxShadow: '0 4px 12px rgba(59, 130, 246, 0.4)',
                    '&:hover': {
                      backgroundColor: '#2563eb',
                      transform: 'scale(1.1)'
                    }
                  }}
                >
                  <Edit size={20} />
                  <VisuallyHiddenInput type ="file"
                  onChange={(e) => {
                     const file = e.target.files[0];
                     file && avatarSubmit(file) ;
                  }}
                 
                   />
                </IconButton>
                   
                
                
              </Box>
            </Box>
          </Container>
        </Box>
  
        {/* Content */}
        <Container maxWidth="lg">
          {updateSuccess && (
            <Alert severity="success" sx={{ mb: 3, borderRadius: '12px' }}>
              Profile updated successfully!
            </Alert>
          )}
          <Snackbar
                                open={snackbarOpen}
                                autoHideDuration={6000}
                                onClose={handleSnackbarClose}
                                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                                 >
                                { success ? <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
                                  group Updated successfully!
                                 </Alert> :  error ? <Alert onClose={handleSnackbarClose} severity="error" sx={{ width: '100%' }}>
                                  an error occured while Updating the group! Please try again later 
                                </Alert> : <Typography> hello shit </Typography> }
                                            
                                 </Snackbar>
  
          <Stack spacing={3} sx={{ paddingTop :'15px'
}}>
            {/* User Information */}
            <Paper
              elevation={0}
              sx={{
                p: 4,
                borderRadius: '16px',
                border: '1px solid #e0e0e0', 
              }}
            >
              <Box
                component="form"
                onSubmit={handleSubmit(onSubmit)}
                sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}
              >
                
                <Stack direction="row" alignItems="center" spacing={1.5}>
                  <User size={24} color="#3b82f6" />
                  <Typography variant="h5" sx={{ fontWeight: 700, color: '#1a1a1a' }}>
                    Group Information
                  </Typography>
                </Stack>
  
                <Divider />
  
                <Stack  spacing={2} width={'90%'}>
                    <Controller
        name='name'
        control = {control}
        rules={{required: "please give a name"}}
        render={({field}) => (
            <TextField
            {...field}
            label="name"
            variant="outlined"
            fullWidth
            error={!!errors.name}
            helperText={errors.name ? errors.name.message : ''}
            />
        )}
        />

        <Controller
        name='description'
        control = {control}
        rules={{required: "please give a description"}}
        render={({field}) => (
            <TextField
            {...field}
            label="Description"
            variant="outlined"
            fullWidth
            error={!!errors.description}
            helperText={errors.description ? errors.description.message : ''}
            ></TextField>
        )}
        />
        <Controller
        name='theme'
        control = {control}
        rules={{required: "please give a theme"}}
        render={({field}) => (
            <TextField
            {...field}
            label="theme"
            variant="outlined"
            fullWidth
            error={!!errors.theme}
            helperText={errors.theme ? errors.theme.message : ''}
            ></TextField>
        )}
        />

        
          <FormControl component="fieldset" >
            <FormLabel component="legend">Join Policy</FormLabel>
            <RadioGroup  value = {joinPolicy} onChange = {(e) => {
                
                    setJoinPolicy(e.target.value) ;
                }
            }  row >
              <FormControlLabel 
                value="OPEN" 
                control={<Radio />} 
                label="OPEN" 
              />
              <FormControlLabel 
                value="CLOSED" 
                control={<Radio />} 
                label="CLOSED" 
              />
              <FormControlLabel 
                value="REQUESTED" 
                control={<Radio />} 
                label="REQUESTED" 
              />
            </RadioGroup>
            <FormHelperText>{error ? error.message : ''}</FormHelperText>
          </FormControl>
        
        

        <ToggleButtonGroup
      value={privacy}
      onChange={handlePrivacy}
      exclusive
      aria-label="text formatting"
    >
      <ToggleButton value="PRIVATE">Private</ToggleButton>
      <ToggleButton value="PUBLIC">Public</ToggleButton>
    </ToggleButtonGroup>
        <Box>
        <Button type="submit" variant="contained" color="primary">
                 Update Group
        </Button>
        </Box>
                  </Stack>
                  </Box>
            </Paper>
            <Paper
              elevation={0}
              sx={{
                p: 4,
                borderRadius: '16px',
                border: '1px solid #e0e0e0', 
              }}
            >
              <Box
                sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}
              >
                
                <Stack direction="row" alignItems="center" spacing={1.5}>
                  <User size={24} color="#3b82f6" />
                  <Typography variant="h5" sx={{ fontWeight: 700, color: '#1a1a1a' }}>
                    Transfer Ownership
                  </Typography>
                </Stack>
  
                <Divider />
  
                <Stack  spacing={2} width={'90%'}>
        
            {!newOwner ? <Box> <TextField
              value={inputValue} 
              onChange={async (e) => {
                const value = e.target.value;
                setInputValue(value);
                if (value === "") { 
                  setShowSuggestions(false);
                  setSuggestions([]);
                } else {
                  fetchSuggestions(value);
                }
            }}

            label="specify an admin of the group"
            variant="outlined"
            fullWidth
            error={!!errors.theme}
            helperText={errors.theme ? errors.theme.message : ''}
            />
            {(showSuggestions && suggestions.length > 0) && (
                          <Paper 
                            elevation={3} 
                            sx={{ 
                              position: 'relative', 
                              zIndex: 1, 
                              width: '100%',
                              mt: 1, 
                              maxHeight: '200px', 
                              overflowY: 'auto' 
                            }}
                          >
                            {suggestions.map(author => (
                              <Box 
                                onClick={() => {
                                  setNewOwner(author);
                                  setInputValue('');
                                  setSuggestions([]);
                                  setShowSuggestions(false);
                                }} 
                                key={author}
                                sx={{
                                  p: 1,
                                  cursor: 'pointer',
                                  '&:hover': {
                                    backgroundColor: 'rgba(0, 0, 0, 0.04)',
                                  },
                                }}
                              >
                                {author}
                              </Box>
                            ))}
                          </Paper>
                        )} </Box> : 
                        <Chip 
                            sx={{ margin: "3px" }} 
                            label={`@${newOwner}`} 
                            onDelete={() => setNewOwner('')}
                                />  }
        

        
        
        

        <Box>
        <Button type="submit" variant="contained" color="primary" onClick={() => { transferOwnership()}}>
                 Change Owner
        </Button>
        </Box>
                  </Stack>
                  </Box>
            </Paper>
            <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: '16px',
              border: '1px solid #e0e0e0'
            }}
          >
            <Button variant="outlined" color="error" startIcon={<Trash />} onClick={() => {deleteGroup()}}>
               Delete Group
             </Button> 
          </Paper>
          </Stack>
        </Container>
      </Box>
    )
  
}

export default GroupProfilePage
