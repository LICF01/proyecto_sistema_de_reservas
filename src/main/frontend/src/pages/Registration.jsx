import React from 'react';
import { Formik, Form } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';
import { Container, Grid, Paper, Typography, Divider } from '@mui/material';
import { Box } from '@mui/system';
import TextField from '../components/FormUI/TextFieldWrapper';
import Button from '../components/FormUI/ButtonWrapper';
import Link from '../components/FormUI/LinkWrapper';
import Avatar from '@mui/material/Avatar';
import PersonIcon from '@mui/icons-material/Person';
import { useNavigate } from 'react-router-dom';

const baseURL = 'http://localhost:8080/perform_login';

const registerUser = (user) => {
  axios.post(baseURL, user).then((response) => {
    console.log(response);
  });
};

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

function Registration() {
  let navigate = useNavigate();

  const handleLogin = () => navigate('/mylogin');

  const handleSubmit = async (values) => {
    console.log(values);
    const response = await fetch('/api/registro', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(values),
    });

    if (response.status !== 200)
      return alert(response.message);

    const data = await response.json();

    navigate('/passwords/mailsent');
  };
  return (
    <Container component="div" maxWidth="md">
      <Grid
        container
        direction="column"
        alignItems="center"
        justifyContent="center"
        marginTop={-5}
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
                        Formulario de registro
                      </Typography>
                      <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                        Complete los siguientes campos
                      </Typography>
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="name" label="Nombre" autoFocus />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="lastName" label="Apellido" />
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
                    <Grid item xs={6}>
                      <TextField name="telefono" label="Telefono" type="tel" />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField name="fechaNac" label="Fecha de nacimiento" />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="tipoDocumento"
                        label="Tipo de Documento"
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <TextField
                        name="numeroDocumento"
                        label="Numero de documento"
                      />
                    </Grid>
                    <Grid container item justifyContent="center" spacing={2}>
                      <Grid item xs={6}>
                        <Button type="submit" value="submit">
                          Registrar
                        </Button>
                      </Grid>
                    </Grid>
                    <Grid
                      container
                      item
                      justifyContent="center"
                      alignItems="center"
                      spacing={2}
                    >
                      <Grid item>
                        <Typography variant="caption">
                          Ya posee una cuenta?
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Link url="/mylogin">Ingrese aquí</Link>
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
