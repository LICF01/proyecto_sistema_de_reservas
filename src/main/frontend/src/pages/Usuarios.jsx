import * as React from 'react';
import Box from '@mui/material/Box';
import { Container, Typography } from '@mui/material';
import MaterialTable, { MTableToolbar } from '@material-table/core';
import NewUser from './NewUser';
import { Button } from "@mui/material";
import { useNavigate } from 'react-router-dom';


const columns = [
  { field: 'id', title: 'ID' },
  { field: 'nombre', title: 'Nombre' },
  {
    field: 'apellido',
    title: 'Apellido',
  },
  { field: 'enabled', title: 'Habilitado' },
  { field: 'fechaNac', title: 'Fecha de Nacimiento' },
  { field: 'locked', title: 'Bloqueado' },
  { field: 'mail', title: 'Email' },
  { field: 'nroDocumento', title: 'Documento' },
  // { field: 'roles', title: 'Tipo de Usuario'},
  { field: 'telefono', title: 'Contacto' },
  { field: 'tipoDocumento', title: 'Tipo Documento' },
];
//
// const columns = [
// 	{title: 'Artista', field: 'artista'},
// 	{title: 'Pais', field: 'pais'}
// ]
// const data = [
// 	{nombre: 'asdfasdfa', apellido: 'asdfasdf'}
// ]

// const rows = [
//   { id: 1, lastName: 'Snow', firstName: 'Jon', age: 35 },
//   { id: 2, lastName: 'Lannister', firstName: 'Cersei', age: 42 },
//   { id: 3, lastName: 'Lannister', firstName: 'Jaime', age: 45 },
//   { id: 4, lastName: 'Stark', firstName: 'Arya', age: 16 },
//   { id: 5, lastName: 'Targaryen', firstName: 'Daenerys', age: null },
//   { id: 6, lastName: 'Melisandre', firstName: null, age: 150 },
//   { id: 7, lastName: 'Clifford', firstName: 'Ferrara', age: 44 },
//   { id: 8, lastName: 'Frances', firstName: 'Rossini', age: 36 },
//   { id: 9, lastName: 'Roxie', firstName: 'Harvey', age: 65 },
// ];

const Usuarios = () => {
  let navigate = useNavigate();
  const [users, setUsers] = React.useState([]);

  const getData = async () => {
    const response = await fetch('/api/usuario/usuarios', {
      method: 'GET',
    });

    console.log(response);

    const data = await response.json();

    console.log(data);

    setUsers(data);
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
          data={users}
          title="Lista de usuarios"
          actions={[
            {
              icon: 'edit',
              tooltip: 'Editar usuario',
              onClick: (event, rowData) => {
                // Do save operation
              },
            },
            {
              icon: 'delete',
              tooltip: 'Eliminar usuario',
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
                <Button variant="outlined" onClick={handleClick}>Añadir Usuario</Button>
              </Box>
            ),
          }}
        />
      </Container>
    </Box>
  );
};

export default Usuarios;
