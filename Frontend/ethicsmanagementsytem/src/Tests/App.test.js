import { render, screen } from '@testing-library/react';
import App from '../App';

const mockLocalStorage = () => {
  const storage = {};
  return {
    getItem: (key) => storage[key] || null,
    setItem: (key, value) => storage[key] = value || '',
    removeItem: (key) => delete storage[key],
    clear: () => Object.keys(storage).forEach(key => delete storage[key])
  };
};

Object.defineProperty(window, 'localStorage', { value: mockLocalStorage() });

describe('App Component', () => {
  test('renders Home component for "/" route', () => {
    window.localStorage.setItem('userToken', 'dummy-token'); 
    render(<App />);
    
    expect(screen.getByText(/home/i)).toBeInTheDocument(); 
  });

  test('redirects to login if not authenticated and accessing a protected route', () => {
    window.localStorage.removeItem('userToken');
    render(<App />);

    expect(screen.getByText(/login/i)).toBeInTheDocument(); 
  });
});
