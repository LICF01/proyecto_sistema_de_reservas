import React from "react";
import { Formik, Form } from "formik";
import * as Yup from "yup";
import { Container, Grid, Paper, Typography } from "@mui/material";
import { Box } from "@mui/system";
import TextField from "../components/FormUI/TextFieldWrapper";
import Button from "../components/FormUI/ButtonWrapper";
import Link from "../components/FormUI/LinkWrapper";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";

const INITIAL_FORM_STATE = {
  email: "",
  password: "",
};

const FORM_VALIDATION = Yup.object().shape({
  email: Yup.string().email("El correo no es válido").required("Obligatorio"),
  password: Yup.string().required("Obligatorio"),
});

function Login() {
  return (
    <Container component="div" maxWidth="xs">
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
                <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                  <LockOutlinedIcon />
                </Avatar>
                <Grid item container justifyContent="center" xs={12}>
                  <Typography>Bienvenido!</Typography>
                </Grid>
              </Grid>
              <Formik
                initialValues={{
                  ...INITIAL_FORM_STATE,
                }}
                validationSchema={FORM_VALIDATION}
                onSubmit={(values) => {
                  console.log(values);
                }}
              >
                <Form>
                  <Grid container spacing={2}>
                    <Grid item container xs={12} justifyContent="center">
                      <Typography>
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
                    <Grid item xs={12}>
                      <Button>Ingresar</Button>
                    </Grid>

                    <Grid container item>
                      <Grid item xs>
                        <Link>Olvidó su contraseña?</Link>
                      </Grid>
                      <Grid item>
                        <Link>Registrarse</Link>
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
