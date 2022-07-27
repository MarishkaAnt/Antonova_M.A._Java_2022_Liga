package org.liga.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Класс описывающий сущность - Пользователь
 */

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends Identifiable {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @Email
    private String email;

    @NotBlank
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects;

    public String toString() {
        return this.getId() + ". "
                + this.getFirstName() + " "
                + this.getLastName();
    }

}
