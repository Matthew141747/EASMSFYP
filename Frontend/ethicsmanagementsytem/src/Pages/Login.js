import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button, Form, Container, Row, Col, Spinner, Alert, Card } from 'react-bootstrap';
import '../Styling/LoginForm.css';

const LoginForm = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();
        setLoading(true);
        setMessage('');

        const userData = {
            username,
            password
        };

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                const data = await response.json();
                onLogin(username, data.token, data.accountType);
                navigate('/Home');
            } else {
                const errorData = await response.json();
                setMessage(errorData.message || 'Login failed');
            }
        } catch (error) {
            console.error('Login failed:', error);
            setMessage('Login request failed. Please try again.');
        }
        setLoading(false);
    };

    return (
        <Container className="login-container">
        <Row>
            <Col md={6} className="mx-auto">
                <Card className="login-card">
                    <Card.Body>
                        <h2 className="text-center">EASMS Login</h2>
                        <p className="text-center">Please enter your login credentials below:</p>
                        <Form onSubmit={handleLogin}>
                            {message && <Alert variant="danger">{message}</Alert>}
                            <Form.Group className="mb-3">
                                <Form.Label>Username</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter username"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </Form.Group>
                            <div className="button-grid">
                                <Button variant="primary" type="submit" disabled={loading}>
                                    {loading ? (
                                        <>
                                            <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                                            <span className="visually-hidden">Loading...</span>
                                        </>
                                    ) : (
                                        'Login'
                                    )}
                                </Button>
                            </div>
                            <div className="extra-actions">
                                <Link to="/Registration">Need an account? Register</Link>
                            </div>
                        </Form>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    </Container>
    );
};

export default LoginForm;
