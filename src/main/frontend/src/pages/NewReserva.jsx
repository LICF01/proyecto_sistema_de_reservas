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
    cliente: {
        id: ''
    },
    habitacion: {
        codHabitacion: ''
    },
    fechaIngreso: '',
    fechaSalida: '',
    cantidadAdultos: '',
    cantidadNinhos: '',
};

const FORM_VALIDATION = Yup.object().shape({
    fechaIngreso: Yup.string().required('Obligatorio'),
    fechaSalida: Yup.string().required('Obligatorio'),
    cantidadAdultos: Yup.number().positive('Debe ingresar un numero positivo').integer().required('Obligatorio'),
    cantidadNinhos: Yup.number('Debe ingresar un numero').integer().required('Obligatorio'),
});


function NewReserva() {
    let navigate = useNavigate();

    const handleCancel = () => navigate('/reservas');

    const handleSubmit= async (values) => {
        console.log(values)
        // axios.post('/api/habitacion', values).then((response) => {
        //     alert(response.data.message);
        //     navigate('/habitaciones');
        // });
        const response = await fetch('/api/reserva', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        }).catch ((error) => {
            alert(error.response.data.message);
        });

        if (!(response.status === 201)) return alert('Debe ingresar los datos correctos')

        const data = await response.json();


        navigate('/reservas');
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
                                                Ingresar los datos de la Reserva:
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField name="cliente.id" label="N° Cliente" />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField name="habitacion.codHabitacion" label="N° Habitación" />
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

export default NewReserva;
