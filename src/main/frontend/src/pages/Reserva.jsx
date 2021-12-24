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
  { field: 'codReserva', title: 'ID' },
  { field: 'habitacion.nombre', title: 'Habitación' },
  {
    field: 'fechaIngreso',
    title: 'Fecha de Ingreso',
  },
  { field: 'fechaSalida', title: 'Fecha de salida' },
  { field: 'cantidadAdultos', title: 'Cant. de Adultos' },
  { field: 'cantidadNinhos', title: 'Cant. de Niños' },
  { field: 'fechaReserva', title: 'Fecha de Reserva' },
  { field: 'precioTotal', title: 'Precio Total' },
];

const Reserva = () => {
  const navigate = useNavigate();
  const [reserva, setReserva] = React.useState([]);
  const [reservas, setReservas] = React.useState([]);
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
    // axios.get('/api/usuario/usuarios').then((response) => {
    //   setUsers(response.data);
    // });
    //
    // axios.get('/api/habitacion').then((response) => {
    //   setHabitaciones(response.data);
    // });

    axios.get('/api/reserva').then((response) => {
      setReservas(response.data);
	  console.log(response)
    });
  };


  const deleteUser = () => {
    handleAlertClose();
    axios.delete('/api/reserva/' + reserva.codReserva).then((response) => {
      alert(response.data.message)
      handleMessageOpen();
	  getData();
    })
  };

  React.useEffect(() => {
    getData();
  }, []);

  const selectReserva = (rowData, action) => {
    action === 'edit'
      ? navigate('/reserva/edit', { state: rowData })
      : navigate('/reserva/delete', rowData.id);
  };

  const handleClick = () => navigate('/nuevareserva');

  return (
    <Box component="main" sx={{ width: '100%', paddingTop: 10 }}>
      <Container maxWidth="xl">
        <MaterialTable
          columns={columns}
          data={reservas}
          title="Lista de reservas"
          actions={[
            {
              icon: 'edit',
              tooltip: 'Editar reserva',
              onClick: (event, rowData) => {
                selectReserva(rowData, 'edit');
              },
            },
            {
              icon: 'delete',
              tooltip: 'Eliminar reserva',
              onClick: (event, rowData) => {
                setReserva(rowData);
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
                  Añadir Reserva
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
            El siguiente N° de Reserva será eliminado: {reserva.codReserva}
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              ¿Está seguro de querer continuar con la operación?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleAlertClose}>Cancelar</Button>
            <Button onClick={deleteUser} autoFocus>
              Aceptar
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Box>
  );
};

export default Reserva;
