package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.WeeklyPlanDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.DailyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Meal;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.entities.WeeklyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.enums.MealType;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.WeeklyPlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeeklyPlansService {
    @Autowired
    private WeeklyPlansRepository weeklyPlansRepository;

    @Autowired
    private DailyPlansService dailyPlansService;

    @Autowired
    private MealsService mealsService;

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
        WeeklyPlan savedPlan = weeklyPlansRepository.save(newPlan);
        List<DailyPlan> planList = new ArrayList<>();
        for (short i = 0; i < 7; i++) {
            planList.add(new DailyPlan(i, savedPlan));
        }
        List<DailyPlan> planListSaved = dailyPlansService.saveManyPlans(planList);
        List<Meal> meals = new ArrayList<>();
        List<MealType> mealTypes = List.of(MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER);
        planListSaved.forEach(dailyPlan -> {
            mealTypes.forEach(mealType -> meals.add(new Meal(mealType, dailyPlan)));
        });
        mealsService.saveManyMeals(meals);
        return savedPlan;
    }


}
