import React, { useEffect, useState } from 'react';
import { useForm, Controller, set } from 'react-hook-form';
import {
  Button,
  Dialog,
  DialogContent,
  Typography,
  Box,
  TextField,
  Paper,
  Chip,
  FormControl,
  RadioGroup,
  FormLabel,
  Radio,
  Checkbox,
  FormControlLabel,
  Snackbar,
  Alert,
  FormHelperText,
  Stack,
  Grid
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { useParams } from 'react-router-dom';
import PdfViewer from './PdfViewer';
import UpdateDocument from './updateDocument';


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




function UpdateFile({ onClose , groupId } ) {

  const {fileId} = useParams() ;

  const [authors, setAuthors] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [fileName, setFileName] = useState(null);
  const [shared, setShared] = useState([]);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [showSharedSuggestions, setShowSharedSuggestions] = useState(false);
  const [inputSharedValue, setInputSharedValue] = useState('');
  const [isShared , setIsShared] = useState(false) ;
  const [selectedValue, setSelectedValue] = useState('PRIVATE'); 
  const [data , setData] = useState(null) ;

  const { control, reset ,  handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      title: '',
      description: '',
      authors: [],
      shared: [],
      downloadable: false,
      accessibility: 'PRIVATE', 
    },
  });


    useEffect(() => {
    let isMounted = true; // prevents setting state if component unmounts

    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8082/file/details/${fileId}`, {
          method: "GET",
          credentials: "include",
        });

        if (!response.ok) {
          throw new Error("Could not fetch file details");
        }

        const json = await response.json();

        // ✅ only set state if not null and component is still mounted
        if (json && isMounted) {
          setData(json);
          reset({
            title: json.title || '',
            description: json.description || '',
            authors: json.authors || [],
            shared: json.shared || [],
            downloadable: json.downloadable || false,
            accessibility: json.accessibility || 'PRIVATE',
          });
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false; // cleanup
    };
  }, [fileId , reset]);
 
   

  // Initialize the form hook with default values
  

 



  // Function to handle form submission
  const onSubmit = async (data) => {
    // Include authors from state in the form data
    const fileData = {
      title : data.title,
      description : data.description ,
      downloadable : data.downloadable ,
      accessibility : selectedValue ,
      shared : shared ,
      authors: authors 
    };

    
    
    const response = await fetch(`http://localhost:8082/file/details/update/${fileId}`, {
      method : "PUT" ,
      credentials : "include" ,
      headers: {
        "Content-Type": "application/json", // important for JSON payload
      },
      body : JSON.stringify(fileData) 
    }) ;
    if(!response.ok){throw new Error("error while updating file details ")}
    setSnackbarOpen(true); // Show a success message
  };
  
  const fetchSuggestions = async (value , arr , setArr ) => {
    try {
      // Re-enabled the original fetch call to your localhost API
      const response = await fetch(`https://localhost:8443/suggestions?value=${value}`, {
        method: "GET",
        
        credentials: 'include'
      });
      
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      
      const data = await response.json(); 
      const uniqueData = data.filter(val => !arr.includes(val));
      setSuggestions(uniqueData); // Set the state with the actual data

      (setArr === "authors") ? setShowSuggestions(true) : setShowSharedSuggestions(true);
    } catch (error) {
      console.error("Error fetching suggestions:", error);
      setSuggestions([]);
      setShowSuggestions(false);
    }
  };

  // Function to handle the snackbar close event
  const handleSnackbarClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackbarOpen(false);
  };
  

    
  return (
    <Box width={'100vw'}>

        <Box display={'flex'} sx={{flexDirection : 'row' , padding : '10px'}} >
            <Stack spacing={2} sx={{width : '55vw'}}>
                <UpdateDocument fileId={fileId}/>
                    </Stack>









<Stack spacing={1} sx={{ padding : "15px" , width : '45vw'}}>

                    <Box
                          component="form"
                          onSubmit={handleSubmit(onSubmit)}
                          sx={{
                        
                            display: 'flex',
                            flexDirection: 'column',
                            gap: 2,
                            
                            margin: '0 auto', 
                          }}
                        >
                          <Typography variant="h6" align="center" gutterBottom>
                            Upload File
                          </Typography>
                    
                          
                            
                    
                    
                          {/* Controller for the title field */}
                          <Controller 
                            name='title'
                            control={control}
                            rules={{ required: 'Please give a title' }}
                            render={({ field }) => (
                              <TextField 
                                type='text' 
                                {...field} 
                                label="Title"
                                variant="outlined"
                                fullWidth
                                error={!!errors.title}
                                helperText={errors.title ? errors.title.message : ''}
                              />
                            )} 
                          />
                    
                          {/* Controller for the description field */}
                          <Controller 
                            name='description'
                            control={control}
                            rules={{ required: 'Please give a description' }}
                            render={({ field }) => (
                              <TextField 
                                {...field} 
                                label="Description"
                                variant="outlined"
                                fullWidth
                                multiline 
                                minRows={3}
                                error={!!errors.description}
                                helperText={errors.description ? errors.description.message : ''}
                              />
                            )} 
                          />
                    
                          {/* Controller for authors with a Chip input and suggestions */}
                          <Controller
                            name='authors'
                            control={control}
                            render={() => (
                              <Box display={'flex'} flexDirection={'column'}>
                                <Box sx={{ margin: "5px" }}>
                                  {authors.map(author => (
                                    <Chip 
                                      sx={{ margin: "3px" }} 
                                      label={`@${author}`} 
                                      key={`${author}`} 
                                      onDelete={() => setAuthors(authors.filter(a => a !== author))}
                                    />
                                  ))}
                                </Box>
                                <TextField 
                                  label={'Add authors'}
                                  value={inputValue} 
                                  onChange={async (e) => {
                                    const value = e.target.value;
                                    setInputValue(value);
                                    if (value === "") { 
                                      setShowSuggestions(false);
                                      setSuggestions([]);
                                    } else {
                                      fetchSuggestions(value , authors , "authors");
                                    }
                                  }}
                                />
                                {showSuggestions && suggestions.length > 0 && (
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
                                          setAuthors([...authors, author]);
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
                                )}
                              </Box>
                            )} 
                          />
                          
                          {/* Controller to manage the file input */}
                    
                          
                          
                    
                         
                    
                          {/* Submit button */}
                          
                    
                          {/* Snackbar for the success message */}
                          <Snackbar
                            open={snackbarOpen}
                            autoHideDuration={6000}
                            onClose={handleSnackbarClose}
                            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                          >
                            <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
                              File submitted successfully! Check the console for the form data.
                            </Alert>
                          </Snackbar>

                          <Controller
                name="downloadable"
                control={control}
                defaultValue={false}
                render={({ field }) => 
                  <FormControlLabel 
                    control={<Checkbox {...field} checked={field.value} onChange={(e) => field.onChange(e.target.checked)} />} 
                    label="Enable Download" 
                  />
                }
              />

              <Controller
        name="accessibility"
        control={control}
        rules={{ required: 'Please select an accessibility option' }}
        render={({ field , value , fieldState: { error } }) => (
          <FormControl component="fieldset" error={!!error}>
            <FormLabel component="legend">Accessibility</FormLabel>
            <RadioGroup {...field} value={selectedValue} row onChange={(e) => {
              
              setSelectedValue(e.target.value) ;
               (e.target.value === "SHARED") ? setIsShared(true) : setIsShared(false);
            }}>
              <FormControlLabel 
                value="PRIVATE" 
                control={<Radio />} 
                label="Private" 
              />
              <FormControlLabel 
                value="USERS" 
                control={<Radio />} 
                label="Users" 
              />
              <FormControlLabel 
                value="PUBLIC" 
                control={<Radio />} 
                label="Public" 
              />
              <FormControlLabel 
                value="SHARED" 
                control={<Radio />} 
                label="Shared" 
              />
            </RadioGroup>
            <FormHelperText>{error ? error.message : ''}</FormHelperText>
          </FormControl>
        )}
      />
         


        

       {isShared && <Controller
        name='shared'
        control={control}
        render={() => (
          <Box display={'flex'} flexDirection={'column'}>
            <Box sx={{ margin: "5px" }}>
              {shared.map(author => (
                <Chip 
                  sx={{ margin: "3px" }} 
                  label={`@${author}`} 
                  key={`${author}`} 
                  onDelete={() => setShared(shared.filter(a => a !== author))}
                />
              ))}
            </Box>
            <TextField 
              label={'share with'}
              value={inputSharedValue} 
              onChange={async (e) => {
                const value = e.target.value;
                setInputSharedValue(value);
                if (value === "") { 
                  setShowSharedSuggestions(false);
                  setSuggestions([]);
                } else {
                  fetchSuggestions(value , shared , "shared");
                }
              }}
            />
            {showSharedSuggestions && suggestions.length > 0 && (
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
                      setShared([...shared, author]);
                      setInputSharedValue('');
                      setSuggestions([]);
                      setShowSharedSuggestions(false);
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
            )}
            
          </Box>
        )} 
      />
      }

      <Box>
                          <Button type="submit" variant="contained" color="primary">
                            Submit
                          </Button>
                          </Box>
              
      </Box>
            </Stack>

      

      </Box>
       
    </Box>
                
            

    

        






    
  )
}

export default UpdateFile
