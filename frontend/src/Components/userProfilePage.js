import { 
  Avatar, 
  Box, 
  Button, 
  IconButton, 
  Paper, 
  Stack, 
  TextField, 
  Typography,
  Container,
  Divider,
  Alert
} from '@mui/material'
import { Camera, User, Mail, Lock, Edit, Delete, Trash } from 'lucide-react'
import React, { useEffect, useState } from 'react'
import { styled } from '@mui/material/styles';

import { Controller, useForm } from 'react-hook-form';

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

function UserProfilePage() {
  const [updateSuccess, setUpdateSuccess] = useState(false);
  const [profile , setProfile] = useState('') ;
  const [coverPic , setCoverPic] = useState(null) ;
  const [username , setUsername] = useState("") ;
  const [r , setR] = useState(0) ;

  const { control, reset, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      firstname: '',
      lastname: '',
      bio: '',
      address: '',
    },
  });

  const {
    control: control1,
    reset: reset1,
    handleSubmit: handleSubmit1,
    formState: { errors: errors1 },
  } = useForm({
    defaultValues: {
      email: "",
    },
  });

  const {
    control: control2,
    handleSubmit: handleSubmit2,
    formState: { errors: errors2 },
  } = useForm({
    defaultValues: {
      oldPassword: '',
      newPassword: '',
    },
  });

  const {
    control: control3,
    reset: reset3,
    handleSubmit: handleSubmit3,
    formState: { errors: errors3 },
  } = useForm({
    defaultValues: {
      cover: "",
    },
  });

  const {
    control: control4,
    reset: reset4,
    handleSubmit: handleSubmit4,
    formState: { errors: errors4 },
  } = useForm({
    defaultValues: {
      avatar: "",
    },
  });

      const deleteUser = async () => {
          const response = fetch("http://localhost:8082/auth/delete" , {
              method : "DELETE" ,
              credentials : "include"
          })
          if(!response.ok){
            throw new Error("could not delete you account") ;
          }
          //in case of success force logout ;
      }
  


  useEffect(() => {
    const getUserInfo = async () => {
      const response = await fetch("http://localhost:8082/get/user", {
        method: "GET",
        credentials: "include"
      })
      if (!response.ok) {
        throw new Error("could not fetch user details");
      }
      const data = await response.json();
      if (data) {
        reset({
          firstname: data.firstname || '',
          lastname: data.lastname || '',
          bio: data.bio || '',
          address: data.address || '',
        });

        setProfile(data.profilePicUrl) ;
        setCoverPic(data.cover) ;
        setUsername(data.username) ;

        reset1({
          email: data.email || ''
        })
      }
    }
    getUserInfo();
  }, [reset, reset1 , r , setR]);




  const coverSubmit = async (file) => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file); // File object directly

    try {
        const response = await fetch("http://localhost:8082/api/save/cover", {
            method: "PUT",
            credentials: "include",
            body: formData
        });

        if (!response.ok) {
            throw new Error("Could not update your cover photo, please try again later");
        }
        setR(prev => prev + 1) ;

    } catch (err) {
        console.error(err);
    }
};

