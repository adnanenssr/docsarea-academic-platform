import { Alert, Box, Button,  FormControl, FormControlLabel, FormHelperText, FormLabel, Radio, RadioGroup,  Snackbar, Stack, TextField, ToggleButton, ToggleButtonGroup, Typography } from '@mui/material'
import React, { useState , useEffect} from 'react'
import { Controller, useForm } from 'react-hook-form'

import { useParams } from 'react-router-dom';

function UpdateGroup() {


    const {groupId} = useParams() ;
    const {control , handleSubmit , reset , formState : {errors}} = useForm() ;

    
    const [snackbarOpen , setSnackbarOpen] = useState(false) ;
    const [success , setSuccess] = useState(false) ;
    const [error , setError] = useState(false) ;
    const [privacy, setPrivacy] = useState('PUBLIC');
    const [joinPolicy , setJoinPolicy] = useState('OPEN') ;

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
      }
    } catch (err) {
      console.error(err);
    }
  };

  fetchGroup();
         return () => {
      isMounted = false; // cleanup
    };

    } , [groupId , reset])

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
            }
        }
        catch(e){
            setError(true) ;
            setSuccess(false) ;
            setSnackbarOpen(true) ;

        }
    }

    const handleSnackbarClose = () =>{
        setSnackbarOpen(false) ;
    }


    

    

  const handlePrivacy = (event, privacy) => {
     if (privacy !== null) {
    setPrivacy(privacy);
     }
  };

  return (
    
    <Box component='form' onSubmit={handleSubmit(onSubmit)} >
        <Snackbar
                open={snackbarOpen}
                autoHideDuration={6000}
                onClose={handleSnackbarClose}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                 >
                { success ? <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
                  group created successfully!
                 </Alert> :  error ? <Alert onClose={handleSnackbarClose} severity="error" sx={{ width: '100%' }}>
                  an error occured while creating the group! Please try again later 
                </Alert> : <Typography> hello shit </Typography> }
                            
                 </Snackbar>
        <Stack spacing={2} sx={{ p :"24px"}}>
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
    
  )
}

  

export default UpdateGroup
