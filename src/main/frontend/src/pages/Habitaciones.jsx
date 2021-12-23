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
    { field: 'codHabitacion', title: 'ID' },
    { field: 'nombre', title: 'Nombre' },
    {
        field: 'cantidadPersonas',
        title: 'Cantidad Personas',
    },
    { field: 'tipoHabitacion', title: 'Tipo Habitación' },
    { field: 'cocinaSN', title: 'Cocina S/N' },
    { field: 'tvhabitacion', title: 'TV S/N' },
    { field: 'camaExtraSN', title: 'Cama Extra S/N' },
    { field: 'precioAdulto', title: 'Precio Adulto' },
    { field: 'precioNinho', title: 'Precio Niño' },
    { field: 'precioMinimo', title: 'Precio Mínimo' },
];

const Habitaciones = () => {
    const navigate = useNavigate();
    const [habitaciones, setHabitaciones] = React.useState([]);
    const [habitacion, setHabitacion] = React.useState([]);
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
        axios.get('/api/habitacion').then((response) => {
            setHabitaciones(response.data);
        });
    };

    const deleteHabitacion = () => {
        handleAlertClose();
        axios.delete('/api/habitacion/' + habitacion.codHabitacion).then((response) => {
            setResponse(response.data.message)
            alert(response.data.message)
            handleMessageOpen();
            navigate(0);
        });
    };

    React.useEffect(() => {
        getData();
    }, []);

    const selectHab = (rowData, action) => {
        action === 'edit'
            ? navigate('/habitacion/edit', { state: rowData })
            : navigate('/habitacion/delete', rowData.id);
    };

    const handleClick = () => navigate('/nuevahabitacion');

    return (
        <Box component="main" sx={{ width: '100%', paddingTop: 10 }}>
            <Container maxWidth="xl">
                <MaterialTable
                    columns={columns}
                    data={habitaciones}
                    title="Lista de habitaciones"
                    actions={[
                        {
                            icon: 'edit',
                            tooltip: 'Editar habitacion',
                            onClick: (event, rowData) => {
                                selectHab(rowData, 'edit');
                            },
                        },
                        {
                            icon: 'delete',
                            tooltip: 'Eliminar habitacion',
                            onClick: (event, rowData) => {
                                setHabitacion(rowData);
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
                                    Añadir Habitacion
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
                        La siguiente habitación será eliminada: {habitacion.nombre}
                    </DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                            ¿Está seguro de querer continuar con la operación?
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleAlertClose}>Cancelar</Button>
                        <Button onClick={deleteHabitacion} autoFocus>
                            Aceptar
                        </Button>
                    </DialogActions>
                </Dialog>
            </Container>
        </Box>
    );
};

export default Habitaciones;