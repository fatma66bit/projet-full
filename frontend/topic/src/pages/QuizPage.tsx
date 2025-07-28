import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Paper,
  RadioGroup,
  FormControlLabel,
  Radio,
  Button,
  CircularProgress,
  Box,
} from '@mui/material';
import api from '../api';

const QuizPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [quiz, setQuiz] = useState<any>(null);
  const [responses, setResponses] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const res = await api.get(`/quiz/${id}`);
        setQuiz(res.data);
        setResponses(new Array(res.data.questions.length).fill(''));
      } catch (error) {
        console.error('Erreur de chargement du quiz', error);
      } finally {
        setLoading(false);
      }
    };
    fetchQuiz();
  }, [id]);

  const handleResponseChange = (index: number, value: string) => {
    const updated = [...responses];
    updated[index] = value;
    setResponses(updated);
  };

  const handleSubmit = async () => {
    try {
      const res = await api.post(`/quiz/${id}/submit`, responses);
      navigate(`/quiz/${id}/result`, { state: { result: res.data } });
    } catch (error) {
      console.error('Erreur lors de la soumission', error);
      alert("Erreur lors de la soumission du quiz.");
    }
  };

  if (loading) {
    return (
      <Box textAlign="center" mt={6}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }} variant="h6" color="textSecondary">
          Chargement du quiz...
        </Typography>
      </Box>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 6, mb: 6 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ fontWeight: 'bold', textAlign: 'center', color: '#1976d2' }}
      >
        Quiz : {quiz.topic.name}
      </Typography>

      {quiz.questions.map((q: any, index: number) => (
        <Paper
          key={q.id}
          elevation={6}
          sx={{
            p: 3,
            mb: 3,
            borderRadius: 3,
            background: 'linear-gradient(135deg, #e3f2fd, #e8f5e9)',
          }}
        >
          <Typography variant="h6" sx={{ mb: 2 }}>
            {index + 1}. {q.questionText}
          </Typography>
          <RadioGroup
            value={responses[index]}
            onChange={(e) => handleResponseChange(index, e.target.value)}
          >
            {q.options.map((opt: string, i: number) => (
              <FormControlLabel
                key={i}
                value={opt}
                control={<Radio />}
                label={opt}
                sx={{
                  '& .MuiFormControlLabel-label': {
                    fontSize: '1rem',
                    color: '#555',
                  },
                }}
              />
            ))}
          </RadioGroup>
        </Paper>
      ))}

      <Box textAlign="center" mt={4}>
        <Button
          variant="contained"
          sx={{
            background: 'linear-gradient(to right, #36d1dc, #81c784)',
            px: 5,
            py: 1.5,
            fontWeight: 'bold',
            fontSize: '1.1rem',
            borderRadius: 3,
            color: '#fff',
            boxShadow: '0 4px 14px rgba(54, 209, 220, 0.4)',
            '&:hover': {
              background: 'linear-gradient(to right, #81c784, #36d1dc)',
              boxShadow: '0 6px 20px rgba(129, 199, 132, 0.6)',
            },
          }}
          onClick={handleSubmit}
          disabled={responses.includes('')}
        >
          Soumettre le Quiz
        </Button>
      </Box>
    </Container>
  );
};

export default QuizPage;
