package org.liga.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    Integer id;
    String name;
    String description;
    Integer userId;
    String status;
    LocalDate deadline;

    public String toString() {
        return this.getId() + ". "
                + this.getName() + ": "
                + this.getDescription() + " - ("
                + this.getStatus() + ") - "
                + this.getDeadline();
    }

}
