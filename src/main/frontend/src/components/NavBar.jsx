import React from "react";
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

const NavBar = () => {
	return (
		<div>
			 <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
			  Administración
          </Typography>
        </Toolbar>
      </AppBar>
	  <Toolbar />
		</div>
	)
}

export default NavBar