const avatarSubmit = async (file) => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file); // File object directly

    try {
        const response = await fetch("http://localhost:8082/api/save/avatar", {
            method: "PUT",
            credentials: "include",
            body: formData
        });

        if (!response.ok) {
            throw new Error("Could not update your cover photo, please try again later");
        }

        setR(prev => prev + 1) ;

    } catch (err) {
        console.error(err);
    }
};

  const onSubmit = async (data) => {
    const response = await fetch("http://localhost:8082/api/update/info", {
      method: "PUT",
      credentials: "include",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({
        firstname: data.firstname,
        lastname: data.lastname,
        bio: data.bio,
        address: data.address,
      })
    })

    if (!response.ok) {
      throw new Error("could not update user infomation");
    }
    setR(prev => prev + 1) ;
    setUpdateSuccess(true);
    setTimeout(() => setUpdateSuccess(false), 3000);
  }

  const onSubmit1 = async (data) => {
    const response = await fetch("http://localhost:8082/user/update", {
      method: "PUT",
      credentials: "include",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({
        email: data.email
      })
    })

    if (!response.ok) {
      throw new Error("could not update your email");
    }
    setUpdateSuccess(true);
    setTimeout(() => setUpdateSuccess(false), 3000);
  }

  const onSubmit2 = async (data) => {
    const response = await fetch("http://localhost:8082/auth/change/password", {
      method: "PUT",
      credentials: "include",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({
        oldPassword: data.oldPassword,
        newPassword: data.newPassword
      })
    })

    if (!response.ok) {
      throw new Error("could not change password");
    }
    setUpdateSuccess(true);
    setTimeout(() => setUpdateSuccess(false), 3000);
  }

  return (
    <Box sx={{
      minHeight: '100vh',
      backgroundColor: '#fafafa',
      pb: 6
    }}>
      {/* Cover Photo Section */}
     { r >= 0  && <Box sx={{ position: 'relative', mb: 12 }}>
        <Paper
          elevation={0}
          sx={{
            position: 'relative',
            height: 280,
            backgroundImage: !coverPic ? 'url("/background.jpg")' : `url("http://localhost:8082/api/get/cover?v=${r}")`,
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
          <Controller
                  name='cover'
                  control={control3}
                  render={({ field: { onChange, ...rest } }) => (
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
                {...rest}
                
                />
            
          </IconButton>
                  )}
                />
          
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
                src={`http://localhost:8082/api/get/avatar?v=${r}`}
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
                {username.slice(0,1).toUpperCase()}
              </Avatar>}
              <Controller
                  name='avatar'
                  control={control4}
                  render={({ field: { onChange, ...rest } }) => (
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
                {...rest}
                 />
              </IconButton>
                  )}
                />
              
              
            </Box>
          </Box>
        </Container>
      </Box>}

      {/* Content */}
      <Container maxWidth="lg">
        {updateSuccess && (
          <Alert severity="success" sx={{ mb: 3, borderRadius: '12px' }}>
            Profile updated successfully!
          </Alert>
        )}

        <Stack spacing={3}>
          {/* User Information */}
          <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: '16px',
              border: '1px solid #e0e0e0'
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
                  User Information
                </Typography>
              </Stack>

              <Divider />

              <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <Controller
                  name='firstname'
                  control={control}
                  render={({ field }) => (
                    <TextField
                      type='text'
                      {...field}
                      label="First Name"
                      variant="outlined"
                      fullWidth
                      error={!!errors.firstname}
                      helperText={errors.firstname?.message}
                      sx={{
                        '& .MuiOutlinedInput-root': {
                          borderRadius: '10px'
                        }
                      }}
                    />
                  )}
                />

                <Controller
                  name='lastname'
                  control={control}
                  render={({ field }) => (
                    <TextField
                      type='text'
                      {...field}
                      label="Last Name"
                      variant="outlined"
                      fullWidth
                      error={!!errors.lastname}
                      helperText={errors.lastname?.message}
                      sx={{
                        '& .MuiOutlinedInput-root': {
                          borderRadius: '10px'
                        }
                      }}
                    />
                  )}
                />
              </Stack>

              <Controller
                name='bio'
                control={control}
                render={({ field }) => (
                  <TextField
                    rows={4}
                    multiline
                    type='text'
                    {...field}
                    label="Biography"
                    variant="outlined"
                    fullWidth
                    error={!!errors.bio}
                    helperText={errors.bio?.message}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '10px'
                      }
                    }}
                  />
                )}
              />

              <Controller
                name='address'
                control={control}
                render={({ field }) => (
                  <TextField
                    type='text'
                    {...field}
                    label="Address"
                    variant="outlined"
                    fullWidth
                    error={!!errors.address}
                    helperText={errors.address?.message}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '10px'
                      }
                    }}
                  />
                )}
              />

              <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Button
                  type='submit'
                  variant="contained"
                  size="large"
                  sx={{
                    px: 4,
                    py: 1.5,
                    borderRadius: '10px',
                    textTransform: 'none',
                    fontWeight: 600,
                    background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)',
                    boxShadow: '0 2px 8px rgba(59, 130, 246, 0.25)',
                    '&:hover': {
                      boxShadow: '0 4px 12px rgba(59, 130, 246, 0.35)',
                    }
                  }}
                >
                  Save Changes
                </Button>
              </Box>
            </Box>
          </Paper>

          {/* Change Email */}
          <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: '16px',
              border: '1px solid #e0e0e0'
            }}
          >
            <Box
              component="form"
              onSubmit={handleSubmit1(onSubmit1)}
              sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}
            >
              <Stack direction="row" alignItems="center" spacing={1.5}>
                <Mail size={24} color="#3b82f6" />
                <Typography variant="h5" sx={{ fontWeight: 700, color: '#1a1a1a' }}>
                  Change Email Address
                </Typography>
              </Stack>

              <Divider />

              <Controller
                name='email'
                control={control1}
                rules={{ required: 'Email is required' }}
                render={({ field }) => (
                  <TextField
                    type='email'
                    {...field}
                    label="Email Address"
                    variant="outlined"
                    fullWidth
                    error={!!errors1.email}
                    helperText={errors1.email?.message}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '10px'
                      }
                    }}
                  />
                )}
              />

              <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Button
                  type='submit'
                  variant="contained"
                  size="large"
                  sx={{
                    px: 4,
                    py: 1.5,
                    borderRadius: '10px',
                    textTransform: 'none',
                    fontWeight: 600,
                    background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)',
                    boxShadow: '0 2px 8px rgba(59, 130, 246, 0.25)',
                    '&:hover': {
                      boxShadow: '0 4px 12px rgba(59, 130, 246, 0.35)',
                    }
                  }}
                >
                  Update Email
                </Button>
              </Box>
            </Box>
          </Paper>

          {/* Update Password */}
          <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: '16px',
              border: '1px solid #e0e0e0'
            }}
          >
            <Box
              component="form"
              onSubmit={handleSubmit2(onSubmit2)}
              sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}
            >
              <Stack direction="row" alignItems="center" spacing={1.5}>
                <Lock size={24} color="#3b82f6" />
                <Typography variant="h5" sx={{ fontWeight: 700, color: '#1a1a1a' }}>
                  Update Password
                </Typography>
              </Stack>

              <Divider />

              <Controller
                name='oldPassword'
                control={control2}
                render={({ field }) => (
                  <TextField
                    type='password'
                    {...field}
                    label="Current Password"
                    variant="outlined"
                    fullWidth
                    error={!!errors2.oldPassword}
                    helperText={errors2.oldPassword?.message}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '10px'
                      }
                    }}
                  />
                )}
              />

              <Controller
                name='newPassword'
                control={control2}
                render={({ field }) => (
                  <TextField
                    type='password'
                    {...field}
                    label="New Password"
                    variant="outlined"
                    fullWidth
                    error={!!errors2.newPassword}
                    helperText={errors2.newPassword?.message}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        borderRadius: '10px'
                      }
                    }}
                  />
                )}
              />

              <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Button
                  type='submit'
                  variant="contained"
                  size="large"
                  sx={{
                    px: 4,
                    py: 1.5,
                    borderRadius: '10px',
                    textTransform: 'none',
                    fontWeight: 600,
                    background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)',
                    boxShadow: '0 2px 8px rgba(59, 130, 246, 0.25)',
                    '&:hover': {
                      boxShadow: '0 4px 12px rgba(59, 130, 246, 0.35)',
                    }
                  }}
                >
                  Change Password
                </Button>
              </Box>
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
            <Button variant="outlined" color="error" startIcon={<Trash />} onClick={() => {deleteUser()}}>
               Delete Account
             </Button> 
          </Paper>
        </Stack>
      </Container>
    </Box>
  )
}

export default UserProfilePage