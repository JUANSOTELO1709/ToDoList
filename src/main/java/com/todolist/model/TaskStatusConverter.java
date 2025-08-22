// Este converter permite que el enum TaskStatus se almacene y recupere correctamente desde la base de datos,
// aceptando valores en mayúsculas y minúsculas.
package com.todolist.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, String> {
    @Override
    public String convertToDatabaseColumn(TaskStatus status) {
        return status == null ? null : status.name();
    }

    @Override
    public TaskStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return TaskStatus.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            if ("pendiente".equalsIgnoreCase(dbData)) return TaskStatus.PENDIENTE;
            if ("completada".equalsIgnoreCase(dbData)) return TaskStatus.COMPLETADA;
            throw new IllegalArgumentException("Valor no válido para TaskStatus: " + dbData);
        }
    }
}