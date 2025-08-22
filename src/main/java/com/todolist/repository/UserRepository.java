package com.todolist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todolist.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Busca un usuario por su nombre de usuario (para validación y login)
    Optional<User> findByUsername(String username);

    // Busca un usuario por nombre de usuario y contraseña (usado en el login)
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    Optional<User> login(@Param("username") String username, @Param("password") String password);

    // Verifica si existe un usuario con el nombre de usuario dado
    boolean existsByUsername(String username);

    // Devuelve la cantidad de tareas completadas para el usuario especificado
    @Query("SELECT COUNT(t) FROM User u JOIN u.tasks t WHERE u.id = :userId AND t.status = 'COMPLETADA'")
    long countCompletedTasksByUserId(@Param("userId") Long userId);

    // Devuelve la cantidad de tareas pendientes para el usuario especificado
    @Query("SELECT COUNT(t) FROM User u JOIN u.tasks t WHERE u.id = :userId AND t.status = 'PENDIENTE'")
    long countPendingTasksByUserId(@Param("userId") Long userId);
}