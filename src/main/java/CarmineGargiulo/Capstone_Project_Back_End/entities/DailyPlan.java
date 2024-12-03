package CarmineGargiulo.Capstone_Project_Back_End.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "daily_plans")
@JsonIgnoreProperties({"weeklyPlan"})
public class DailyPlan {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "daily_plan_id")
    private long dailyPlanId;
    @Column(nullable = false)
    private short day;
    @Column(name = "tot_calories")
    private double totCalories;
    @ManyToOne
    @JoinColumn(name = "week_plan_id")
    private WeeklyPlan weeklyPlan;
    @OneToMany(mappedBy = "dailyPlan")
    @OrderBy("mealId")
    private List<Meal> meals;

    public DailyPlan(short day, WeeklyPlan weeklyPlan) {
        this.day = day;
        this.weeklyPlan = weeklyPlan;
        this.totCalories = 0;
    }
}
