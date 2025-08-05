import { useState } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  Paper,
  MenuItem,
  Box,
  Avatar,
  InputAdornment,
} from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import EmailIcon from '@mui/icons-material/Email';
import VpnKeyIcon from '@mui/icons-material/VpnKey';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    password: '',
    role: 'USER',
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await api.post('/users', formData);
      navigate('/login');
    } catch (error) {
      console.error('Erreur lors de l’inscription', error);
      alert("Erreur lors de l'inscription");
    }
  };

  return (
    <Box
  sx={{
    minHeight: '100vh',
    width: '100vw', // ✅ Ajouté
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    background: 'linear-gradient(135deg, #b388ff, #81d4fa, #f48fb1)',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
  }}
>
      <Container maxWidth="xs">
        <Paper
          elevation={10}
          sx={{
            p: 5,
            borderRadius: 4,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            backgroundColor: '#ffffffcc',
            backdropFilter: 'blur(12px)',
            boxShadow: '0 8px 24px rgba(0,0,0,0.2)',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: '#b388ff', width: 56, height: 56 }}>
            <LockOutlinedIcon fontSize="large" />
          </Avatar>

          <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 2 }}>
            Créer un compte
          </Typography>

          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{ mt: 1, width: '100%', display: 'flex', flexDirection: 'column', gap: 2 }}
          >
            <TextField
              label="Nom complet"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              required
              fullWidth
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <PersonIcon />
                  </InputAdornment>
                ),
              }}
            />

            <TextField
              label="Adresse email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              required
              fullWidth
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <EmailIcon />
                  </InputAdornment>
                ),
              }}
            />

            <TextField
              label="Mot de passe"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              required
              fullWidth
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <VpnKeyIcon />
                  </InputAdornment>
                ),
              }}
            />

            <TextField
              select
              label="Rôle"
              name="role"
              value={formData.role}
              onChange={handleChange}
              fullWidth
            >
              <MenuItem value="USER">Utilisateur</MenuItem>
              <MenuItem value="ADMIN">Administrateur</MenuItem>
            </TextField>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{
                mt: 3,
                py: 1.5,
                borderRadius: 3,
                fontSize: '1rem',
                fontWeight: 'bold',
                textTransform: 'none',
                background: 'linear-gradient(to right, #b388ff, #81d4fa, #f48fb1)',
                color: '#fff',
                boxShadow: '0 4px 14px rgba(0,0,0,0.2)',
                '&:hover': {
                  background: 'linear-gradient(to right, #f48fb1, #81d4fa, #b388ff)',
                },
              }}
            >
              S'inscrire
            </Button>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
};

export default Register;
