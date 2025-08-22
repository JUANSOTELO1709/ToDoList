package com.todolist.model;

public enum TaskStatus {
    PENDIENTE,
    COMPLETADA;

    // Convierte un String a TaskStatus, ignorando mayúsculas/minúsculas Para evitar errores en la base de datos
    public static TaskStatus fromString(String value) {
        if (value == null) return null;
        try {
            return TaskStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Si no encuentra, intenta con mapeo manual para valores conocidos
            if ("pendiente".equalsIgnoreCase(value)) return PENDIENTE;
            if ("completada".equalsIgnoreCase(value)) return COMPLETADA;
            throw new IllegalArgumentException("Valor no válido para TaskStatus: " + value);
        }
    }
}