import apiClient from '../../api/apiClient'

// upload
export const uploadFile = async (file) => {
  try {
    const response = await apiClient.post('/files/upload', file, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    return await response.data
  } catch (error) {
    throw error
  }
}
