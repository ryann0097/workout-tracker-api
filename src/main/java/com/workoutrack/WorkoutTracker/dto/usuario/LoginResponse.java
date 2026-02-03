package com.workoutrack.WorkoutTracker.dto.usuario;

public record LoginResponse(String token, java.util.UUID id, String username) {
}
