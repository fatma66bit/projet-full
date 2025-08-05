import { useLocation, useNavigate } from 'react-router-dom';
import { Box, Button, Container, Paper, Typography } from '@mui/material';

const ResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const result = location.state?.result;

  if (!result) {
    return (
      <Container sx={{ mt: 6 }}>
        <Typography variant="h5" color="error" align="center">
          Résultat non disponible.
        </Typography>
      </Container>
    );
  }

  const percentage = Math.round((result.score / result.totalScore) * 100);

  return (
    <Box
    sx={{
      minHeight: '100vh',
      width: '100vw',
      background: 'linear-gradient(135deg, #e0f7fa, #f3e5f5)',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      px: 2,
    }}
  >
    <Container maxWidth="sm" sx={{ mt: 6, mb: 6 }}>
      <Paper
        elevation={8}
        sx={{
          p: 5,
          textAlign: 'center',
          borderRadius: 4,
          background: 'linear-gradient(135deg, #e3f2fd, #e8f5e9)',
          boxShadow: '0 12px 30px rgba(0,0,0,0.15)',
        }}
      >
        <Typography
          variant="h4"
          gutterBottom
          sx={{ fontWeight: 'bold', color: '#1976d2' }}
        >
          Résultat du Quiz
        </Typography>
        <Typography variant="h6" sx={{ mb: 2 }}>
          Score : {result.score} / {result.totalScore}
        </Typography>
        <Typography
          variant="h5"
          sx={{
            color: percentage >= 50 ? 'success.main' : 'error.main',
            fontWeight: 'bold',
            mb: 4,
          }}
        >
          {percentage >= 50 ? 'Bravo !' : 'Essayez encore !'}
        </Typography>
        <Box>
          <Button
            variant="contained"
            sx={{
              background: 'linear-gradient(to right, #36d1dc, #81c784)',
              px: 5,
              py: 1.5,
              fontWeight: 'bold',
              fontSize: '1rem',
              borderRadius: 3,
              color: '#fff',
              boxShadow: '0 4px 14px rgba(54, 209, 220, 0.4)',
              '&:hover': {
                background: 'linear-gradient(to right, #81c784, #36d1dc)',
                boxShadow: '0 6px 20px rgba(129, 199, 132, 0.6)',
              },
            }}
            onClick={() => navigate('/topic')}
          >
            Retour à l'accueil
          </Button>
        </Box>
      </Paper>
    </Container>
    </Box>
  );
};

export default ResultPage;
