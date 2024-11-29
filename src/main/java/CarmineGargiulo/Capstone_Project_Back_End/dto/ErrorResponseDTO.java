package CarmineGargiulo.Capstone_Project_Back_End.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String message, LocalDateTime timestamp) {
}
