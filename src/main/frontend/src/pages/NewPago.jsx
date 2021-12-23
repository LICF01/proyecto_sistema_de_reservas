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
    montoPago: '',
    metodoPago: '',
    reserva: {
        codReserva: ''
    }
};

const FORM_VALIDATION = Yup.object().shape({
    metodoPago: Yup.string().required('Obligatorio'),
    montoPago: Yup.number().positive('Debe ingresar un numero positivo').integer().required('Obligatorio'),
});


function NewPago() {
    let navigate = useNavigate();

    const handleCancel = () => navigate('/pagos');

    const handleSubmit= async (values) => {

        const response = await fetch('/api/pago', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        });
        console.log(response)

        if (!(response.status === 201)) return alert('Debe ingresar los datos correctos')

        navigate('/pagos');
        //const data = await response.json();

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
                                                Ingresar los datos del pago:
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="metodoPago"
                                                label="Metodo de Pago"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="montoPago"
                                                label="Monto de Pago"
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <TextField
                                                name="reserva.codReserva"
                                                label="Nro Reserva"
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

export default NewPago;