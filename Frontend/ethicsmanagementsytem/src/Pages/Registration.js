import React, { useState } from 'react';
import { Container, Row, Col, Form, Card, Button, Alert, Spinner } from 'react-bootstrap';
import '../Styling/Register.css';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordB, setPasswordB] = useState('');
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');


    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setMessage(''); 

         // Make sure both passwords match
        if (password !== passwordB) {
            setMessage('Passwords do not match.');
            setLoading(false);
            return;
        }

        const userData = {
            username: username,
            email: email,
            password: password,
        };

        try {
            
            const response = await fetch("http://localhost:8080/api/users/register", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
               setMessage('Registration successful! Please log in.');

            } else {
                const responseData = await response.json();
                 setMessage(responseData.message || 'Registration failed. Please try again.');
                console.log("error", response.status, responseData);
            }
        } catch (error) {
            // Handle network errors or other issues
            setMessage('Registration request failed. Please try again.');
            console.error('Registration failed:', error);
        }
        setLoading(false);
    };

    return (
        <Container className="register-container">
        <Row>
            <Col md={6} className="mx-auto">
                <Card className="register-card">
                    <Card.Body>
                        <h2 className="text-center">EASMS Register</h2>
                        <p className="text-center">Please fill in the information below:</p>
                        <Form onSubmit={handleSubmit}>
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
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder="Enter email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
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
                            <Form.Group className="mb-3">
                                <Form.Label>Confirm Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Confirm Password"
                                    value={passwordB}
                                    onChange={(e) => setPasswordB(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3 button-grid">
                            <Button variant="primary" type="submit" disabled={loading} block>
                                {loading ? (
                                    <>
                                        <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                                        <span className="visually-hidden">Loading...</span>
                                    </>
                                ) : (
                                    'Register'
                                )}
                            </Button>
                        </Form.Group>
                        </Form>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    </Container>
);
};

export default RegisterForm;