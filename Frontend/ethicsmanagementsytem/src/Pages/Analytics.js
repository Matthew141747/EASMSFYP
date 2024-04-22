import React from 'react';
import ChartWithDateRange from '../Pages/ChartDR';
import '../Styling/Analytics.css'; 
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
// AnalyticsDashboard Component
const Analytics = () => {


//Initially populate the charts with data from the previous four months 
const loadInitialData = () => {
  const endDate = new Date();
  const startDate = new Date();
  startDate.setMonth(startDate.getMonth() - 4); 

  if (startDate.getMonth() === endDate.getMonth()) { // Handle year rollover
    startDate.setFullYear(startDate.getFullYear() - 1);
  }

  // Trigger data fetching for each chart
  fetchDataAppVol(startDate, endDate);
  fetchDataHumanParticipants(startDate, endDate);
  fetchDataOverviewResearchProject(startDate, endDate);
  fetchDataResearchProjectInfo(startDate, endDate);
};

useEffect(() => {
  loadInitialData();
}, []); // Effect runs once on mount

const navigate = useNavigate();
const token = localStorage.getItem('userToken');
useEffect(() => {
 // Redirect user to login page if not logged in
 if (!token) {
     navigate('/login');
 }
}, [token, navigate]); // The effect will run on component mount and whenever the token changes
  

  const [chartDataHumanParticipants, setChartDataHumanParticipants] = useState({
    labels: [],
    datasets: []
  });

  const [chartDataOverviewOfResearchProject, setChartOverviewOfResearchProject] = useState({
    labels: [],
    datasets: []
  });

  const [chartDataResearchProjectInfo, setChartDataResearchProjectInfo] = useState({
    labels: [],
    datasets: []
  });


  //Chart - Volume of Applications by Faculty and Month 
  const [chartDataAppVol, setchartDataAppVol] = useState({
    labels: [],
    datasets: [
      {
        label: 'Volume of Applications by Faculty',
        data: [],
        borderColor: 'rgb(54, 162, 235)',
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
      }
    ]
  });

  const fetchDataAppVol = async (startDate, endDate) => {
    const token = localStorage.getItem('userToken');
    const queryParams = `?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`;
    const response = await fetch(`http://localhost:8080/api/submissions/volume-by-faculty${queryParams}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
    });
  
    if (!response.ok) {
      throw new Error(`Error: ${response.status}`);
    }
  
    const data = await response.json();
    //console.log('Analytics Data for Volume', data);
    updateChartDataAppVol(data); // The function needs to be specific to each chart as well
  };

  // Update chart data function specific to the Application Volume chart
  const updateChartDataAppVol = (data) => {
    const datasets = Object.entries(data).map(([key, value]) => {
      const label = formatLabel(key);
      return {
        label: label,
        data: [value], // Each dataset has one data point
        borderColor: facultyColors[label] || '#000', // Default color if not found
        backgroundColor: facultyColors[label] || '#000',
      };
    });

    setchartDataAppVol({
      labels: ['Volume of Applications by Faculty'], // One generic label for x-axis
      datasets: datasets, // Array of datasets
    });
  };

  // Helper function to format labels
    const formatLabel = (label) => {
      return label.split('_')
                  .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                  .join(' ');
    };

    // Define colors for each faculty
    const facultyColors = {
      'Arts Humanities Social Sciences': 'rgba(255, 99, 132, 0.5)',
      'Education Health Sciences': 'rgba(54, 162, 235, 0.5)',
      'Kemmy Business School': 'rgba(255, 206, 86, 0.5)',
      'Science Engineering': 'rgba(75, 192, 192, 0.5)',
    };

    //Chart - Human Participants in the study 

    const fetchDataHumanParticipants = async (startDate, endDate) => {
      const token = localStorage.getItem('userToken');
      const queryParams = `?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`;
      const response = await fetch(`http://localhost:8080/api/analytics/HumanParticipants/data${queryParams}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
      });
    
      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }
    
      const data = await response.json();
      //console.log('Analytics Data for Human Participants', data);
      updateChartDataHumanParticipants(data);
    };

    const updateChartDataHumanParticipants = (data) => {
      const labels = Object.keys(data);
      const datasetData = Object.values(data);
     // console.log('Human Participants labels', labels);
      const chartData = {
        labels: labels,
        datasets: [{
          data: datasetData,
          backgroundColor: labels.map(label => chartColors[label] || '#000'), // Ensure color consistency
        }]
      };
      setChartDataHumanParticipants(chartData);
    };

    
    const chartColors = {
      'Working with Vulnerable Person': '#FF6384',
      'Staff in Clinical Setting': '#36A2EB',
      'Under Age of Eighteen': '#FFCE56',
      'Adults with Learning Difficulties': '#4BC0C0',
      'Adult Patients': '#F7464A',
      'Relatives of Ill People': '#46BFBD',
      'Adults with Psychological Impairments': '#FDB45C',
      'Adults Under Protection': '#949FB1',
      'Hospital GP Patients': '#4D5360'
    };

    //Overview of the Research Project 

    const fetchDataOverviewResearchProject = async (startDate, endDate) => {
      const token = localStorage.getItem('userToken');
      const queryParams = `?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`;
      const response = await fetch(`http://localhost:8080/api/analytics/OverviewOfTheResearchProject/data${queryParams}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
      });
    
      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }
    
      const data = await response.json();
      console.log('Analytics Data for Overview of Research Project', data);
      updateChartDataOverviewResearchProject(data);
    };

    const updateChartDataOverviewResearchProject = (data) => {
      const labels = Object.keys(data);
      const datasetData = Object.values(data);
     // console.log('Overview of research Project labels',labels);
      const chartData = {
        labels: labels,
        datasets: [{
          data: datasetData,
          backgroundColor: labels.map(label => overviewResearchProjectColors[label] || '#000'),
        }]
      };
      setChartOverviewOfResearchProject(chartData);
    };

    const overviewResearchProjectColors = {
      'surveyPhysicalOnCampus': '#FF6384',
      'surveyPhysicalOffCampus': '#36A2EB',
      'surveyOnline': '#FFCE56',
      'interviewPhysicalOnCampus': '#4BC0C0',
      'interviewPhysicalOffCampus': '#F7464A',
      'interviewOnline': '#46BFBD',
      'workshopPhysicalOnCampus': '#FDB45C',
      'workshopPhysicalOffCampus': '#949FB1',
      'workshopOnline': '#4D5360',
      'prototypeTestingPhysicalOnCampus': '#5AD3D1',
      'prototypeTestingPhysicalOffCampus': '#FF9F40',
      'prototypeTestingOnline': '#B2912F',
      'biologicalSampleAcquisition': '#A8B3C5',
      'dataAcquisitionPersonal': '#616774',
      'fieldTestingOnsite': '#DA92DB',
      'other': '#C88D94'
    };

    //Research Project Information 
    const fetchDataResearchProjectInfo = async (startDate, endDate) => {
      const token = localStorage.getItem('userToken');
      const queryParams = `?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`;
      const response = await fetch(`http://localhost:8080/api/analytics/ResearchProjectInformation/data${queryParams}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
      });
  
      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }
  
      const data = await response.json();
     // console.log('Analytics Data for Research Project Information', data);
      updateChartDataResearchProjectInfo(data);
    };

    const updateChartDataResearchProjectInfo = (data) => {
      const labels = Object.keys(data);
      const datasetData = Object.values(data);
     // console.log('Research Project Info labels', labels);
      const chartData = {
        labels: labels,
        datasets: [{
          label: 'Research Project Information',
          data: datasetData,
          backgroundColor: labels.map(label => researchProjectInfoColors[label] || '#000'),
          borderColor: labels.map(label => researchProjectInfoColors[label] || '#000'),
        }],
      };
      setChartDataResearchProjectInfo(chartData);
    };

    const researchProjectInfoColors = {
      'participantsRecorded': '#FF6384',
      'audioRecording': '#36A2EB',
      'videoRecording': '#FFCE56',
      'audioVideoRecording': '#4BC0C0',
      'accommodationForNonParticipants': '#FDB45C',
      'prototypeServiceFramework' : '#949FB1', 
      'prototypeDigitalUiApp' : '#4D5360',
      'prototypeDeveloped' : '#616774',
      'prototypePhysicalArtifact' : '#C88D94' 
    };
  
  return (
    <>
  
    <div className="analytics-dashboard">
    <ChartWithDateRange
          fetchData={fetchDataAppVol}
          chartData={chartDataAppVol}
          title="Volume of Applications by Faculty and Month"
          showLegend={true}
        />
          <ChartWithDateRange
          fetchData={fetchDataHumanParticipants}
          chartData={chartDataHumanParticipants}
          title="Human Participants in the Study"
          showLegend={false}
        />

           <ChartWithDateRange
          fetchData={fetchDataOverviewResearchProject}
          chartData={chartDataOverviewOfResearchProject}
          title="Overview of Research Project Information"
          showLegend={false}
        />
           <ChartWithDateRange
          fetchData={fetchDataResearchProjectInfo}
          chartData={chartDataResearchProjectInfo}
          title="Research Project Information"
          showLegend={false}
        />

    </div>
    </>
  );
};

export default Analytics;