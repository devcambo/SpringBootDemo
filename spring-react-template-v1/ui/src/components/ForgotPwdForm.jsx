import React, { useContext, useState } from 'react';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../contexts/auth/AuthContext';
import { forgotPassword } from '../contexts/auth/AuthActions';

const ForgotPwdForm = () => {
  const [validated, setValidated] = useState(false);
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const { dispatch } = useContext(AuthContext);
  const navigate = useNavigate();

  const forgotPwd = async (email) => {
    try {
      const response = await forgotPassword(email);
      navigate('/login'); // Redirect to login after successful password reset request
    } catch (error) {
      throw error;
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;

    if (form.checkValidity() === false) {
      e.stopPropagation();
    } else {
      // Simulate sending a password reset email
      forgotPassword(email);

      // Fake success message
      setMessage('A password reset link has been sent to your email.');
      setError('');
      setEmail('');
    }

    setValidated(true);
  };

  return (
    <Container>
      <Row className='justify-content-md-center'>
        <Col md={6}>
          <h3 className='my-4 text-center'>Forgot Password</h3>

          <p className='text-muted text-center mb-4'>
            Enter the email address associated with your account and we'll send
            you a link to reset your password.
          </p>

          {/* Success or Error Messages */}
          {error && <Alert variant='danger'>{error}</Alert>}
          {message && <Alert variant='success'>{message}</Alert>}

          {/* Forgot Password Form */}
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            <Form.Group controlId='validationCustomEmail' className='mb-3'>
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type='email'
                placeholder='Enter your email'
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                isInvalid={validated && !email}
              />
              <Form.Control.Feedback type='invalid'>
                Please enter a valid email address.
              </Form.Control.Feedback>
            </Form.Group>

            <Button type='submit' variant='primary' className='w-100'>
              Send Reset Link
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default ForgotPwdForm;
