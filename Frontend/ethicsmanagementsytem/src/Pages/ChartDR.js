import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import ChartComponent from './ChartA'; 
import "react-datepicker/dist/react-datepicker.css";

const ChartWithDateRange = ({ fetchData, chartData, title, showLegend = true }) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());

  const handleFetchData = () => {
    fetchData(startDate, endDate);
  };

  return (
    <div className="chart-with-date-range">
      <div className="title-and-date-range">
        <h2 className="chart-title">{title}</h2>
        <div className="date-range-controls">
          <DatePicker className="date-picker-input" selected={startDate} onChange={date => setStartDate(date)} />
          <DatePicker className="date-picker-input" selected={endDate} onChange={date => setEndDate(date)} />
          <button className="update-data-button" onClick={handleFetchData}>Update</button>
        </div>
      </div>
      <ChartComponent data={chartData} showLegend={showLegend} />
    </div>
  );
};

export default ChartWithDateRange;