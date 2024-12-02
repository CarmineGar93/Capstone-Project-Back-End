package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.DailyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.DailyPlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyPlansService {
    @Autowired
    private DailyPlansRepository dailyPlansRepository;

    public List<DailyPlan> saveManyPlans(List<DailyPlan> planList) {
        return dailyPlansRepository.saveAll(planList);
    }
}
