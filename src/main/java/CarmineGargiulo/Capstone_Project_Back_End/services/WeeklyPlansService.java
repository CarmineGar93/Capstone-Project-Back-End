package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.WeeklyPlanDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.entities.WeeklyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.WeeklyPlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeeklyPlansService {
    @Autowired
    private WeeklyPlansRepository weeklyPlansRepository;

    public Page<WeeklyPlan> getAllUserWeeklyPlans(User logged, int page, int size, String sort) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return weeklyPlansRepository.findByUser(logged, pageable);
    }

    public WeeklyPlan saveWeeklyPlan(User logged, WeeklyPlanDTO body) {
        LocalDate date = body.thisWeek() ? LocalDate.now() : LocalDate.now().plusDays(7);
        LocalDate start = date.minusDays((date.getDayOfWeek().getValue()) - 1);
        LocalDate end = start.plusDays(6);
        WeeklyPlan newPlan = new WeeklyPlan(start, end, logged);
        if (weeklyPlansRepository.existsByUserAndStatus(logged, newPlan.getStatus()))
            throw new BadRequestException("There is already a plan for " + (body.thisWeek() ? "this week" : "next " +
                    "week"));
        return weeklyPlansRepository.save(newPlan);
    }


}
