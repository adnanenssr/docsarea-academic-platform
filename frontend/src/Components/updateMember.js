import { Alert, Box, Button, Checkbox, Dialog, DialogContent, FormControl, FormControlLabel, FormHelperText, FormLabel, IconButton, Radio, RadioGroup, Snackbar, Stack, TextField, Typography } from '@mui/material'
import { Edit } from 'lucide-react';
import React, { useEffect, useState } from 'react'
import { Controller, useForm } from 'react-hook-form';

function UpdateMemberForm({onClose , groupId , username}) {
    const {control , handleSubmit , reset , setValue , watch, formState : {errors}} = useForm({
        defaultValues: {
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


        useEffect(() =>{
            let isMounted = true ;
            const getMember = async () => {
                try{
                const response = await fetch(`http://localhost:8082/get/member/${groupId}/${username}` , 
                    {
                        method : "GET" ,
                        credentials :"include" ,
                    }
                )
                if(!response.ok){
                    throw new Error("an error occured while fetching the member configuration! please try again later") ;
                }
                const data = await response.json() ;
                if(data && isMounted){
                    setValue("fileReview", data.reviewFile);
                    setValue("joinRequestReview", data.reviewJoinRequest);
                    setValue("role", data.role);
                    setValue("uploadPermission", data.uploadPermission);
                    setValue("invitePermission", data.invitePermission);
                    reset({
                        role: data.role || "MEMBER",
                        uploadPermission: data.uploadPermission || "REVIEWED",
                        fileReview: data.reviewFile || false,
                        joinRequestReview: data.reviewJoinRequest || false,
                        invitePermission: data.invitePermission|| false,
                    })
                }
            }
            catch(err){
                setSuccess(false) ;
                setError(true) ;
                setSnackbarOpen(true) ;
            }
            };
            getMember() ;
             return () => {
      isMounted = false; // cleanup
    };

            
        } , [groupId , username])
        
        const onSubmit = async (data) => {
            const memberInfo = {
                role : data.role ,
                reviewFile : data.fileReview ,
                reviewJoinRequest : data.joinRequestReview ,
                uploadPermission : data.uploadPermission ,
                invitePermission : data.invitePermission
            }
            try{
            const response = await fetch(`http://localhost:8082/${username}/member/update/${groupId}` , {
                method : "PUT" ,
                credentials : "include" ,
                headers : {
                    "content-type" : "application/json"
                } ,
                body : JSON.stringify(memberInfo)
            }) ;
            if(!response.ok){
                throw new Error("could not update this member , Please try again later") ;
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
                
                <Typography>Update Member : @{username}</Typography>
                        
    
    
    
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

function UpdateMember({groupId , username}) {
  const [open, setOpen] = useState(false);
      
        const handleOpen = () => {
          setOpen(true);
        };
      
        const handleClose = () => {
          setOpen(false);
        }
    return (
      <Box>
          
  
      <IconButton onClick={handleOpen} >
              <Edit/>
            </IconButton>
      <Dialog 
              open={open} 
              onClose={handleClose}
              
              
            >
              <DialogContent>
                  <Box>
                <UpdateMemberForm groupId={groupId} username={username} onClose={handleClose} />
                </Box>
              </DialogContent>
            </Dialog>
      </Box>
  
    )
}

export default UpdateMember


