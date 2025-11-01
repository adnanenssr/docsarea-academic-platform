import React from 'react';
import { useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { TextField, Button, Checkbox, FormControlLabel, Stack, Box, Typography } from '@mui/material';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import { Link } from 'react-router-dom';

export default function RegisterForm() {
  const { handleSubmit, control, formState: { errors } } = useForm();
  const [failed , setFailed] = useState(false) ;
  
  

  const onSubmit = async (data) => {

    setFailed(false) ;
    try{
    const response = await fetch("http://localhost:8082/register" ,{
      method: "POST", // HTTP method
      headers: {
        "Content-Type": "application/json", // important for JSON payload
      },
      body: JSON.stringify({
        username: data.username,
        email: data.email,
        password: data.password,
      }),
    });
    if (!response.ok){
        setFailed(true) ;
    }


    console.log(data.username);
}
catch(e){
    setFailed(true) ;
    console.log("error");
}
  };

  return (
                 <Box  display={'flex'} sx={{overflow: 'auto' ,backgroundColor : '#D6DAC8',
                width: '100vw' , height : '100vh', alignItems : "center" , justifyContent: "center"  }}>
                    <form onSubmit={handleSubmit(onSubmit)}>
                <Stack  display={'flex'} spacing={3} sx={{backgroundColor:'#FAF9EE',overflow: 'auto' , width : {
      xs: '80vw',  // for extra-small screens
      sm: '50vw',  // for small screens
      md: '40vw',  // for medium screens
      lg: '25vw',  // for large screens
    } , padding : '10px' , boxShadow:'2px' ,  boxShadow: 6  }}>
        
       <Typography variant ={'h2'} textAlign={'center'} sx={{ color : '#9CAFAA' , fontWeight : 'bold' , fontSize: 'clamp(2rem, 6vw, 3rem)' , margin: 'auto'  }} >Register</Typography>
        {/* Username */}
        <Controller 
          name="username"
          control={control}
          defaultValue=""
          rules={{ required: 'Username is required' }}
          render={({ field }) => 
            <TextField 
              {...field} 
              label="Username" 
              error={!!errors.username} 
              helperText={errors.username?.message} 
            />
          }
        />

        {/* Email */}
        <Controller
          name="email"
          control={control}
          defaultValue=""
          rules={{ 
            required: 'Email is required',
            pattern: { value: /\S+@\S+\.\S+/, message: 'Invalid email' }
          }}
          render={({ field }) => 
            <TextField 
              {...field} 
              label="Email" 
              error={!!errors.email} 
              helperText={errors.email?.message} 
            />
          }
        />

        {/* Password */}
        <Controller
          name="password"
          control={control}
          defaultValue=""
          rules={{ required: 'Password is required', minLength: { value: 6, message: 'Minimum 6 characters' } }}
          render={({ field }) => 
            <TextField 
              {...field} 
              label="Password" 
              type="password" 
              error={!!errors.password} 
              helperText={errors.password?.message} 
            />
          }
        />

        {/* Checkbox */}
        <Controller
          name="terms"
          control={control}
          defaultValue={false}
          rules={{ required: 'You must accept terms' }}
          render={({ field }) => 
            <FormControlLabel 
              control={<Checkbox {...field} checked={field.value} required />} 
              label="I accept terms and conditions" 
            />
          }
        />

        <Button type="submit" variant="contained">Submit</Button>
         <Typography component="p" sx={{ fontSize: '15px', fontFamily: 'poppins' }}>
      Do you already have an account?{" "}
      <Link to="/login" style={{ textDecoration: "none", color: "blue" }}>
        Login
      </Link>
    </Typography>
                        
         {failed && <Typography  component={'p'} sx={{ fontSize: '15px' , fontFamily : 'poppins' , color : 'red' , textAlign : 'center'}}> unkown error ! please try again later .
                        </Typography>}
                
      </Stack>

    </form>
    </Box>

  );
}
