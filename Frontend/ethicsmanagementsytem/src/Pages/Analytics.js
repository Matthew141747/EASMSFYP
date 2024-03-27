import React from 'react';
import BasicChart from '../Pages/ChartA';
import '../Styling/Analytics.css'; 

const Analytics = () => {
  // Placeholder data for the charts
  const chartData = {
    labels: ['January', 'February', 'March', 'April', 'May'],
    datasets: [
      {
        label: 'Dataset 1',
        data: [65, 59, 80, 81, 56],
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div className="analytics-dashboard">
      <BasicChart chartData={chartData} chartType="line" />
      <BasicChart chartData={chartData} chartType="bar" />
      <BasicChart chartData={chartData} chartType="radar" />
      <BasicChart chartData={chartData} chartType="doughnut" />
    </div>
  );
};

export default Analytics;