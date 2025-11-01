import { Alert, Box, Button, Checkbox, Dialog, DialogContent, FormControl, FormControlLabel, FormHelperText, FormLabel, Radio, RadioGroup, Snackbar, Stack, TextField, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { Controller, useForm } from 'react-hook-form';

function AddMemberForm({groupId , onClose}) {

    const {control , handleSubmit , reset , setValue , watch, formState : {errors}} = useForm({
    defaultValues: {
      username: "",
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
    
    const onSubmit = async (data) => {
        const memberInfo = {
            username : data.username ,
            role : data.role ,
            reviewFile : data.fileReview ,
            reviewJoinRequest : data.joinRequestReview ,
            uploadPermission : data.uploadPermission ,
            invitePermission : data.invitePermission
        }
        try{
        const response = await fetch(`http://localhost:8082/${groupId}/member/add` , {
            method : "POST" ,
            credentials : "include" ,
            headers : {
                "content-type" : "application/json"
            } ,
            body : JSON.stringify(memberInfo)
        }) ;
        if(!response.ok){
            throw new Error("could not add member to group , Please try again later") ;
        }
        setSuccess(true) ;
        setError(false) ;
        setSnackbarOpen(true) ;
        onClose() ;
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
            <Box>
            <Controller
                    name='username'
                    control = {control}
                    rules={{required: "please specify a user"}}
                    render={({field}) => (
                        <TextField
                        {...field}
                        label="member"
                        variant="outlined"
                        fullWidth
                        error={!!errors.name}
                        helperText={errors.name ? errors.name.message : ''}
                        />
                    )}
                    />
                    </Box>



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
                        <Button type='submit'>add Member</Button>
                        </Box>

            
        </Stack>
    </Box>
    </Box>
  )
}

function AddMember({groupId}) {
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
            Add Member
          </Button>
    <Dialog 
            open={open} 
            onClose={handleClose}
            
            
          >
            <DialogContent>
                <Box>
              <AddMemberForm groupId = {groupId} onClose={handleClose} />
              </Box>
            </DialogContent>
          </Dialog>
    </Box>

  )
}

export default AddMember