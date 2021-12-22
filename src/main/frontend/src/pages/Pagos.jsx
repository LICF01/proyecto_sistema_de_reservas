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
    { field: 'codPago', title: 'ID Pago' },
    { field: 'estado', title: 'Estado' },
    {
        field: 'fechaPago',
        title: 'Fecha de Pago',
    },
    { field: 'metodoPago', title: 'Método de Pago' },
    { field: 'montoPago', title: 'Monto del Pago' },
    { field: 'reserva.codReserva', title: 'Código Reserva' },
    { field: 'reserva.cliente.nombre', title: 'Cliente' },
    { field: 'reserva.habitacion.nombre', title: 'Habitación' },
];

const Habitaciones = () => {
    const navigate = useNavigate();
    const [pagos, setPagos] = React.useState([]);
    const [pago, setPago] = React.useState([]);
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
        axios.get('/api/pago').then((response) => {
            setPagos(response.data);
        });
    };

    const deleteUser = () => {
        handleAlertClose();
        axios.delete('/api/pago/' + pago.pagoid).then((response) => {
            setResponse(response.data);
            handleMessageOpen();
            navigate(0);
        });
    };

    React.useEffect(() => {
        getData();
    }, []);

    const selectPago = (rowData, action) => {
        action === 'edit'
            ? navigate('/pago/edit', { state: rowData })
            : navigate('/pago/delete', rowData.id);
    };

    const handleClick = () => navigate('/nuevopago');

    return (
        <Box component="main" sx={{ width: '100%', paddingTop: 10 }}>
            <Container maxWidth="xl">
                <MaterialTable
                    columns={columns}
                    data={pagos}
                    title="Lista de pagos"
                    actions={[
                        {
                            icon: 'edit',
                            tooltip: 'Editar pago',
                            onClick: (event, rowData) => {
                                selectPago(rowData, 'edit');
                            },
                        },
                        {
                            icon: 'delete',
                            tooltip: 'Eliminar pago',
                            onClick: (event, rowData) => {
                                setPago(rowData);
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
                                    Añadir Pago
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
                        El siguiente pago sera eliminado {pago.pagoID}
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
            </Container>
        </Box>
    );
};

export default Habitaciones;