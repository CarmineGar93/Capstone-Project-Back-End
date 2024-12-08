package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.WeeklyPlanDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.entities.WeeklyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.services.WeeklyPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/plans")
public class WeeklyPlansController {
    @Autowired
    private WeeklyPlansService weeklyPlansService;

    @GetMapping("/myplans")
    public Page<WeeklyPlan> getLoggedPlans(@AuthenticationPrincipal User logged,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "startDate") String sortBy) {
        return weeklyPlansService.getAllUserWeeklyPlans(logged, page, size, sortBy);
    }

    @GetMapping("/myplans/active")
    public WeeklyPlan getActiveWeeklyPlan(@AuthenticationPrincipal User logged) {
        return weeklyPlansService.getActivePlan(logged);
    }

    @PatchMapping("/myplans")
    public void updatePlansStatus(@AuthenticationPrincipal User logged) {
        weeklyPlansService.updatePlansStatus(logged);
    }

    @PostMapping("/myplans")
    public WeeklyPlan savePlan(@AuthenticationPrincipal User logged, @RequestBody @Validated WeeklyPlanDTO body,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return weeklyPlansService.saveWeeklyPlan(logged, body);
    }
}
