package com.prueba.tareas.dto;

import java.util.Date;

public record TaskDto(Long id, String title, String description, Date expirationDate, boolean isComplete) {
}
