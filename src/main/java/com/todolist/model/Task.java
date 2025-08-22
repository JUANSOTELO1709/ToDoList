package com.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Convert(converter = TaskStatusConverter.class)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDIENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private User user;

    // Constructores: inicializan la tarea con o sin datos
    public Task() {}

    public Task(String title, String description, Date dueDate, User user) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.user = user;
    }

    // Métodos para acceder y modificar los atributos de la tarea
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // Métodos para cambiar el estado y mostrar información de la tarea
    public void toggleStatus() {
        this.status = (this.status == TaskStatus.PENDIENTE)
                ? TaskStatus.COMPLETADA
                : TaskStatus.PENDIENTE;
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETADA;
    }

    public boolean isPending() {
        return this.status == TaskStatus.PENDIENTE;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status=" + status + "}";
    }
}

