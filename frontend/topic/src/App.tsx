import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import TopicPage from './pages/Topic';
import ResultPage from './pages/ResultPage';
import QuizPage from './pages/QuizPage';
import Admin from './pages/admin';
import ChartPage from './pages/ChartPage';
import Header from './components/header';
import Home from './pages/Home';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

const theme = createTheme({
  palette: {
    primary: {
      main: '#00695C',
    },
    secondary: {
      main: '#00C853',
    },
    background: {
      default: '#f4f7fa',
    },
  },
  typography: {
    fontFamily: 'Roboto, sans-serif',
  },
});

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        {/* Header visible sur toutes les pages */}
        <Header />
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/topic" element={<TopicPage />} />
          <Route path="/quiz/:id" element={<QuizPage />} />
          <Route path="/quiz/:id/result" element={<ResultPage />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/chart" element={<ChartPage />} />
          <Route path="/" element={<Home />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
};

export default App;
