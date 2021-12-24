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
    estado: Yup.string().required('Obligatorio'),
});

function EditPago() {
    let navigate = useNavigate();
    const { state } = useLocation();

    const handleCancel = () => navigate('/pagos');

    const INITIAL_FORM_STATE = {
        estado: 'INACTIVO',

    };

    const handleSubmit = (values) => {
        axios.put('/api/pago/' + state.codPago, values).then((response) => {
            alert(response.data.message);
            navigate('/pagos');
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
                                            <Typography variant="h4">
                                                Modificar Datos del Pago
                                            </Typography>
                                            <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                                                Código de Pago: {state.codPago}
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField name="estado" label="Estado" autoFocus />
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

export default EditPago;