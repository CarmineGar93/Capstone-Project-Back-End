package CarmineGargiulo.Capstone_Project_Back_End.entities;

import CarmineGargiulo.Capstone_Project_Back_End.enums.PlanStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "weekly_plans")
public class WeeklyPlan {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "weekly_plan_id")
    private long weeklyPlanId;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private PlanStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "weeklyPlan")
    private List<DailyPlan> dailyPlans;

    public WeeklyPlan(LocalDate startDate, LocalDate endDate, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        if(startDate.isBefore(LocalDate.now())) this.status = PlanStatus.ACTIVE;
        else this.status = PlanStatus.IN_PROGRAM;
    }
}
