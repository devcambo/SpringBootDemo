import React from 'react';
import { Outlet } from 'react-router-dom';
import MainNavbar from './MainNavbar';
import MainFooter from './MainFooter';

const MainLayout = () => {
  return (
    <div className='d-flex flex-column min-vh-100'>
      <MainNavbar />
      <main className='flex-grow-1'>
        <Outlet />
      </main>
      <MainFooter />
    </div>
  );
};

export default MainLayout;
