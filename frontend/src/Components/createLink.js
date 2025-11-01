import { Alert, Box, Button, Checkbox, Dialog, DialogContent, FormControl, FormControlLabel, FormHelperText, FormLabel, IconButton, Radio, RadioGroup, Snackbar, Stack, TextField, Typography } from '@mui/material'
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { CheckIcon, CopyIcon } from 'lucide-react';
import React, { useEffect, useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import { ContentCopy } from '@mui/icons-material';

function CreateLinkForm({ setLink , setOpenLink , groupId , onClose}) {

    const {control , handleSubmit , reset , setValue , watch, formState : {errors}} = useForm({
    defaultValues: {
      expirationDate :"" ,
      role: "MEMBER",
      uploadPermission: "REVIEWED",
      fileReview: false,
      joinRequestReview: false,
      invitePermission: false,
    },
  }) ;

    const [isAdmin , setIsAdmin] = useState(false) ;
    const [isModerator , setIsModerator] = useState(false) ;
    const [snackbarOpen , setSnackbarOpen] = useState(false) ;
    const [error , setError] = useState(false) ;
    const [success , setSuccess] = useState(false) ;
    const [open , setOpen] = useState(false) ;
    
    const onSubmit = async (data) => {
        const linkInfo = {
            description : data.description ,
            expiresAt : data.expiresAt ,
            role : data.role ,
            reviewFile : data.fileReview ,
            reviewJoinRequest : data.joinRequestReview ,
            uploadPermission : data.uploadPermission ,
            invitePermission : data.invitePermission
        }
        try{
        const response = await fetch(`http://localhost:8082/create/invitation/${groupId}` , {
            method : "POST" ,
            credentials : "include" ,
            headers : {
                "content-type" : "application/json"
            } ,
            body : JSON.stringify(linkInfo)
        }) ;
        if(!response.ok){
            throw new Error("could not add member to group , Please try again later") ;
        }
        const data = await response.json() ;
        if(data){
        setLink(data.id)
        setSuccess(true) ;
        setError(false) ;
        setSnackbarOpen(true) ;
        setOpenLink(true) ;
        onClose() ;
        }
        
        
    }catch (err){
        setSuccess(false) ;
        setError(true) ;
        setSnackbarOpen(true) ;
    }

    }

    const role = watch("role");

    const handleSnackbarClose = () => {
        setSnackbarOpen(false) ;
    }
    useEffect(() =>{
    if (role === "ADMIN") {
      setValue("fileReview", true);
      setValue("joinRequestReview", true);
      setIsAdmin(true) ;
      setIsModerator(false) ;
      
    } else if (role === "MODERATOR") {
      setValue("fileReview", false);
      setValue("joinRequestReview", false);
      setIsModerator(true) ;
      setIsAdmin(false) ;
    } else {
      // MEMBER fallback
      setValue("fileReview", false);
      setValue("joinRequestReview", false);
      setValue("invitePermission", false);
      setIsModerator(false) ;
      setIsAdmin(false) ;
    }
  }, [role, setValue]);


  return (
    <Box sx={{ width :"40vw" }}>
    <Box component ='form' onSubmit={handleSubmit(onSubmit)}  sx={{padding : "24px"}}>
        <Snackbar
                        open={snackbarOpen}
                        autoHideDuration={6000}
                        onClose={handleSnackbarClose}
                        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                         >
                        { success ? <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
                          Member added successfully!
                         </Alert> :  error ? <Alert onClose={handleSnackbarClose} severity="error" sx={{ width: '100%' }}>
                          an error occured while adding the member! Please try again later 
                        </Alert> : <Typography> hello shit </Typography> }
                                    
                         </Snackbar>
        <Stack spacing={2} sx={{
           
            margin : "13px" ,
        }}>
            <Controller 
              name="description" 
              control={control}
              render={({field}) =>
                <TextField {...field} label="description" />
              }
              />

            <Controller 
              name="expiresAt" 
              control={control}
              render={({field}) =>
                 <LocalizationProvider dateAdapter={AdapterDayjs}>
                 <DemoContainer components={['DatePicker']}>
                 <DatePicker {...field} label="Choose Expiration Date" />
                 </DemoContainer>
                 </LocalizationProvider>
              }
              />
            



             <Controller
                name="role"
                control={control}
                render={({ field }) => 

            <FormControl component="fieldset" >
            <FormLabel component="legend">Role</FormLabel>
            <RadioGroup  {...field} 
              row >
              <FormControlLabel 
                value="MEMBER" 
                control={<Radio />} 
                label="MEMBER" 
              />
              <FormControlLabel 
                value="MODERATOR" 
                control={<Radio />} 
                label="MODERATOR" 
              />
              <FormControlLabel 
                value="ADMIN" 
                control={<Radio />} 
                label="ADMIN" 
              />
            </RadioGroup>
            
          </FormControl>}
          />
           <Controller
                name="uploadPermission"
                control={control}
                render={({ field }) => 

          <FormControl component="fieldset" >
            <FormLabel component="legend">Upload Files Permissions</FormLabel>
            <RadioGroup  {...field}
              row >
              <FormControlLabel 
                value="AUTO_PUBLISH" 
                control={<Radio />} 
                label="Auto-Publish" 
              />
              <FormControlLabel 
                value="FORBIDDEN" 
                control={<Radio />} 
                label="Forbidden" 
              />
              <FormControlLabel 
                value="REVIEWED" 
                control={<Radio />} 
                label="Reviewed" 
              />
            </RadioGroup>
            <FormHelperText></FormHelperText>
          </FormControl>
                          }
                          />


          {  (isAdmin || isModerator ) && <Controller
                          name="fileReview"
                          control={control}
                          defaultValue={isAdmin ? true : false}
                          render={({ field }) => 
                            <FormControlLabel 
                              control={<Checkbox {...field} checked={ field.value} onChange={(e) => isAdmin ? field.onChange(true)  : field.onChange(e.target.checked)} />} 
                              label="provide file review permission to this user " 
                            />
                          }
                        />}
                        {(isAdmin || isModerator ) && <Controller
                          name="joinRequestReview"
                          control={control}
                          defaultValue={isAdmin ? true : false}
                          render={({ field }) => 
                            <FormControlLabel 
                              control={<Checkbox {...field} checked={ field.value} onChange={(e) => isAdmin ? field.onChange(true)  : field.onChange(e.target.checked)} />} 
                              label="provide group's join requests review permission to this user " 
                            />
                          }
                        />}
                        { isAdmin && <Controller
                          name="invitePermission"
                          control={control}
                          defaultValue={false}
                          render={({ field }) => 
                            <FormControlLabel 
                              control={<Checkbox {...field} checked={field.value} onChange={(e) => field.onChange(e.target.checked)} />} 
                              label="provide invite other members permission to this user" 
                            />
                          }
                        />}

                        <Box>
                        <Button type='submit'>Create Link</Button>
                        </Box>

            
        </Stack>
    </Box>
    
    </Box>
  )
}

function GetLink({id , setOpenLink}){
    const [open , setOpen] = useState(false) ;
    const link = `http://localhost:8082/invitation/${id}` ;
    const [copy , setCopy] = useState(false) ;
    
    useEffect (() => {
        setOpen(true) ;
        setCopy(false) ;
    },[])

    const handleClose = () => {
        
        setOpen(false) ;
        setOpenLink(false) ;
    }

    const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(link);
      
        
      

    } catch (err) {
      console.error("Failed to copy: ", err);
    }
    setCopy(true)
    setTimeout(() => {
        setCopy(false);
       }, 2000);
  };

    return(
        <Snackbar  sx={{width : '70vw'}}
            open={open} 
            onClose={handleClose}
            anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
            autoHideDuration={6000}
          >
            
                <Box>
              <Alert icon={false} onClose={handleClose}>Link : {link} 
                <IconButton onClick={handleCopy}>
                    {copy ? <CheckIcon/> : <CopyIcon/> }
                </IconButton></Alert>
              </Box>
            
          </Snackbar>
    )
}

function CreateLink({groupId}) {
    const [open, setOpen] = useState(false);
    const [openLink , setOpenLink] = useState(false) ;
    const [linkId , setLinkId] = useState("") ;
    
      const handleOpen = () => {
        setOpen(true);
      };
    
      const handleClose = () => {
        setOpen(false);
      }
  return (
    <Box>
        

    <Button onClick={handleOpen} variant="contained">
            Create Invitation Link
          </Button>
    <Dialog 
            open={open} 
            onClose={handleClose}
            
            
          >
            <DialogContent>
                <Box>
              <CreateLinkForm setLink={setLinkId} setOpenLink = {setOpenLink} groupId = {groupId} onClose={handleClose} />
              </Box>
            </DialogContent>
          </Dialog>
          {openLink && <GetLink id={linkId} setOpenLink={setOpenLink} />}
    </Box>
    

  )
}

export default CreateLink