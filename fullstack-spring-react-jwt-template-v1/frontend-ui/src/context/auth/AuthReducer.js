const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return {
        ...state,
        jwtToken: action.payload,
        isAuthenticated: true,
      }
    default:
      return state
  }
}

export default authReducer
