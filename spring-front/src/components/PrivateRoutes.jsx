import { Navigate, Outlet } from 'react-router-dom'
import store from '../services/store'; 
export  const  PrivateRoutes = () => {
    const { token } = store();
    let auth = {'token':token}

    return (
      auth.token ? <Outlet/> : <Navigate to='/login'/>
    )
  }
