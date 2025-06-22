import React, { useContext, useState } from 'react';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import AuthContext from '../contexts/auth/AuthContext';
import { register } from '../contexts/auth/AuthActions';
import { useNavigate } from 'react-router-dom';

const RegisterForm = () => {
  const [validated, setValidated] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const { dispatch } = useContext(AuthContext);
  const nagivate = useNavigate();

  const registerUser = async (userData) => {
    try {
      const response = await register(userData);
      dispatch({
        type: 'REGISTER',
        payload: response,
      });
    } catch (error) {
      console.error('Registration failed:', error);
    }
  };

  const handleSubmit = (e) => {
    const form = e.currentTarget;
    e.preventDefault();

    if (form.checkValidity() === false) {
      e.stopPropagation();
    } else {
      // Custom validation: check if passwords match
      if (formData.password !== formData.confirmPassword) {
        setError('Passwords do not match.');
        setSuccess('');
        return;
      }
      const userData = {
        username: formData.username,
        email: formData.email,
        password: formData.password,
      };

      registerUser(userData);
      nagivate('/login');
      setError('');
      setSuccess('Registration successful!');

      clearForm();
    }

    setValidated(true);
  };

  const clearForm = () => {
    setFormData({
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
    });
    setError('');
    setSuccess('');
    setValidated(false);
  };

  return (
    <Container>
      <Row className='justify-content-md-center'>
        <Col md={6}>
          <h3 className='my-4 text-center'>Register</h3>

          {error && <Alert variant='danger'>{error}</Alert>}
          {success && <Alert variant='success'>{success}</Alert>}

          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            {/* Username */}
            <Form.Group controlId='validationCustomUsername' className='mb-3'>
              <Form.Label>Username</Form.Label>
              <Form.Control
                type='text'
                placeholder='Enter username'
                name='username'
                value={formData.username}
                onChange={handleChange}
                required
              />
              <Form.Control.Feedback type='invalid'>
                Please choose a username.
              </Form.Control.Feedback>
            </Form.Group>

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
                Please enter a password.
              </Form.Control.Feedback>
            </Form.Group>

            {/* Confirm Password */}
            <Form.Group
              controlId='validationCustomConfirmPassword'
              className='mb-3'
            >
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control
                type='password'
                placeholder='Confirm password'
                name='confirmPassword'
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
              <Form.Control.Feedback type='invalid'>
                Please confirm your password.
              </Form.Control.Feedback>
            </Form.Group>

            {/* Submit Button */}
            <Button type='submit' variant='primary' className='w-100'>
              Register
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default RegisterForm;
