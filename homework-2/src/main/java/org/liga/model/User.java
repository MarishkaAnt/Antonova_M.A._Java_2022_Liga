package org.liga.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @OneToMany
    Set<Task> tasks;

    public String toString() {
        return    this.getId() + ". "
                + this.getFirstName() + " "
                + this.getLastName();
    }

}
