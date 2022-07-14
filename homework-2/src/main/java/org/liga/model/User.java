package org.liga.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table//(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String firstName;
    String lastName;
    @OneToMany
    //@JoinTable(name = "tasks")
    Set<Task> tasks;

    public String toString() {
        return    this.getId() + ". "
                + this.getFirstName() + " "
                + this.getLastName();
    }

}
