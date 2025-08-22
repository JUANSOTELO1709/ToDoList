package com.todolist.repository;

import com.todolist.model.Task;
import com.todolist.model.TaskStatus;
import com.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Devuelve todas las tareas asociadas a un usuario
    List<Task> findByUser(User user);

    // Devuelve las tareas de un usuario filtradas por estado
    List<Task> findByUserAndStatus(User user, TaskStatus status);

    // Devuelve las tareas pendientes de un usuario ordenadas por fecha de vencimiento
    List<Task> findByUserAndStatusOrderByDueDateAsc(User user, TaskStatus status);

    // Devuelve las tareas pendientes cuyo vencimiento ya pasó
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.status = 'PENDIENTE' AND t.dueDate < CURRENT_DATE")
    List<Task> findOverdueTasksByUser(@Param("user") User user);

    // Devuelve la cantidad de tareas de un usuario según el estado
    long countByUserAndStatus(User user, TaskStatus status);

    // Actualiza el estado de una tarea específica de un usuario
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :taskId AND t.user.id = :userId")
    int updateTaskStatus(@Param("userId") Long userId, @Param("taskId") Long taskId, @Param("status") TaskStatus status);

    // Devuelve las tareas de un usuario dentro de un rango de fechas
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findByUserAndDueDateBetween(@Param("user") User user,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);

    // Devuelve las tareas de un usuario cuyo título contiene el texto indicado
    @Query("SELECT t FROM Task t WHERE t.user = :user AND LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Task> findByUserAndTitleContaining(@Param("user") User user, @Param("title") String title);
}