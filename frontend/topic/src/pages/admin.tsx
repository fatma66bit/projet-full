import React, { useEffect, useState } from 'react';
import {
  Container,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Box,
  List,
  ListItem,
} from '@mui/material';
import api from '../api';

const Admin = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    api
      .get('/users/admin/users-info')
      .then(res => setUsers(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <Box
  sx={{
    minHeight: '100vh',
    width: '100vw', // ✅ Ajouté
    padding: '2rem',
    background: 'linear-gradient(135deg, #f8c6f0, #b2c7f9, #e4bdf0)',
    backgroundSize: '400% 400%',
    animation: 'gradientBG 10s ease infinite',
  }}
>
      <Container maxWidth="lg">
        <Typography
          variant="h4"
          align="center"
          gutterBottom
          sx={{
            color: '#4a148c',
            fontWeight: 'bold',
            mb: 4,
            textShadow: '1px 1px 2px rgba(0,0,0,0.2)',
          }}
        >
          Résultats des utilisateurs
        </Typography>

        <TableContainer component={Paper} elevation={6} sx={{ borderRadius: 4 }}>
          <Table>
            <TableHead sx={{ backgroundColor: '#ce93d8' }}>
              <TableRow>
                <TableCell sx={{ fontWeight: 'bold', color: '#4a148c' }}>Nom</TableCell>
                <TableCell sx={{ fontWeight: 'bold', color: '#4a148c' }}>Email</TableCell>
                <TableCell sx={{ fontWeight: 'bold', color: '#4a148c' }}>Rôle</TableCell>
                <TableCell sx={{ fontWeight: 'bold', color: '#4a148c' }}>Topics</TableCell>
                <TableCell sx={{ fontWeight: 'bold', color: '#4a148c' }}>Résultats de Quiz</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {users.map((user: any) => (
                <TableRow key={user.id} hover>
                  <TableCell>{user.fullName}</TableCell>
                  <TableCell>{user.email}</TableCell>
                  <TableCell>{user.role}</TableCell>
                  <TableCell>
                    <List dense>
                      {user.topicNames.map((t: string, i: number) => (
                        <ListItem key={i} sx={{ paddingY: 0.2 }}>
                          {t}
                        </ListItem>
                      ))}
                    </List>
                  </TableCell>
                  <TableCell>
                    <List dense>
                      {user.quizResults.map((qr: any, i: number) => (
                        <ListItem key={i} sx={{ paddingY: 0.2 }}>
                          Quiz {qr.quizId}: <strong>{qr.score}</strong> / {qr.totalScore}
                        </ListItem>
                      ))}
                    </List>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>

      {/* Gradient animation */}
      <style>{`
        @keyframes gradientBG {
          0% { background-position: 0% 50%; }
          50% { background-position: 100% 50%; }
          100% { background-position: 0% 50%; }
        }
      `}</style>
    </Box>
  );
};

export default Admin;
