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
    nombre: Yup.string().required('Obligatorio'),
    cantidadPersonas: Yup.number().min(1, 'Debe ingresar un número del 1 al 6').max(6, 'Debe ingresar un número del 1 al 6').positive('Debe ingresar un número del 1 al 6').integer().required('Obligatorio'),
    tipoHabitacion: Yup.string().required('Obligatorio'),
    cocinaSN: Yup.string().required('Obligatorio'),
    tvhabitacion: Yup.string().required('Obligatorio'),
    camaExtraSN: Yup.string().required('Obligatorio'),
    precioAdulto: Yup.number().positive('Debe ingresar un numero positivo').integer().required('Obligatorio'),
    precioNinho: Yup.number().positive('Debe ingresar un numero positivo').integer().required('Obligatorio'),
    precioMinimo: Yup.number().positive('Debe ingresar un numero positivo').integer().required('Obligatorio'),
});

function EditarHabitacion() {
    let navigate = useNavigate();
    const { state } = useLocation();
    console.log(state);

    const handleCancel = () => navigate('/habitaciones');

    const INITIAL_FORM_STATE = {
        nombre: state.nombre,
        cantidadPersonas: state.cantidadPersonas,
        tipoHabitacion: state.tipoHabitacion,
        cocinaSN: state.cocinaSN,
        tvhabitacion: state.tvhabitacion,
        camaExtraSN: state.camaExtraSN,
        precioAdulto: state.precioAdulto,
        precioNinho: state.precioNinho,
        precioMinimo: state.precioMinimo,
    };

    const handleSubmit = (values) => {
        axios.put('/api/habitacion/' + state.codHabitacion, values).then((response) => {
            alert(response.data.message);
            navigate('/habitaciones');
        }).catch ((error) => {
            alert(error.response.data.message);
        });
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
                                                Modificar Datos de la Habitación
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField name="nombre" label="Nombre" autoFocus />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField name="cantidadPersonas" label="Cantidad de Personas" />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField
                                                name="tipoHabitacion"
                                                label="Tipo de Habitacion"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="cocinaSN"
                                                label="Cocina Si/No"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="tvhabitacion"
                                                label="TV Si/No"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="camaExtraSN"
                                                label="Cama Extra Si/No"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="precioAdulto"
                                                label="Precio Adulto"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="precioNinho"
                                                label="Precio Niño"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="precioMinimo"
                                                label="Precio Minimo"
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

export default EditarHabitacion;