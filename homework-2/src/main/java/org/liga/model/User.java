package org.liga.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    Integer id;
    String firstName;
    String lastName;

    public String toString() {
        return    this.getId() + ". "
                + this.getFirstName() + " "
                + this.getLastName();
    }

}
