import React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import AppBar from '@mui/material/AppBar';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import LoginIcon from '@mui/icons-material/Login';
import Link from './FormUI/LinkWrapper';
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
          <Divider />
          <List>
            <ListItem button key="Login" onClick={(event) => handleClicks("/mylogin")}>
              <ListItemIcon>
                <LoginIcon />
              </ListItemIcon>
              <ListItemText primary="login" />
            </ListItem>
            <ListItem button key="" onClick={(event) => handleClicks("/mylogin")}>
              <ListItemIcon>
                <LoginIcon />
              </ListItemIcon>
              <ListItemText primary="login" />
            </ListItem>
          </List>
        </Box>
      </Drawer>
    </div>
  );
};

export default DrawerWrapper;
