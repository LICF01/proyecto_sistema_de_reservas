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
  apellido: Yup.string().required('Obligatorio'),
  telefono: Yup.string().required('Obligatorio'),
  tipoDocumento: Yup.string().required('Obligatorio'),
  nroDocumento: Yup.string().required('Obligatorio'),
});

function EditUser() {
  let navigate = useNavigate();
  const { state } = useLocation();
  console.log(state);

  const handleCancel = () => navigate('/usuarios');

  const INITIAL_FORM_STATE = {
    nombre: state.nombre,
    apellido: state.apellido,
    telefono: state.telefono,
    tipoDocumento: state.tipoDocumento,
    nroDocumento: state.nroDocumento,
  };

  const handleSubmit = (values) => {
    axios.put('/api/usuario/' + state.id, values).then((response) => {
      alert(response.data.message);
      navigate('/usuarios');
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
                        {state.nombre} {state.apellido}
                      </Typography>
                      <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                        Perfil de usuario
                      </Typography>
                    </Grid>
                    <Grid item xs={12}>
                      <TextField name="nombre" label="Nombre" autoFocus />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField name="apellido" label="Apellido" />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField name="telefono" label="Telefono" type="tel" />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                        name="tipoDocumento"
                        label="Tipo de Documento"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                        name="nroDocumento"
                        label="Numero de documento"
                      />
                    </Grid>
                    <Grid container item justifyContent="center" spacing={2}>
                      <Grid item xs={5}>
                        <Button type="submit" value="submit">
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

export default EditUser;
