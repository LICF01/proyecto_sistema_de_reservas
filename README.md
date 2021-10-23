---
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
- Spring Boot Starter Tomcat
- Spring Security
- Project Lombok
- Maven

---
Esta es la documentación del sistema de reservas, el mismo se encuentra disponible en siguiente [repositorio](https://github.com/LICF01/proyecto_sistema_de_reservas.git)

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
