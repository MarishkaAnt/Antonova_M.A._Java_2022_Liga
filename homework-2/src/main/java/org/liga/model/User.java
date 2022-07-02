package org.liga.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
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

    public String toString() {
        return    this.getId() + ". "
                + this.getFirstName() + " "
                + this.getLastName();
    }

}
