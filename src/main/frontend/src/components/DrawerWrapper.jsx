import React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import LoginIcon from '@mui/icons-material/Login';
import KingBedIcon from '@mui/icons-material/KingBed';
import BookOnlineIcon from '@mui/icons-material/BookOnline';
import PaymentsIcon from '@mui/icons-material/Payments';
import { useNavigate, useLocation } from 'react-router-dom';

const drawerWidth = 240;

const DrawerWrapper = () => {
  let navigate = useNavigate();

	const handleClicks = (path) => {
		console.log(path)
		navigate(path)
	}

  return (
    <div>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto' }}>
          <List>
            <ListItem button key="Usuarios" onClick={(event) => handleClicks("/usuarios")}>
              <ListItemIcon>
                <PeopleAltIcon />
              </ListItemIcon>
              <ListItemText primary="Usuarios" />
            </ListItem>
          </List>
            <ListItem button key="habitaciones" onClick={(event) => handleClicks("/habitaciones")}>
              <ListItemIcon>
                <KingBedIcon />
              </ListItemIcon>
              <ListItemText primary="Habitaciones" />
            </ListItem>
            <ListItem button key="reservas" onClick={(event) => handleClicks("/reservas")}>
              <ListItemIcon>
                <BookOnlineIcon />
              </ListItemIcon>
              <ListItemText primary="Reservas" />
            </ListItem>
            <ListItem button key="pagos" onClick={(event) => handleClicks("/pagos")}>
              <ListItemIcon>
                <PaymentsIcon />
              </ListItemIcon>
              <ListItemText primary="Pagos" />
            </ListItem>
          <Divider />
          <List>
            <ListItem button key="Login" onClick={(event) => handleClicks("/mylogin")}>
              <ListItemIcon>
                <LoginIcon />
              </ListItemIcon>
              <ListItemText primary="Login" />
            </ListItem>
          </List>
        </Box>
      </Drawer>
    </div>
  );
};

export default DrawerWrapper;
