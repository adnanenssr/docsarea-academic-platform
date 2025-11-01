import { Alert, Box, Button, Dialog, DialogContent, FormControl, FormControlLabel, FormHelperText, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, Snackbar, Stack, TextField, ToggleButton, ToggleButtonGroup, Typography } from '@mui/material'
import React, { useState } from 'react'
import { Controller, useForm } from 'react-hook-form'

function AddGroupForm({onClose}) {

    const {control , handleSubmit , reset , formState : {errors}} = useForm() ;

    const [onRemovePolicy , setOnRemovePolicy] = useState("KEEP_FILES_ON_MEMBER_REMOVAL") ;
    const [groupId , setGroupId] = useState(null) ;
    const [snackbarOpen , setSnackbarOpen] = useState(false) ;
    const [success , setSuccess] = useState(false) ;
    const [error , setError] = useState(false) ;
    const [privacy, setPrivacy] = useState('PUBLIC');

    const onSubmit = async (data) => {
        try{
        const response = await fetch(`http://localhost:8082/group/create` , {
            method : "POST" ,
            credentials : "include" ,
            headers: {
                 "Content-Type": "application/json", // important for JSON payload
                  },
            body : JSON.stringify({
                name : data.name ,
                description : data.description ,
                theme : data.theme ,
                joinPolicy : data.joinPolicy ,
                privacy : privacy ,
                strategy : onRemovePolicy
            })
        }) ;
        if(!response.ok){
            throw new Error("could not create group! Please try again")
        }
        const result = await response.json() ;
        if(result){ setGroupId(data.id)}
        
            setError(false) ;
            setSuccess(true) ;
            setSnackbarOpen(true) ;
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


    const  handleChange = (e) => {
         
        setOnRemovePolicy(e.target.value) ;
         
    };

    

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

        <Controller
        name="joinPolicy"
        control= {control}
        rules={{ required: 'Please select a join Policy option' }}
        render={({ field , fieldState: { error } }) => (
          <FormControl component="fieldset" error={!!error}>
            <FormLabel component="legend">Join Policy</FormLabel>
            <RadioGroup {...field}  row >
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
        )}
        />

        
        <FormControl fullWidth>
        <InputLabel >Choose Files Policy</InputLabel>
        <Select required
          value={onRemovePolicy}
          label="Choose Files Policy"
          onChange={handleChange}
        >
          <MenuItem value={"KEEP_FILES_ON_MEMBER_REMOVAL"}>KEEP FILES ON MEMBER REMOVAL</MenuItem>
          <MenuItem value={"REMOVE_FILES_ON_MEMBER_REMOVAL"}>REMOVE FILES ON MEMBER REMOVAL</MenuItem>
          <MenuItem value={"TRANSFERE_FILES_TO_OWNER"}>TRANSFERE FILES TO OWNER</MenuItem>
        </Select>
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
                 Create Group
        </Button>
        </Box>
</Stack>
    </Box>
    
  )
}


function AddGroup() {
    const [open, setOpen] = useState(false);
    
      const handleOpen = () => {
        setOpen(true);
      };
    
      const handleClose = () => {
        setOpen(false);
      }
  return (
    <Box>
        

    <Button onClick={handleOpen} variant="contained">
            Create Group
          </Button>
    <Dialog 
            open={open} 
            onClose={handleClose}
            
            
          >
            <DialogContent>
              <AddGroupForm onClose={handleClose} />
            </DialogContent>
          </Dialog>
    </Box>

  )
}

export default AddGroup
