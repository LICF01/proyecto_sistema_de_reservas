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
  password: "",
};

const FORM_VALIDATION = Yup.object().shape({
  password: Yup.string().required("Obligatorio"),
});
const EnterNewPassword = () => {
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
                  <Typography  variant="h4" sx={{marginBottom: 3}}>Ingresa tu nueva contraseña</Typography>
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
                    <Grid item xs={12}>
                      <TextField
                        name="password"
                        label="Contraseña"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                        name="confirmPassword"
                        label="Confirmación de contraseña"
                        type="password"
                        id="confirmPassword"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <Button sx={{marginTop: 2}}>Cambiar contraseña</Button>
                    </Grid>
                  </Grid>
                </Form>
              </Formik>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Container>
	)
}

export default EnterNewPassword
