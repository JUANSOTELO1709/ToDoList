package com.todolist.service;

import com.todolist.model.Task;
import com.todolist.model.TaskStatus;
import com.todolist.model.User;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    // Devuelve todas las tareas asociadas a un usuario

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Task createTask(Task task) {
    // Crea y guarda una nueva tarea en la base de datos
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long taskId) {
    // Busca una tarea por su ID
        return taskRepository.findById(taskId);
    }

    public Task updateTask(Task task) {
    // Actualiza los datos de una tarea existente
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
    // Elimina una tarea por su ID
        taskRepository.deleteById(taskId);
    }

    public void toggleTaskStatus(Long taskId) {
    // Cambia el estado de una tarea entre PENDIENTE y COMPLETADA
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(task.getStatus() == TaskStatus.PENDIENTE ?
                    TaskStatus.COMPLETADA : TaskStatus.PENDIENTE);
            taskRepository.save(task);
        }
    }

    public List<Task> getUserTasksByStatus(User user, TaskStatus status) {
    // Devuelve las tareas de un usuario filtradas por estado
        return taskRepository.findByUserAndStatus(user, status);
    }
}