import { Alert, Box, Button, Grid, Paper, Snackbar, Stack, Typography } from '@mui/material';
import React, { useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import PdfViewer from './PdfViewer';
import { styled } from '@mui/material/styles';


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

function UpdateDocument({fileId}) {

    const { control, reset , handleSubmit, formState: { errors } } = useForm();
    const [fileName , setFileName] = useState(null) ;
    const [error , setError] = useState(false) ;
    const [success , setSuccess] = useState(true) ;
    const [snackbarOpen , setSnackbarOpen] = useState(false) ;
    const [counter , setCounter] = useState(1) ;


function createFile (fileContent , fileName , type){
    return new File([fileContent] , fileName , {type : type} ) ;
}


    

    const onSubmit =async (data) => {

        const file = createFile(data.file , data.file.name , "application/pdf") ;


        
        const formData = new FormData() ;
        formData.append("file" , file) ;

        try{
        const response = await fetch(`http://localhost:8082/file/file/update/${fileId}` , {
            method : "PUT" ,
            credentials : 'include' ,
            body : formData 
        })
        if(!response.ok){
            throw new Error("an error occured while updating the file!") ;
        }
        setError(false) ;
        setSuccess(true) ;
        setSnackbarOpen(true) ;
        setCounter((prev) => prev + 1) ;
        setFileName(null) ;
        reset() ;
        
        }catch(error){
            setError(true) ;
            setSuccess(false) ;
            setSnackbarOpen(true) ;

        }

  } ;

  const handleSnackbarClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackbarOpen(false);
  };


  return (
    <Stack spacing={2}>
        <Snackbar
                    open={snackbarOpen}
                    autoHideDuration={6000}
                    onClose={handleSnackbarClose}
                    anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                     >
                     { success ? <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
                    File updated successfully!
                    </Alert> :  error ? <Alert onClose={handleSnackbarClose} severity="error" sx={{ width: '100%' }}>
                     an error occured while updating the file! Please try again later 
                    </Alert> : <Typography> hello shit </Typography> }
                    
                    </Snackbar>
                    <Box component='form' onSubmit={handleSubmit(onSubmit)} >


                        <Grid display={'flex'} container spacing={2} >
                        
                            
                            <Grid size={9} >
                            
            <Controller
            name="file"
            control={control}
            rules={{ required: 'Please select a file' }}
            render={({ field: { onChange, value, ...rest } }) => (
              <Paper
                elevation={1}
                sx={{
                  
                  display: 'flex',
                  flex : "1" ,
                  flexDirection: 'column',
                  gap: 1,
                  border: `2px dashed ${errors.file ? 'red' : '#ccc'}`,
                  textAlign: 'center',

                  
                }}
              >
                
                <Button
                  component="label"
                  fullWidth
                  disableRipple
                  sx={{
                    backgroundColor: 'transparent',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                  }}
                >
                  <VisuallyHiddenInput
                    type="file"
                    onChange={(e) => {
                      const file = e.target.files[0];
                      setFileName(file.name);
                      onChange(file);
                    }}
                    
                    {...rest}
                  />
                  {fileName ? fileName.slice(0,35).concat("...") :  "Select File"}
                </Button>
                
              </Paper>
            )}
          />
          
    
                        </Grid>
                        <Grid size={3}>
                            <Stack >
                             
                                  <Button type="submit" variant="contained" color="primary">
                                    Update File
                                  </Button></Stack>
                                  
    
                        </Grid>
                        
                        </Grid>
                        
                        </Box>
                         <PdfViewer fileId={fileId} counter = {counter}></PdfViewer>
                        
                        </Stack>
  )
}

export default UpdateDocument
