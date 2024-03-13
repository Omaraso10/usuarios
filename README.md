# usuarios-apirest
## Proyecto de creación de Usuarios

## Detalle del proyecto

- Java 11
- SpringBoot 2.6.8
- Base de datos en Memoria (H2)
- Gestor de dependencias: Gradle
- Autenticación con JWT (spring-security-oauth2)
- Persistencia con JPA: Hibernate
- El script de creación de BD se encuentra en el archivo data.sql dentro del proyecto

## Pasos para ejecutar el proyecto

- Descargar el proyecto desde GitHub
- Levantar proyecto con Intellij IDEA
- Al iniciar la ejecución se creara la BD y sus tablas automaticamente, además algunos registros de prueba.

## Pasos para testear el proyecto

- Importar el proyecto "api-rest-usuarios.postman_collection.json" en la herramienta Postman
- Iniciar el proyecto springboot usuarios-apirest
- Ejecutar en Postman el endpoint (POST) JWT, el cual obtendra el token necesario para ejecutar los demás endpoint
- Ejecutar en postman los siguientes endpoints

  GET /usuarios all
  GET /usuario x uuid
  POST /crea usuario
  PUT /update usuario x uuid