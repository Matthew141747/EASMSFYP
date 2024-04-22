import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';  
import Analytics from '../Pages/Analytics';
import fetchMock from 'jest-fetch-mock';

fetchMock.enableMocks();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'), 
    useNavigate: () => jest.fn() 
}));

beforeEach(() => {
  fetch.resetMocks();
});

describe('Analytics Component', () => {
  it('renders correctly and initializes data fetching', async () => {
    fetch.mockResponses(
        [JSON.stringify({ data: [] }), { status: 200 }],
        [JSON.stringify({
          "arts_humanities_social_sciences": 3,
          "education_health_sciences": 1,
          "irish_world_academy": 2,
          "kemmy_business_school": 4,
          "science_engineering": 21
        }), { status: 200 }],
        [JSON.stringify({
          "other": 0,
          "interviewPhysicalOffCampus": 2,
          "prototypeTestingOnline": 1,
          "prototypeTestingPhysicalOffCampus": 1,
          "surveyPhysicalOffCampus": 3,
          "surveyOnline": 1,
          "workshopPhysicalOffCampus": 0,
          "biologicalSampleAcquisition": 4,
          "workshopOnline": 0,
          "dataAcquisitionPersonal": 1,
          "surveyPhysicalOnCampus": 3,
          "interviewPhysicalOnCampus": 4,
          "fieldTestingOnsite": 5,
          "workshopPhysicalOnCampus": 0,
          "interviewOnline": 1,
          "prototypeTestingPhysicalOnCampus": 4
        }), { status: 200 }]
    );

    render(
      <MemoryRouter>
        <Analytics />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(4);
    });

    expect(screen.getByText('Volume of Applications by Faculty and Month')).toBeInTheDocument();
    expect(screen.getByText('Human Participants in the Study')).toBeInTheDocument();
    expect(screen.getByText('Overview of Research Project Information')).toBeInTheDocument();
    expect(screen.getByText('Research Project Information')).toBeInTheDocument();
  });

  it('updates charts with fetched data', async () => {
    const mockChartData = {
      labels: ['Arts & Humanities', 'Education & Health', 'Irish World Academy', 'Kemmy Business School', 'Science & Engineering'],
      datasets: [{
        label: 'Volume of Applications',
        data: [3, 1, 2, 4, 21]
      }]
    };

    fetch.mockResponseOnce(JSON.stringify(mockChartData));

    render(
      <MemoryRouter>
        <Analytics />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Volume of Applications')).toBeInTheDocument();
      mockChartData.labels.forEach(label => {
        expect(screen.getByText(label)).toBeInTheDocument();
      });
    });
  });
});