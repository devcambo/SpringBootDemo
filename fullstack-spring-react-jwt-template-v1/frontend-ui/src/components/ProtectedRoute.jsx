import React, { useContext } from 'react'
import AuthContext from '../context/auth/AuthContext'
import { Navigate, Outlet } from 'react-router-dom'

const ProtectedRoute = () => {
  const { isAuthenticated } = useContext(AuthContext)
  return isAuthenticated ? <Outlet /> : <Navigate to='/login' />
}

export default ProtectedRoute
