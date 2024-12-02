package CarmineGargiulo.Capstone_Project_Back_End.dto;


import jakarta.validation.constraints.NotNull;

public record WeeklyPlanDTO(@NotNull(message = "Week control must be provided")
                            boolean thisWeek) {
}
