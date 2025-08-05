import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  type ChartOptions,
  type ChartData,
} from 'chart.js';

import ChartDataLabels from 'chartjs-plugin-datalabels';
import api from '../api'; // Vérifie bien le chemin

ChartJS.register(
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  ChartDataLabels
);

interface QuizResult {
  quizId: number;
  topic: string;
  score: number;
  totalScore: number;
}
const ChartPage: React.FC = () => {
  const [chartData, setChartData] = useState<ChartData<'bar'> | null>(null);
  const [quizResults, setQuizResults] = useState<QuizResult[]>([]);

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (!userId) return;

    api.get<QuizResult[]>(`/chart/user/${userId}`)
      .then((response) => {
        setQuizResults(response.data);

        const topics = response.data.map(item => item.topic);
        const percentages = response.data.map(item =>
          item.totalScore > 0 ? (item.score / item.totalScore) * 100 : 0
        );

        const backgroundColors = topics.map((_, i) =>
          `hsl(${(i * 360) / topics.length}, 70%, 60%)`
        );
        const borderColors = topics.map((_, i) =>
          `hsl(${(i * 360) / topics.length}, 70%, 40%)`
        );

        setChartData({
          labels: topics,
          datasets: [
            {
              label: 'Score (%) par quiz',
              data: percentages,
              backgroundColor: backgroundColors,
              borderColor: borderColors,
              borderWidth: 1,
            }
          ]
        });
      })
      .catch(error =>
        console.error('Erreur de chargement du graphique:', error)
      );
  }, [userId]);

  const options: ChartOptions<'bar'> = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        max: 100,
        title: { display: true, text: 'Score (%)' }
      },
      x: {
        title: { display: true, text: 'Sujet' }
      }
    },
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
      tooltip: {
        enabled: true
      },
      datalabels: {
        anchor: 'end',
        align: 'end',
        color: '#000',
        font: {
          weight: 'bold'
        },
        formatter: function (value: number) {
          return value.toFixed(1) + '%';
        }
      }
    }
  };

  return (
  <div
  style={{
    width: '100vw',              // ✅ Largeur écran
    height: '100vh',             // ✅ Hauteur écran
    display: 'flex',             // ✅ Centrage
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '40px',
    background: 'linear-gradient(135deg, #f8bbd0, #e1bee7, #b3e5fc)',
  }}
>
    <h2 style={{
      textAlign: 'center',
      marginBottom: '30px',
      color: '#4a148c',
      fontWeight: 'bold',
      textShadow: '1px 1px 2px rgba(0,0,0,0.2)',
    }}>
      Statistiques des quiz
    </h2>

    {chartData ? (
      <div
        style={{
          height: '400px',
          width: '100%',
          marginBottom: '40px',
          background: 'white',
          padding: '20px',
          borderRadius: '16px',
          boxShadow: '0 4px 12px rgba(0,0,0,0.1)'
        }}
      >
        <Bar data={chartData} options={options} />
      </div>
    ) : (
      <p>Chargement du graphique...</p>
    )}

    <h3 style={{ marginTop: '20px', marginBottom: '10px', color: '#6a1b9a' }}>
      Détails des quiz
    </h3>

    <table
      style={{
        width: '100%',
        borderCollapse: 'collapse',
        fontFamily: 'Arial, sans-serif',
        backgroundColor: '#ffffffcc',
        backdropFilter: 'blur(8px)',
        borderRadius: '12px',
        overflow: 'hidden',
      }}
    >
      <thead>
        <tr style={{ backgroundColor: '#ce93d8', color: '#fff' }}>
          <th style={{ padding: '10px' }}>Quiz ID</th>
          <th style={{ padding: '10px' }}>Quiz</th>
          <th style={{ padding: '10px' }}>Nombre de questions</th>
          <th style={{ padding: '10px' }}>Réponses correctes</th>
        </tr>
      </thead>
      <tbody>
        {quizResults.map((quiz, index) => (
          <tr key={index} style={{ backgroundColor: index % 2 === 0 ? '#f3e5f5' : '#e1bee7' }}>
            <td style={{ padding: '10px', textAlign: 'center' }}>{quiz.quizId}</td>
            <td style={{ padding: '10px', textAlign: 'center' }}>{quiz.topic}</td>
            <td style={{ padding: '10px', textAlign: 'center' }}>{quiz.totalScore}</td>
            <td style={{ padding: '10px', textAlign: 'center' }}>{quiz.score}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

};

export default ChartPage;
