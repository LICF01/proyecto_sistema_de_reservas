import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import { useNavigate, useLocation } from 'react-router-dom';

const FORM_VALIDATION = Yup.object().shape({
    fechaIngreso: Yup.string().required('Obligatorio'),
    fechaSalida: Yup.string().required('Obligatorio'),
    cantidadAdultos: Yup.string().required('Obligatorio'),
    cantidadNinhos: Yup.string().required('Obligatorio'),
});

function EditarReserva() {
    let navigate = useNavigate();
    const { state } = useLocation();
    // console.log(state);

    const handleCancel = () => navigate('/reservas');

    const INITIAL_FORM_STATE = {
        habitacion: {
            codhabitacion: state.habitacion.codhabitacion,
        },
        fechaIngreso: state.fechaIngreso,
        fechaSalida: state.fechaSalida,
        cantidadAdultos: state.cantidadAdultos,
        cantidadNinhos: state.cantidadNinhos,
    };

    const handleSubmit = (values) => {
        axios.put('/api/reserva/' + state.codReserva, values).then((response) => {
            // console.log(values)
            alert(response.data.message);
            navigate('/reservas');
        }).catch ((error) => {
            console.log(values)
            alert(error.response.data.message);
        });
        console.log(INITIAL_FORM_STATE)
    };

    return (

        <Container component="div" maxWidth="sm">
            <Grid
                container
                direction="column"
                alignItems="center"
                justifyContent="center"
                marginTop={-4}
                style={{ minHeight: '100vh' }}
            >
                <Grid item>
                    <Paper variant="outlined" elevation={0}>
                        <Box p={5}>
                            <Formik
                                initialValues={{
                                    ...INITIAL_FORM_STATE,
                                }}
                                validationSchema={FORM_VALIDATION}
                                validateOnChange={false}
                                validateOnBlur={false}
                                onSubmit={(values) => {
                                    handleSubmit(values);

                                }}
                            >
                                <Form>
                                    <Grid container spacing={6}>
                                        <Grid
                                            item
                                            container
                                            justifyContent="center"
                                            flexDirection="column"
                                            alignItems="center"
                                        >
                                            <Typography variant="h3">
                                                {state.nombre}
                                            </Typography>
                                            <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                                                Modificar Datos de la Reserva
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField name="fechaIngreso" label="Fecha de Ingreso (AAAA/MM/DD)" />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField name="fechaSalida" label="Fecha de Salida (AAAA/MM/DD)" />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField
                                                name="cantidadAdultos"
                                                label="Cantidad Adultos"
                                            />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField
                                                name="cantidadNinhos"
                                                label="Cantidad Niños"
                                            />
                                        </Grid>
                                        <Grid container item justifyContent="center" spacing={2}>
                                            <Grid item xs={5}>
                                                <Button value="submit">
                                                    Guardar
                                                </Button>
                                            </Grid>
                                            <Grid item xs={5}>
                                                <Button variant="outlined" onClick={handleCancel}>
                                                    Cancelar
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Form>
                            </Formik>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
}

export default EditarReserva;