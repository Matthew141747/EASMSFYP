import React from 'react';
import { Chart } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement, ArcElement, RadialLinearScale  } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, BarElement, ArcElement, RadialLinearScale );

// ChartComponent for rendering line charts
const ChartComponent = ({ data, title, type = 'bar', showLegend = true  }) => {
  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: showLegend,
        position: 'top',
      },
      title: {
        display: true,
        text: title,
      },
    },
  };

  return (
    <div className="chart-card">
      <Chart  type={type} data={data} options={options} />
    </div>
  );
};

export default ChartComponent;