import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <AppBar
      position="static"
      sx={{
        background: 'linear-gradient(90deg, #b388ff, #81d4fa, #f48fb1)',
        boxShadow: '0 4px 10px rgba(0,0,0,0.2)'
      }}
    >
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1, fontWeight: 'bold' }}>
          Plateforme Éducative
        </Typography>
        <Box>
          <Button color="inherit" component={Link} to="/">Home</Button>
          <Button color="inherit" component={Link} to="/register">Register</Button>
          <Button color="inherit" component={Link} to="/login">Login</Button>
          <Button color="inherit" component={Link} to="/chart">Chart</Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
