import React from 'react';
import { Container, Typography, Box, Grid } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import QuizIcon from '@mui/icons-material/Quiz';

const Home = () => {
  return (
    <Box
      sx={{
        height: '100vh',
        width: '100vw',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundImage: 'url("/background.jpg")',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <Container maxWidth="md">
        <Box
          sx={{
            padding: '40px',
            borderRadius: '20px',
            boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)',
            textAlign: 'center',
            background: 'linear-gradient(135deg, rgba(226, 157, 223, 0.9), rgba(173, 216, 230, 0.9))',
            color: 'white',
            backdropFilter: 'blur(4px)',
          }}
        >
          <Typography
            variant="h4"
            gutterBottom
            sx={{
              fontWeight: 'bold',
              textShadow: '1px 1px 2px rgba(0,0,0,0.3)',
            }}
          >
            Bienvenue dans notre plateforme éducative
          </Typography>
          <Typography
            variant="subtitle1"
            sx={{
              mb: 3,
              textShadow: '1px 1px 2px rgba(0,0,0,0.3)',
            }}
          >
            Explorez, apprenez et testez vos connaissances.
          </Typography>

          <Grid container spacing={2} justifyContent="center" sx={{ mt: 2 }}>
            <Grid item>
              <SearchIcon sx={{ fontSize: 40, color: 'white' }} />
            </Grid>
            <Grid item>
              <MenuBookIcon sx={{ fontSize: 40, color: 'white' }} />
            </Grid>
            <Grid item>
              <QuizIcon sx={{ fontSize: 40, color: 'white' }} />
            </Grid>
          </Grid>
        </Box>
      </Container>
    </Box>
  );
};

export default Home;
