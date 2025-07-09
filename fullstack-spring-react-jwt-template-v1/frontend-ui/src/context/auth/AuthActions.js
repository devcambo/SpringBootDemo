import apiClient from '../../api/apiClient'

// register
export const registerUser = async (user) => {
  try {
    await apiClient.post('/auth/register', user)
  } catch (error) {
    throw error
  }
}

// login
export const loginUser = async (user) => {
  try {
    const response = await apiClient.post('/auth/login', user)
    return response.data
  } catch (error) {
    throw error
  }
}

// forgot password
export const forgotPassword = async (userEmail) => {
  try {
    await apiClient.post('/auth/forgot-password', userEmail)
  } catch (error) {
    throw error
  }
}

// reset password
export const resetPassword = async (token, userPassword) => {
  try {
    await apiClient.patch(`/auth/reset-password?token=${token}`, userPassword)
  } catch (error) {
    throw error
  }
}
