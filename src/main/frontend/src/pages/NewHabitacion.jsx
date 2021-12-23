import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import { useNavigate } from 'react-router-dom';

const INITIAL_FORM_STATE = {
    nombre: '',
    cantidadPersonas: '',
    tipoHabitacion: '',
    cocinaSN: '',
    tvhabitacion: '',
    camaExtraSN: '',
    precioAdulto: '',
    precioNinho: '',
    precioMinimo: '',
};

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


function NewHabitacion() {
    let navigate = useNavigate();

    const handleCancel = () => navigate('/habitaciones');

    const handleSubmit= async (values) => {
        console.log(values)
        // axios.post('/api/habitacion', values).then((response) => {
        //     alert(response.data.message);
        //     navigate('/habitaciones');
        // });
        const response = await fetch('/api/habitacion', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        }).catch ((error) => {
            alert(error.response.data.message);
        });;

        if (!(response.status === 201)) return alert('Debe ingresar los datos correctos')

        const data = await response.json();


        navigate('/habitaciones');
    };


    return (
        <Container component="div" maxWidth="md">
            <Grid
                container
                direction="column"
                alignItems="center"
                justifyContent="center"
                marginTop={-10}
                style={{ minHeight: '100vh' }}
            >
                <Grid item>
                    <Paper variant="outlined" elevation={0}>
                        <Box p={5}>
                            <Grid item container justifyContent="center">
                            </Grid>
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
                                    <Grid container spacing={3}>
                                        <Grid item container xs={12} justifyContent="left">
                                            <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                                                Ingresar los datos de la habitacion:
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField name="nombre" label="Nombre" autoFocus />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField name="cantidadPersonas" label="Cantidad de Personas" />
                                        </Grid>
                                        <Grid item xs={4}>
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
                                        <Grid container item justifyContent="flex-end" spacing={2}>
                                            <Grid item xs={2}>
                                                <Button variant="outlined" onClick={handleCancel}>
                                                    Cancelar
                                                </Button>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Button value="submit">
                                                    Aceptar
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

export default NewHabitacion;
