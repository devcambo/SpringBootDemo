import apiClient from '../../api/apiClient'

// profile
export const findUserProfile = async () => {
  try {
    const response = await apiClient.get('/users/me')
    return response.data
  } catch (error) {
    throw error
  }
}

// update user
export const updateUser = async (userId, user) => {
  try {
    await apiClient.put(`/users/${userId}`, user)
  } catch (error) {
    throw error
  }
}
