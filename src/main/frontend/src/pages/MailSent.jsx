import React from "react";
import { Container, Grid, Paper, Typography } from "@mui/material";
import { Box } from "@mui/system";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';

const MailSent = () => {
	return (
    <Container component="div" maxWidth="md">
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
					<CheckCircleIcon color="success" sx={{fontSize: 80, paddingBottom: 2}}/>
                </Grid>
                <Grid item container justifyContent="center" xs={12}>
                  <Typography  variant="h4" align="center">
Se le ha enviado un correo electrónico para restablecer la contraseña.
									</Typography>
                </Grid>
              </Grid>
                  <Grid container spacing={3}>
                    <Grid item container xs={12} justifyContent="center">
                      <Typography variant="subtitle1" align="center" sx={{ marginTop: 2 }}>
Revise su cuenta de correo electrónico.
                      </Typography>
                    </Grid>
                  </Grid>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Container>
	)
}

export default MailSent
