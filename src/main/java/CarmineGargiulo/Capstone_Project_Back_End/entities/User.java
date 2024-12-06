package CarmineGargiulo.Capstone_Project_Back_End.entities;

import CarmineGargiulo.Capstone_Project_Back_End.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
@JsonIgnoreProperties({"accountNonLocked", "accountNonExpired", "credentialsNonExpired", "enabled", "authorities",
        "password", "weeklyPlans", "username", "favouriteRecipes"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id")
    private long userId;
    @Column(nullable = false)
    private String name, surname, email, password;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<WeeklyPlan> weeklyPlans;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"),
            name = "user_favourite_recipes")
    @Setter(AccessLevel.NONE)
    private List<Recipe> favouriteRecipes;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatarUrl = "https://ui-avatars.com/api/?name=" +
                name + "+" + surname;
        this.role = Role.USER;
        this.favouriteRecipes = new ArrayList<>();
    }

    public void addFavourite(Recipe recipe) {
        this.favouriteRecipes.add(recipe);
    }

    public boolean removeFavourite(long reference) {
        return this.favouriteRecipes.removeIf(recipe1 -> recipe1.getReference() == reference);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
