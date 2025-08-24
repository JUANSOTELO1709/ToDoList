-- Creación de la tabla de usuarios
                                             CREATE TABLE users (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 username VARCHAR(50) NOT NULL UNIQUE,
                                                 password VARCHAR(100) NOT NULL
                                             );

                                             -- Creación de la tabla de tareas
                                             CREATE TABLE tasks (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 title VARCHAR(100) NOT NULL,
                                                 description TEXT,
                                                 due_date DATE,
                                                 status ENUM('PENDIENTE', 'COMPLETADA') NOT NULL,
                                                 user_id BIGINT NOT NULL,
                                                 FOREIGN KEY (user_id) REFERENCES users(id)
                                             );

                                             -- Consultas de ejemplo

                                             -- Insertar un usuario
                                             INSERT INTO users (username, password) VALUES ('juan', '0608');

                                             -- Insertar una tarea
                                             INSERT INTO tasks (title, description, due_date, status, user_id)
                                             VALUES ('Entregar la prueba tecnica', 'Pasar la prueba tecnica para trabajar con IDL como desarrollador', '2025-08-25', 'PENDIENTE', 1);

                                             -- Consultar todas las tareas de un usuario
                                             SELECT * FROM tasks WHERE user_id = 1;

                                             -- Consultar tareas pendientes
                                             SELECT * FROM tasks WHERE status = 'PENDIENTE' WHERE user_id = 1;

                                             -- Consultar usuarios y cantidad de tareas
                                             SELECT u.username, COUNT(t.id) AS total_tareas
                                             FROM users u
                                             LEFT JOIN tasks t ON u.id = t.user_id
                                             GROUP BY u.id, u.username;

                                             SELECT * FROM tasks WHERE status ='PENDIENTE';
                                             SELECT * FROM tasks WHERE status ='COMPLETAS' AND due_date = (01,01,2025);

                                             SELECT *
                                             FROM tasks
                                             WHERE status = 'COMPLETAS'
                                               AND due_date BETWEEN '2025-01-01' AND '2025-01-31';


