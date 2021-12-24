import * as React from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import { Container } from '@mui/material';
import MaterialTable, { MTableToolbar } from '@material-table/core';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';

const modalStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const columns = [
  { field: 'id', title: 'ID' },
  { field: 'nombre', title: 'Nombre' },
  {
    field: 'apellido',
    title: 'Apellido',
  },
  { field: 'enabled', title: 'Habilitado' },
  { field: 'fechaNac', title: 'Fecha de Nacimiento' },
  { field: 'locked', title: 'Bloqueado' },
  { field: 'mail', title: 'Email' },
  { field: 'nroDocumento', title: 'Documento' },
  { field: 'telefono', title: 'Contacto' },
  { field: 'tipoDocumento', title: 'Tipo Documento' },
];

const Usuarios = () => {
  const navigate = useNavigate();
  const [users, setUsers] = React.useState([]);
  const [user, setUser] = React.useState([]);
  const [response, setResponse] = React.useState([]);
  const [showAlert, setShowAlert] = React.useState(false);
  const [showMessage, setShowMessage] = React.useState(false);

  const handleAlertOpen = () => {
    setShowAlert(true);
  };

  const handleAlertClose = () => {
    setShowAlert(false);
  };

  const handleMessageOpen = () => {
    setShowMessage(true);
  };

  const handleMessageClose = () => {
    setShowMessage(false);
  };
  const getData = () => {
    axios.get('/api/usuario/usuarios').then((response) => {
      setUsers(response.data);
    });
  };

  const deleteUser = () => {
    handleAlertClose();
    axios.delete('/api/usuario/' + user.id).then((response) => {
      setResponse(response.data);
      handleMessageOpen();
	  getData();
    });
  };

  React.useEffect(() => {
    getData();
  }, []);

  const selectUser = (rowData, action) => {
    action === 'edit'
      ? navigate('/user/edit', { state: rowData })
      : navigate('/user/delete', rowData.id);
  };

  const handleClick = () => navigate('/nuevousuario');

  return (
    <Box component="main" sx={{ width: '100%', paddingTop: 10 }}>
      <Container maxWidth="xl">
        <MaterialTable
          columns={columns}
          data={users}
          title="Lista de usuarios"
          actions={[
            {
              icon: 'edit',
              tooltip: 'Editar usuario',
              onClick: (event, rowData) => {
                selectUser(rowData, 'edit');
              },
            },
            {
              icon: 'delete',
              tooltip: 'Eliminar usuario',
              onClick: (event, rowData) => {
                setUser(rowData);
                handleAlertOpen();
              },
            },
          ]}
          options={{ actionsColumnIndex: -1 }}
          localization={{
            header: {
              actions: 'Acciones',
            },
          }}
          components={{
            Toolbar: (props) => (
              <Box
                sx={{ display: 'flex', alignItems: 'center', paddingRight: 3 }}
              >
                <Box sx={{ flexGrow: 1, paddingRight: 5 }}>
                  <MTableToolbar {...props} />
                </Box>
                <Button variant="outlined" onClick={handleClick}>
                  Añadir Usuario
                </Button>
              </Box>
            ),
          }}
        />

        <Dialog
          open={showAlert}
          onClose={handleAlertClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title" sx={{}}>
            El siguiente usuario sera eliminado {user.mail}
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              Esta seguro de querer continuar con la operación?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleAlertClose}>Cancelar</Button>
            <Button onClick={deleteUser} autoFocus>
              Aceptar
            </Button>
          </DialogActions>
        </Dialog>

        <Modal
          open={showMessage}
          onClose={handleMessageClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={modalStyle}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
            </Typography>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
              {response.message}
            </Typography>
          </Box>
        </Modal>
      </Container>
    </Box>
  );
};

export default Usuarios;
