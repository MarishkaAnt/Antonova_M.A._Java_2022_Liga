package org.liga.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    Integer id;
    String name;
    String description;
    Integer userId;
    LocalDate deadline;

    public String toString() {
        return    this.getId() + ". "
                + this.getName() + ": "
                + this.getDescription() + " - "
                + this.getDeadline() + " - "
                + this.getUserId() + ", deadline=";
    }


}
