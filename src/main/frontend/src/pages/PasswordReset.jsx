import React from "react";
import { Formik, Form } from "formik";
import axios from "axios";
import * as Yup from "yup";
import { Container, Grid, Paper, Typography } from "@mui/material";
import { Box } from "@mui/system";
import TextField from "../components/FormUI/TextFieldWrapper";
import Button from "../components/FormUI/ButtonWrapper";
import Link from "../components/FormUI/LinkWrapper";


const baseURL = "http://localhost:8080/perform_login";

const resetPassword = (user) => {
  axios.post(baseURL, user).then((response) => {
    console.log(response);
  });
};


const INITIAL_FORM_STATE = {
  email: "",
};

const FORM_VALIDATION = Yup.object().shape({
  email: Yup.string()
    .email("El correo no es válido")
    .required("Obligatorio"),
});

const PasswordReset = () => {

  return (
    <Container component="div" maxWidth="sm">
      <Grid
        container
        direction="column"
        alignItems="center"
        justifyContent="center"
        marginTop={-10}
        style={{ minHeight: "100vh" }}
      >
        <Grid item xs={12}>
          <Paper variant="outlined" elevation={0}>
            <Box p={5}>
              <Grid item container justifyContent="center" xs={12}>
                <Grid item container justifyContent="center" xs={12}>
                  <Typography  variant="h3">Haz olvidado tu contraseña?</Typography>
                </Grid>
              </Grid>
              <Formik
                initialValues={{
                  ...INITIAL_FORM_STATE,
                }}
                validationSchema={FORM_VALIDATION}
                onSubmit={(values) => {
                  resetPassword(values);
                }}
              >
                <Form>
                  <Grid container spacing={3}>
                    <Grid item container xs={12}>
                      <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
						  Ingresa tu correo electrónico.
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
                      <Button>Resetear contraseña</Button>
                    </Grid>
                      <Grid container item justifyContent="center">
                      <Grid item justifyContent="center">
                        <Link>Regresear al login</Link>
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

export default PasswordReset
