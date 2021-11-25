
## Integrantes
- Katia Micaela Adorno Ríos 
- Lia Fiorella Cano Forcadell 
- Lucas Iván Cubilla Fleitas 
- Gabriela Jazmín Ojeda Areco
- Noelia Yamila Jara Jara 
- Edith Adriana Avalos Rodas 

---

#### Dependencias
- Java 11
- Spring Boot
- Spring Boot Starter Web
- Spring Boot Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Mail
- Spring Boot Starter Tomcat
- Spring Security
- Project Lombok
- Maven
- PostgreSQL

---

Esta es la documentación del sistema de reservas, el mismo se encuentra disponible en siguiente [repositorio](https://github.com/LICF01/proyecto_sistema_de_reservas.git)

En este README, se encuentra disponible parte de la documentación, se puede ver la versión completa en el siguiente link:
[Documentación de la API del Sistema de Reservas](https://documenter.getpostman.com/view/17729901/UV5agbnL)

Se debe crear la base de datos que se va a utilizar manualmente en PostgresSQL, preferentemente llamar a la base de datos "hotel_booking" para mayor fluidez en su ejecución. ***Se debe de cambiar también la contraseña que se encuentra definida en properties, siendo su acceso:*** `/src/main/resources/static/application.properties`

Ejecute la clase principal `Application` siguiendo esta [guía de la documentación oficial de Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-running-your-application.html)

Los siguientes endpoints que son capaces de recibir requests son las siguientes
- `/api/registro`
- `/api/usuarios`
- `/api/habitacion`

Ademas, se implementó una muestra de login/logout a través de un formulario web con validaciones de los campos

Puede ser probado en:
- `/login`
- `/logout`

Los siguientes usuarios estan predefinidos:

| Usuario            | Contraseña |
|--------------------|------------|
| usuario1@gmail.com | user1      |
| usuario2@gmail.com | user2      |

Se puede probar un usuario personalizado enviando los datos correspondientes en formato JSON a `/registro`

## Registro
Al momento del registro el servicio devuelve un token generado para la confirmación y habilitación de la cuenta

Se puede confirmar el token a traves del correo que es enviado al email utilizado durante el registro (Vea la sección Configuracion del servicio de email)
o bien enviando un `GET`con el token generado al endpoint de confirmación

- `/api/registro/confirm?token={TOKEN GENERADO}`

Ejemplo:
- `/api/registro/confirm?token=8aa97f0b-8fa7-456b-aea6-d130d769eeaa`

No se podrá utilizar la cuenta mientras no se realice la confirmación

## Configuración del servicio de Email
Para utilizar esta funcionalidad debe especificar las propiedades del servidor de correo en el archivo application.properties

El servicio esta configurado para utilizar el servidor SMTP de Gmail, solo debe ingresar el usuario y la contraseña de aplicacion
en los campos `spring.mail.username=` y `spring.mail.password=` respectivamente como se muestra a continuación:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=USUARIO DEL SERVIDOR SMTP
spring.mail.password=CONTRASEÑA DEL SERVIDOR SMTP
```
Tenga en cuenta que la contraseña no debe ser una contraseña normal de la cuenta de Gmail, sino una contraseña de aplicación 
generada para nuestra cuenta de Google. Siga este [enlace](https://support.google.com/accounts/answer/185833) para ver los detalles y generar su contraseña de la aplicación de Google.

Si cuenta con [Node.js](http://nodejs.org/) y [npm](https://npmjs.org/) puede utilizar [MailDev](https://maildev.github.io/maildev/) para pruebas locales, para el mismo solo debe cambiar el host y el puerto

Ejemplo:
```
spring.mail.host=localhost
spring.mail.port=1025
```
