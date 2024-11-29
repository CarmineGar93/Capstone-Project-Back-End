package CarmineGargiulo.Capstone_Project_Back_End.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "recipe_id")
    private long recipeId;
    @Column(nullable = false)
    private long reference;
    @Column(nullable = false, name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredientList;

    public Recipe(long reference, String imageUrl, String name) {
        this.reference = reference;
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
