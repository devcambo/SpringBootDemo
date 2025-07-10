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
