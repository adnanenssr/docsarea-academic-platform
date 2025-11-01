// App.js
import React, { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
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
  Stack
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { FormatColorResetSharp } from '@mui/icons-material';


// Styled component for the visually hidden input.
// This is a standard and accessible way to handle custom file inputs.
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

// The component for the file upload form
export function UploadForm({ onClose , groupId , r }  ) {
  const [authors, setAuthors] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [fileName, setFileName] = useState('');
  const [shared, setShared] = useState([]);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [showSharedSuggestions, setShowSharedSuggestions] = useState(false);
  const [inputSharedValue, setInputSharedValue] = useState('');
  const [isShared , setIsShared] = useState(false) ;
  const [selectedValue, setSelectedValue] = useState('PRIVATE'); 

  const [uploadUrl , setUploadUrl] = useState("") ;

 useEffect(() => {
    groupId ? setUploadUrl(`http://localhost:8082/upload/${groupId}`) :  setUploadUrl(`http://localhost:8082/upload`) ;
  }, [groupId])
   

  // Initialize the form hook with default values
  const { control, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      title: '',
      description: '',
      file: null,
      authors: [],
      shared : [] ,
      downloadable : false ,
      // Set PRIVATE as default value for the radio group
      accessibility: 'PRIVATE', 
    },
  });

  function createFile(name, content, type) {
  return new File([content], name, { type: type });
}  



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

    const fileContent = createFile(data.file.name , data.file , "application/pdf");
    const formData = new FormData() ;
    formData.append("data" , new Blob([JSON.stringify(fileData)], { type: "application/json" })
    ) ;
    formData.append("file" , fileContent) ;
    const response = await fetch(uploadUrl, {
      method : "POST" ,
     
      credentials : "include" ,
      body : formData 
    })
    console.log('Form data submitted:', formData);
    setSnackbarOpen(true); // Show a success message
    onClose(); // Close the dialog after submission
    r(prev => prev + 1) ;
  };
  
  const fetchSuggestions = async (value , arr , setArr ) => {
    try {
      // Re-enabled the original fetch call to your localhost API
      const response = await fetch(`http://localhost:8082/user/suggestions?value=${value}`, {
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
    <Box
      component="form"
      onSubmit={handleSubmit(onSubmit)}
      sx={{
        display: 'flex',
        flexDirection: 'column',
        gap: 2,
        p: 3, 
        margin: '0 auto', 
      }}
    >
      <Typography variant="h6" align="center" gutterBottom>
        Upload File
      </Typography>

      <Box display={'flex'} sx={{ flexDirection : "row" }}>
        <Stack spacing={1} sx={{ width : '70%' , flex :'1' , padding : "8px"}}>


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
      <Box>
      <Button type="submit" variant="contained" color="primary">
        Submit
      </Button>
      </Box>

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
      </Stack>
      
      


      {/* --------------------------------------------------------------------------------------------
      ---------------------------------------------------------------------------------------------
      ---------------------------------------------------------------------------------------- */}

       <Box width={'30vw'}>
        <Stack sx={{padding : "8px"}}>
          
      <Controller
        name="file"
        control={control}
        rules={{ required: 'Please select a file' }}
        render={({ field: { onChange, value, ...rest } }) => (
          <Paper
            elevation={1}
            sx={{
              padding : "8px" ,
              display: 'flex',
              flex : "1" ,
              flexDirection: 'column',
              gap: 1,
              border: `2px dashed ${errors.file ? 'red' : '#ccc'}`,
              textAlign: 'center',
              '&:hover': {
                backgroundColor: '#a7a9a888',
              }
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
                  onChange(file);
                  setFileName(file ? file.name : '');
                }}
                {...rest}
              />
              Select File
            </Button>
            
            {fileName && (
              <Typography variant="outlined" color="text.secondary" sx={{ backgroundColor: 'transparent'  }}>
                File selected: {fileName.length > 42 ? fileName.slice(0,30).concat("...") : fileName }
              </Typography>
              
            )}
            
            {errors.file && (
              <Typography color="error" variant="body2" sx={{ textAlign: 'center' }}>
                {errors.file.message}
              </Typography>
            )}
            
          </Paper>
        )}
      />
    
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
              
      
      </Stack>
      </Box>
      </Box>
       

    </Box>
  );
}

// The main component that manages the dialog
export default function UploadDialog( {groupId , r }) {
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
      <Button onClick={handleOpen} variant="contained">
        Add Paper 
      </Button>
      <Dialog 
        open={open} 
        onClose={handleClose}
        PaperProps={{
          sx: {
            width: '85vw',
            maxWidth: 'none',
          }
        }}
      >
        <DialogContent>
          <UploadForm onClose={handleClose} groupId = {groupId} r={r}/>
        </DialogContent>
      </Dialog>
    </div>
  );
}