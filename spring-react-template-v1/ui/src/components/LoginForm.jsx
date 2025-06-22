import React, { useContext, useState } from 'react';
import { Form, Button, Container, Row, Col, Alert, Nav } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../contexts/auth/AuthContext';
import { login } from '../contexts/auth/AuthActions';

const LoginForm = () => {
  const [validated, setValidated] = useState(false);
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const { dispatch } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const loginUser = async (email, password) => {
    try {
      const response = await login(email, password);
      dispatch({
        type: 'LOGIN',
        payload: response.access_token,
      });
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;

    if (form.checkValidity() === false) {
      e.stopPropagation();
    } else {
      // Simulate login logic or API call
      console.log('Form data:', formData);
      const { email, password } = formData;
      loginUser(email, password);

      // Example of fake validation
    }

    setValidated(true);
  };

  return (
    <Container>
      <Row className='justify-content-md-center'>
        <Col md={6}>
          <h3 className='my-4 text-center'>Login</h3>

          {/* Success or Error Messages */}
          {error && <Alert variant='danger'>{error}</Alert>}
          {success && <Alert variant='success'>{success}</Alert>}

          {/* Login Form */}
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            {/* Email */}
            <Form.Group controlId='validationCustomEmail' className='mb-3'>
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type='email'
                placeholder='Enter email'
                name='email'
                value={formData.email}
                onChange={handleChange}
                required
              />
              <Form.Control.Feedback type='invalid'>
                Please enter a valid email address.
              </Form.Control.Feedback>
            </Form.Group>

            {/* Password */}
            <Form.Group controlId='validationCustomPassword' className='mb-3'>
              <Form.Label>Password</Form.Label>
              <Form.Control
                type='password'
                placeholder='Password'
                name='password'
                value={formData.password}
                onChange={handleChange}
                required
              />
              <Form.Control.Feedback type='invalid'>
                Please enter your password.
              </Form.Control.Feedback>
            </Form.Group>

            {/* Submit Button */}
            <Button type='submit' variant='primary' className='w-100 mb-3'>
              Login
            </Button>
          </Form>

          {/* Forgot Password Link */}
          <div className='text-center'>
            <Nav.Link as={Link} to='/forgot-password' className='small'>
              Forgot Password?
            </Nav.Link>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginForm;
