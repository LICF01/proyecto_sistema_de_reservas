import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import { useNavigate } from 'react-router-dom';
// import MenuItem from '@mui/material/MenuItem';
// import Select from '@mui/material/Select';
// // import InputLabel from '@mui/material/InputLabel';

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
    cantidadPersonas: Yup.number().min(1).required('Obligatorio'),
    tipoHabitacion: Yup.string().required('Obligatorio'),
    cocinaSN: Yup.string().required('Obligatorio'),
    tvhabitacion: Yup.string().required('Obligatorio'),
    camaExtraSN: Yup.string().required('Obligatorio'),
    precioAdulto: Yup.number().min(1).required('Obligatorio'),
    precioNinho: Yup.number().min(1).required('Obligatorio'),
    precioMinimo: Yup.number().min(1).required('Obligatorio'),
});

function NewHabitacion() {
    let navigate = useNavigate();
    // const [age, setAge] = React.useState('');

    // const handleChange = (event) => {
    //     INITIAL_FORM_STATE.cocinaSN = event.target.value;
    //     // setAge(event.target.value);
    // };

    const handleCancel = () => navigate('/habitaciones');


    const handleSubmit= async (values) => {
        console.log(values)
        const response = await fetch('/api/habitacion', {
            method: 'POST',
            // headers: { "Content-Type": "application/json" },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        });

        if (!(response.status === 201)) return alert('No se han podido cargar los datos');

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
                                            {/*<InputLabel id="demo-simple-select-label">Age</InputLabel>*/}
                                            {/*<Select fullWidth={120}*/}
                                            {/*        labelId="demo-simple-select-label"*/}
                                            {/*        label="Cocina"*/}
                                            {/*        id="demo-simple-select"*/}
                                            {/*        value={INITIAL_FORM_STATE.cocinaSN}*/}
                                            {/*        onChange={handleChange}*/}
                                            {/*>*/}
                                            {/*    <MenuItem value='TRUE'>Si</MenuItem>*/}
                                            {/*    <MenuItem value='FALSE'>No</MenuItem>*/}
                                            {/*</Select>*/}
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
