import { useState, useEffect } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  Paper,
  Box,
  CircularProgress,
  List,
  ListItem,
  ListItemText,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const TopicPage = () => {
  const [topicName, setTopicName] = useState('');
  const [loading, setLoading] = useState(false);
  const [definitions, setDefinitions] = useState<Record<string, string> | null>(null);
  const [summary, setSummary] = useState<string | null>(null);
  const [fullName, setFullName] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchFullName = async () => {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) return;
        const res = await api.get(`/users/${userId}/fullName`);
        setFullName(res.data.fullName);
      } catch (error) {
        console.error('Erreur nom complet', error);
      }
    };
    fetchFullName();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const userId = localStorage.getItem('userId');
      const res = await api.post('/learn', { topic: topicName, userId });
      setDefinitions(res.data.definitions);
      setSummary(res.data.summary);
      localStorage.setItem('topic', JSON.stringify({ topic: topicName, userId }));
      if (res.data.id) {
        localStorage.setItem('lastTopicId', res.data.id);
      }
    } catch (err) {
      console.error('Erreur génération topic', err);
      alert("Erreur lors de la génération.");
    } finally {
      setLoading(false);
    }
  };

  const handleGenerateQuiz = async () => {
    try {
      const topicId = Number(localStorage.getItem('lastTopicId'));
      if (!topicId) {
        alert("Veuillez d'abord générer un sujet.");
        return;
      }
      const res = await api.post('/quiz/generate/auto', null, {
        params: { topicId, questionCount: 5 },
      });
      const quizId = res.data.id;
      navigate(`/quiz/${quizId}`);
    } catch (error) {
      console.error('Erreur quiz', error);
      alert("Erreur lors de la génération du quiz.");
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundImage: `url('/bg-quiz-pattern.png')`,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        backgroundColor: '#a1c4fd',
        backgroundBlendMode: 'overlay',
        px: 2,
      }}
    >
      <Container maxWidth="md">
        <Paper
          elevation={10}
          sx={{
            p: 5,
            borderRadius: 5,
            backgroundColor: '#ffffffcc',
            backdropFilter: 'blur(10px)',
            boxShadow: '0 12px 30px rgba(0,0,0,0.2)',
          }}
        >
          <Typography variant="h4" align="center" sx={{ fontWeight: 'bold', mb: 1 }}>
            Apprendre un sujet
          </Typography>

          {fullName && (
            <Typography align="center" sx={{ color: '#444', mb: 3 }}>
              Bonjour <strong>{fullName}</strong>, entre un sujet pour commencer à apprendre.
            </Typography>
          )}

          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{ display: 'flex', gap: 2, flexDirection: { xs: 'column', sm: 'row' } }}
          >
            <TextField
              label="Ex : JavaScript, mitochondrie, Napoléon..."
              variant="outlined"
              fullWidth
              value={topicName}
              onChange={(e) => setTopicName(e.target.value)}
              required
              sx={{ backgroundColor: '#fff', borderRadius: 1 }}
            />
            <Button
              type="submit"
              variant="contained"
              sx={{
                px: 4,
                fontWeight: 'bold',
                background: 'linear-gradient(to right, #36d1dc, #5b86e5)',
                color: '#fff',
                '&:hover': {
                  background: 'linear-gradient(to right, #5b86e5, #36d1dc)',
                },
              }}
            >
              Générer
            </Button>
          </Box>

          {loading && (
            <Box sx={{ mt: 4, textAlign: 'center' }}>
              <CircularProgress />
              <Typography sx={{ mt: 2 }}>Génération en cours...</Typography>
            </Box>
          )}

          {definitions && (
            <Box sx={{ mt: 5 }}>
              <Typography variant="h5" sx={{ mb: 2, color: '#1976d2' }}>
                Définitions clés
              </Typography>
              <List>
                {Object.entries(definitions).map(([term, def]) => (
                  <ListItem key={term} sx={{ pl: 0 }}>
                    <ListItemText
                      primary={<strong>{term}</strong>}
                      secondary={def}
                    />
                  </ListItem>
                ))}
              </List>
            </Box>
          )}

          {summary && (
            <Box sx={{ mt: 4 }}>
              <Typography variant="h5" sx={{ mb: 2, color: '#2e7d32' }}>
                Résumé
              </Typography>
              <Paper
                sx={{
                  p: 2,
                  backgroundColor: '#e8f5e9',
                  borderLeft: '6px solid #66bb6a',
                }}
              >
                <Typography>{summary}</Typography>
              </Paper>

              <Box sx={{ mt: 4, textAlign: 'center' }}>
                <Button
  variant="contained"
  color="secondary"
  onClick={handleGenerateQuiz}
  sx={{
    fontWeight: 'bold',
    px: 4,
    py: 1.2,
    borderRadius: 3,
    background: 'linear-gradient(to right, #36d1dc, #81c784)',  // bleu -> vert
    '&:hover': {
      background: 'linear-gradient(to right, #81c784, #36d1dc)', // inverse au hover
    },
  }}
>
  Générer un Quiz
</Button>

              </Box>
            </Box>
          )}
        </Paper>
      </Container>
    </Box>
  );
};

export default TopicPage;
