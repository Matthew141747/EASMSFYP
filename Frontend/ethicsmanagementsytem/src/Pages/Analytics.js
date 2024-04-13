import React from 'react';
import ChartComponent from '../Pages/ChartA';
import '../Styling/Analytics.css'; 

// AnalyticsDashboard Component
const Analytics = () => {
  // Dummy data for each chart
  const applicationVolumeData = {
    labels: ['January', 'February', 'March', 'April', 'May'],
    datasets: [
      { label: 'Faculty of Science & Engineering', data: [12, 19, 3, 5, 2], borderColor: 'rgb(54, 162, 235)', backgroundColor: 'rgba(54, 162, 235, 0.5)', },
      { label: 'Kemmy Business School', data: [7, 11, 5, 8, 3], borderColor: 'rgb(255, 205, 86)', backgroundColor: 'rgba(255, 205, 86, 0.5)', },
      { label: 'Faculty of Education and Health Sciences', data: [14, 4, 5, 12, 3], borderColor: 'rgb(255, 205, 86)', backgroundColor: 'rgba(75, 192, 192, 0.5)', }

    ],
  };

  const humanParticipantsData = {
    labels: ['vulnerable person?', 'Under 18?', 'Adult Patients', 'Clinical Setting', 'Psychological Impairments','Learning Difficulties', 'Relatives of ill people', 'care/prison', 'Rudimentary English', 'Hospital Patients'],
    datasets: [
      { label: 'Applications', data: [20, 15, 5, 3, 1, 5, 8, 9, 12, 3], borderColor: 'rgb(75, 192, 192)', backgroundColor: 'rgba(75, 192, 192, 0.5)', }
    ],
  };

  // Assuming a simple mapping for research project information overview
  const researchProjectInfoData = {
    labels: ['Survey', 'Interview', 'Workshop', 'Prototype Testing', 'Biological Sample Acquisition', 'Field Testing'],
    datasets: [
      { label: 'Applications', data: [10, 7, 3, 5, 2, 4], borderColor: 'rgb(153, 102, 255)', backgroundColor: 'rgba(153, 102, 255, 0.5)', }
    ],
  };

  // Dummy data for Data Recording chart
  const dataRecordingInfoData = {
    labels: ['Prototype Development', 'Participants'],
    datasets: [
      { label: 'Count', data: [8, 150], borderColor: 'rgb(255, 99, 132)', backgroundColor: 'rgba(255, 99, 132, 0.5)', }
    ],
  };

  return (
    <>
  
    <div className="analytics-dashboard">
      <ChartComponent data={applicationVolumeData} title="Volume of Applications by Faculty and Month" />
      <ChartComponent data={humanParticipantsData} title="Human Participants in the Study" />
      <ChartComponent data={researchProjectInfoData} title="Overview of Research Project Information" />
      <ChartComponent data={dataRecordingInfoData} title="Data Recording Information" />
    </div>
    </>
  );
};

export default Analytics;