import React from 'react';
import { Navbar, Container, Button, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const MainNavbar = () => {
  return (
    <Navbar bg='light' expand='lg' className='py-3 shadow-sm'>
      <Container>
        <Navbar.Brand as={Link} to='/'>
          MyApp
        </Navbar.Brand>
        <Nav className='ms-auto d-flex gap-2 align-items-center'>
          <Button variant='outline-primary' as={Link} to='/login'>
            Login
          </Button>
          <Button variant='primary' as={Link} to='/register'>
            Register
          </Button>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default MainNavbar;
