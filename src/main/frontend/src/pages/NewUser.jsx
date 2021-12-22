import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import Link from '../components/FormUI/LinkWrapper';
import Avatar from '@mui/material/Avatar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';

const INITIAL_FORM_STATE = {
  name: '',
  lastName: '',
  email: '',
  password: '',
  telefono: '',
  fechaNac: '',
  tipoDocumento: '',
  numeroDocumento: '',
};

const FORM_VALIDATION = Yup.object().shape({
  name: Yup.string().required('Obligatorio'),
  lastName: Yup.string().required('Obligatorio'),
  email: Yup.string().email('El correo no es válido').required('Obligatorio'),
  password: Yup.string().required('Obligatorio'),
  telefono: Yup.string().required('Obligatorio'),
  fechaNac: Yup.string().required('Obligatorio'),
  tipoDocumento: Yup.string().required('Obligatorio'),
  numeroDocumento: Yup.string().required('Obligatorio'),
});

function NewUser() {
  let navigate = useNavigate();

  const handleCancel = () => navigate('/usuarios');

  const handleSubmit = async (values) => {
	console.log(values)
    const response = await fetch('/api/registro', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(values)
    });

    if (!(response.status === 400)) return alert('No se han podido cargar los datos');

    const data = await response.json();

    navigate('/usuarios');
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
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                  <LockOutlinedIcon />
                </Avatar>
                <Grid item container justifyContent="center">
                  <Typography variant="h5">Bienvenido!</Typography>
                </Grid>
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
                    <Grid item container xs={12} justifyContent="center">
                      <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                        Ingrese sus credenciales para continuar
                      </Typography>
                    </Grid>
                    <Grid item xs={4}>
                      <TextField name="name" label="Nombre" autoFocus />
                    </Grid>
                    <Grid item xs={4}>
                      <TextField name="lastName" label="Apellido" />
                    </Grid>
                    <Grid item xs={4}>
                      <TextField
                        name="fechaNac"
                        label="Fecha de nacimiento"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="email"
                        label="Email"
                        autoComplete="email"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="password"
                        label="Contraseña"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                      />
                    </Grid>
                    <Grid item xs={4}>
                      <TextField
                        name="telefono"
                        label="Telefono"
                        type="tel"
                      />
                    </Grid>
                    <Grid item xs={3}>
                      <TextField
                        name="tipoDocumento"
                        label="Tipo de Documento"
                      />
                    </Grid>
                    <Grid item xs={5}>
                      <TextField
                        name="numeroDocumento"
                        label="Numero de documento"
                      />
                    </Grid>
                    <Grid container item justifyContent="flex-end" spacing={2}>
                      <Grid item xs={2}>
                        <Button variant="outlined" onClick={handleCancel}>
                          Cancelar
                        </Button>
                      </Grid>
                      <Grid item xs={2}>
                        <Button type="submit" value="submit">
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

export default NewUser;
