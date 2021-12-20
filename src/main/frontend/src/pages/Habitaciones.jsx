import * as React from 'react';
import Box from '@mui/material/Box';
import { Container, Typography } from '@mui/material';
import MaterialTable, { MTableToolbar } from '@material-table/core';
import NewUser from './NewUser';
import { Button } from "@mui/material";
import { useNavigate } from 'react-router-dom';


const columns = [
    { field: 'codHabitacion', title: 'ID' },
    { field: 'nombre', title: 'Nombre' },
    {
        field: 'cantPersonas',
        title: 'Cantidad Personas',
    },
    { field: 'tipoHabitacion', title: 'Tipo Habitacion' },
    { field: 'cocinaSN', title: 'Cocina S/N' },
    { field: 'tvHabitacion', title: 'TV S/N' },
    { field: 'cocinaSN', title: 'Cocina S/N' },
    { field: 'camaExtraSN', title: 'Cama Extra S/N' },
    { field: 'precioAdulto', title: 'Precio Adulto' },
    { field: 'precioNinho', title: 'Precio Niño' },
    { field: 'precioMinimo', title: 'Precio Minimo' },
];

const Habitaciones = () => {
    let navigate = useNavigate();
    const [habitaciones, setHabitaciones] = React.useState([]);

    const getData = async () => {
        const response = await fetch('/api/habitacion', {
            method: 'GET',
        });

        console.log(response);

        const data = await response.json();

        console.log(data);

        setHabitaciones(data);
    };

    React.useEffect(() => {
        getData();
    }, []);

    const handleClick = () =>
        navigate('/nuevousuario')

    return (
        <Box component="main" sx={{ width: '100%', paddingTop: 10 }}>
            <Container maxWidth="xl">
                <MaterialTable
                    columns={columns}
                    data={habitaciones}
                    title="Lista de habitaciones"
                    actions={[
                        {
                            icon: 'edit',
                            tooltip: 'Editar habitacion',
                            onClick: (event, rowData) => {
                                // Do save operation
                            },
                        },
                        {
                            icon: 'delete',
                            tooltip: 'Eliminar habitacion',
                            onClick: (event, rowData) => {
                                // Do save operation
                            },
                        },
                    ]}
                    options={{ actionsColumnIndex: -1 }}
                    localization={{
                        header: {
                            actions: 'Acciones',
                        },
                    }}
                    components={{
                        Toolbar: (props) => (
                            <Box
                                sx={{ display: 'flex', alignItems: 'center', paddingRight: 3 }}
                            >
                                <Box sx={{ flexGrow: 1, paddingRight: 5 }}>
                                    <MTableToolbar {...props} />
                                </Box>
                                <Button variant="outlined" onClick={handleClick}>Añadir Habitacion</Button>
                            </Box>
                        ),
                    }}
                />
            </Container>
        </Box>
    );
};

export default Habitaciones;