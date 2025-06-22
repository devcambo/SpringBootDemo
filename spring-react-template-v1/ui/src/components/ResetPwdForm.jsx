import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import { resetPassword } from '../contexts/auth/AuthActions';

const ResetPwdForm = () => {
  const [validated, setValidated] = useState(false);
  const [passwords, setPasswords] = useState({
    newPassword: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const queryParams = new URLSearchParams(location.search);
  const token = queryParams.get('token');

  console.log('Reset Password Token:', token);

  const resetPwd = async (newPassword) => {
    try {
      const response = await resetPassword(token, newPassword);
    } catch (error) {
      throw error;
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPasswords({
      ...passwords,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;

    if (form.checkValidity() === false) {
      e.stopPropagation();
    } else {
      // Custom validation: check if passwords match
      if (passwords.newPassword !== passwords.confirmPassword) {
        setError('Passwords do not match.');
        setSuccess('');
        return;
      }

      // Simulate successful password update
      console.log('New password:', passwords.newPassword);
      resetPwd(passwords.newPassword);
      setError('');
      setSuccess('Your password has been successfully reset.');

      // Clear fields
      setPasswords({
        newPassword: '',
        confirmPassword: '',
      });

      // Optionally redirect after delay
      // setTimeout(() => navigate('/login'), 2000);
    }

    setValidated(true);
  };

  return (
    <Container>
      <Row className='justify-content-md-center'>
        <Col md={6}>
          <h3 className='my-4 text-center'>Reset Password</h3>

          <p className='text-muted text-center mb-4'>
            Please enter your new password and confirm it below.
          </p>

          {/* Success or Error Messages */}
          {error && <Alert variant='danger'>{error}</Alert>}
          {success && <Alert variant='success'>{success}</Alert>}

          {/* Reset Password Form */}
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            {/* New Password */}
            <Form.Group
              controlId='validationCustomNewPassword'
              className='mb-3'
            >
              <Form.Label>New Password</Form.Label>
              <Form.Control
                type='password'
                placeholder='Enter new password'
                name='newPassword'
                value={passwords.newPassword}
                onChange={handleChange}
                required
                isInvalid={validated && !passwords.newPassword}
              />
              <Form.Control.Feedback type='invalid'>
                Please enter a new password.
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
                placeholder='Confirm new password'
                name='confirmPassword'
                value={passwords.confirmPassword}
                onChange={handleChange}
                required
                isInvalid={validated && !passwords.confirmPassword}
              />
              <Form.Control.Feedback type='invalid'>
                Please confirm your new password.
              </Form.Control.Feedback>
            </Form.Group>

            {/* Submit Button */}
            <Button type='submit' variant='primary' className='w-100'>
              Reset Password
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default ResetPwdForm;
