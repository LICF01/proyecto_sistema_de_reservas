import React from 'react';
import Box from '@mui/material/Box';
import Login from './pages/Login';
import Registration from './pages/Registration';
import PasswordReset from './pages/PasswordReset';
import MailSent from './pages/MailSent';
import NewPassword from './pages/EnterNewPassword';
import Usuarios from './pages/Usuarios';
import SideBar from './components/DrawerWrapper';
import NavBar from './components/NavBar';
import NuevoUsuario from './pages/NewUser';
import Habitaciones from "./pages/Habitaciones";
import UserConfirmed from './pages/UserConfirmed';
import EditUser from './pages/EditUser';

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function App() {
  return (
    <Router>
      <NavBar />
      <Box sx={{ display: 'flex' }}>
        <SideBar />
        <Routes>
          <Route exact path="/mylogin" element={<Login />} />
          <Route exact path="/registration" element={<Registration />} />
          <Route exact path="/passwords/reset" element={<PasswordReset />} />
          <Route exact path="/passwords/mailsent" element={<MailSent />} />
          <Route
            exact
            path="/passwords/newpassword"
            element={<NewPassword />}
          />
          <Route exact path="/usuarios" element={<Usuarios />} />
          <Route exact path="/nuevousuario" element={<NuevoUsuario />} />
          <Route exact path="/habitaciones" element={<Habitaciones />} />
          <Route exact path="/user/confirmed" element={<UserConfirmed />} />
          <Route exact path="/user/edit" element={<EditUser />} />
        </Routes>
      </Box>
    </Router>
  );
}

export default App;
