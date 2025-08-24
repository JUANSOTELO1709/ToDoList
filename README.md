

<img width="1851" height="877" alt="Image" src="https://github.com/user-attachments/assets/9c645213-cb02-4c06-827e-291d292ae2df" />

# Configuración de la Base de Datos para ToDoList

Este proyecto utiliza MariaDB como sistema de gestión de base de datos. Sigue estos pasos para configurar la base de datos y conectar la aplicación:

## 1. Instalar MariaDB
Si no tienes MariaDB instalado, descárgalo e instálalo desde [https://mariadb.org/download/](https://mariadb.org/download/).

## 2. Crear la base de datos
Abre tu cliente de MariaDB y ejecuta:

```sql
CREATE DATABASE todo_db;
```

## 3. Crear las tablas
Puedes usar el archivo `src/main/resources/todo_example.sql` incluido en el proyecto. Ejecuta su contenido para crear las tablas y datos de ejemplo:

```sql
USE todo_db;
SOURCE src/main/resources/todo_example.sql;
```

O copia y pega el contenido del archivo en tu cliente SQL.

## 4. Configurar credenciales en la aplicación
Edita el archivo `src/main/resources/application.properties` para que los datos de conexión coincidan con tu instalación:

```
spring.datasource.url=jdbc:mariadb://localhost:3306/todo_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

Reemplaza `TU_USUARIO` y `TU_CONTRASEÑA` por los datos de tu instalación.

## 5. Verificar conexión
Compila y ejecuta la aplicación. Si la configuración es correcta, la aplicación se conectará automáticamente y podrás usar el sistema de tareas.

---
Desarrollado por juan david sotelo.