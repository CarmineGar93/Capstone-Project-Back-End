package CarmineGargiulo.Capstone_Project_Back_End.entities;

import CarmineGargiulo.Capstone_Project_Back_End.enums.MealType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "meals")
@JsonIgnoreProperties({"dailyPlan"})
public class Meal {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "meal_id")
    private long mealId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MealType type;
    @ManyToOne
    @JoinColumn(name = "daily_plan_id", nullable = false)
    private DailyPlan dailyPlan;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @Setter(AccessLevel.NONE)
    private Recipe recipe;

    public Meal(MealType type, DailyPlan dailyPlan) {
        this.type = type;
        this.dailyPlan = dailyPlan;
    }

    public void addRecipe(Recipe recipe) {
        this.dailyPlan.setTotCalories(this.dailyPlan.getTotCalories() + recipe.getCalories());
        this.dailyPlan.getWeeklyPlan().calculateAvgDailyCalories();
        this.recipe = recipe;
    }

    public void removeRecipe() {
        this.dailyPlan.setTotCalories(this.dailyPlan.getTotCalories() - this.recipe.getCalories());
        this.dailyPlan.getWeeklyPlan().calculateAvgDailyCalories();
        this.recipe = null;
    }
}
