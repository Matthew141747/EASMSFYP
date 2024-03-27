import React from 'react';
import { Chart } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement, ArcElement, RadialLinearScale  } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement, ArcElement, RadialLinearScale );

const BasicChart = ({ chartData, chartType }) => {
  return (
    <div>
      <Chart type={chartType} data={chartData} />
    </div>
  );
};

export default BasicChart;