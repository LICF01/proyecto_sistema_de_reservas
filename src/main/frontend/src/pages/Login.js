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
  email: '',
  password: '',
};

const FORM_VALIDATION = Yup.object().shape({
  email: Yup.string().email('El correo no es válido').required('Obligatorio'),
  password: Yup.string().required('Obligatorio'),
});

function Login() {
  let navigate = useNavigate();

  const encodeFormData = (data) => {
    return Object.keys(data)

      .map(
        (key) => encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
      )

      .join('&');
  };

  const handleSubmit = async (values) => {
    const response =
    await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: encodeFormData(values)
    });

    if (!(response.status === 202)) return alert("Datos inválidos")

    const data = await response.json();

    navigate('/usuarios')
  };

  return (
    <Container component="div" maxWidth="xs">
      <Grid
        container
        direction="column"
        alignItems="center"
        justifyContent="center"
        marginTop={-10}
        style={{ minHeight: '100vh' }}
      >
        <Grid item xs={12}>
          <Paper variant="outlined" elevation={0}>
            <Box p={5}>
              <Grid item container justifyContent="center" xs={12}>
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                  <LockOutlinedIcon />
                </Avatar>
                <Grid item container justifyContent="center" xs={12}>
                  <Typography variant="h5">Bienvenido!</Typography>
                </Grid>
              </Grid>
              <Formik
                initialValues={{
                  ...INITIAL_FORM_STATE,
                }}
                validationSchema={FORM_VALIDATION}
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
                    <Grid item xs={12}>
                      <TextField
                        name="email"
                        label="Email"
                        autoComplete="email"
                        autoFocus
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                        name="password"
                        label="Contraseña"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                      />
                    </Grid>
                    <Grid container item>
                      <Grid item xs>
                        <Typography variant="caption">
                          Mantenme conectado
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Link>Olvidó su contraseña?</Link>
                      </Grid>
                    </Grid>
                    <Grid item xs={12}>
                      <Button type="submit" value="submit">
                        Ingresar
                      </Button>
                    </Grid>
                    <Grid container item alignItems="center">
                      <Grid item xs>
                        <Typography variant="caption">
                          No posee una cuenta?
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Button variant="outlined">Registrese</Button>
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

export default Login;
