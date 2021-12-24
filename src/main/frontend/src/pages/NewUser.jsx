import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography, Divider } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import Link from '../components/FormUI/LinkWrapper';
import { useNavigate } from 'react-router-dom';


const INITIAL_FORM_STATE = {
  nombre: '',
  apellido: '',
  mail: '',
  contrasena: '',
  telefono: '',
  fechaNac: '',
  tipoDocumento: '',
  nroDocumento: '',
};

const FORM_VALIDATION = Yup.object().shape({
  nombre: Yup.string().required('Obligatorio'),
  apellido: Yup.string().required('Obligatorio'),
  mail: Yup.string().email('El correo no es válido').required('Obligatorio'),
  contrasena: Yup.string().required('Obligatorio'),
  telefono: Yup.string().required('Obligatorio'),
  fechaNac: Yup.string().required('Obligatorio'),
  tipoDocumento: Yup.string().required('Obligatorio'),
  nroDocumento: Yup.string().required('Obligatorio'),
});

function Registration() {
  let navigate = useNavigate();

  const handleSubmit = async (values) => {
    console.log(values);
    const response = await fetch('/api/usuario/add', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(values),
    });

    if (response.status !== 200)
      return alert(response.message);

      navigate('/usuarios');

  };

  return (
    <Container component="div" maxWidth="md">
      <Grid
        container
        direction="column"
        alignItems="center"
        justifyContent="center" marginTop={-5} style={{ minHeight: '100vh' }}
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
                        Formulario de usuario
                      </Typography>
                      <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                        Complete los siguientes campos
                      </Typography>
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="nombre" label="Nombre" autoFocus />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="apellido" label="Apellido" />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="mail"
                        label="Email"
                        autoComplete="email"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="contrasena"
                        label="Contraseña"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="telefono" label="Telefono" type="tel" />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="fechaNac" label="Fecha de nacimiento (dd/MM/yyyy)" />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="tipoDocumento"
                        label="Tipo de Documento"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="nroDocumento"
                        label="Numero de documento"
                      />
                    </Grid>
                    <Grid container item justifyContent="center" spacing={2}>
                      <Grid item xs={6}>
                        <Button value="submit">
                          Añadir
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

export default Registration;
