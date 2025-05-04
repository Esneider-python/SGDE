# SGDE - Sistema Gestor de Elementos

Este proyecto es parte de la evidencia **GA7-220501096-AA2-EV02** del componente formativo: **Construcción de aplicaciones con JAVA**. 
El sistema permite gestionar entidades del sistema educativo como elementos,usuarios,roles, colegio,sede,bloque,piso,aulas, reporte, informe,  aulas,elementos tecnológicos y mobiliarios, elementos_eliminados, historial_movimientos.


## 📌 Objetivo

Desarrollar un módulos web utilizando **Java con Servlets y JSP**, conectado a una base de datos MySQL, que permita operaciones CRUD (crear, consultar, actualizar y eliminar) sobre las entidades del sistema.

## 🧰 Tecnologías utilizadas

- Java 24
- Apache NetBeans IDE 25
- Apache Tomcat 9.x
- MySQL
- JDBC
- JSP (JavaServer Pages)
- HTML/CSS/JavaScript
- Maven
- Git/GitHub

## 📂 Estructura del proyecto
SGDE/
├── src/
│ └── main/
│ ├── java/ (Modelos, DAOs, Servlets)
│ └── webapp/
│ ├── Vistas/ (JSP)
│ ├── css/
│ └── js/
├── pom.xml
├── README.md


## Funcionalidades implementadas:
- [x] Inicio de sesion
- [x] Registro de bloques, pisos, sedes, colegios,aulas, elementos tecnológicos y mobiliarios.
- [x] Vista de registros en la base de datos  
- [x] Actualización y eliminación de datos
- [x] Movimiento de elementos entre aulas
- [x] Reporte de elementos y visualizacion  de historial
- [x] Cambio de identificador de elementos
- [x] Generar informes
- [x] historial de eliminaciones de elementos


## 🔗 Conexión a la base de datos

- Base de datos: `databaselapaz`
- Conexión configurada en los DAOs a través de JDBC
- Archivo de configuración `DBConnection.java`

## Requisitos para ejecutar el proyecto
Tener instalado:

Java JDK 8+
NetBeans con soporte Maven
Apache Tomcat configurado en el IDE
MySQL Server

## Repositorio 

## Autor
Nombre Jose Esneider Ortega Camargo
Ficha: 2910881
Evidencia: GA7-220501096-AA2-EV02
