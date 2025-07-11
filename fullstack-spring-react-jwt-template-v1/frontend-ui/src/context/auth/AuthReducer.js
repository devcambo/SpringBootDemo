const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return {
        ...state,
        jwtToken: action.payload,
        isAuthenticated: true,
      }
    case 'LOGOUT':
      return {
        ...state,
        jwtToken: null,
        isAuthenticated: false,
      }
    default:
      return state
  }
}

export default authReducer
