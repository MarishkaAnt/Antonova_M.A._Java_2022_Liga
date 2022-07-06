package org.liga.model;

import lombok.*;
import org.liga.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String description;
    Integer userId;
    @Enumerated(EnumType.STRING)
    Status status;
    LocalDate deadline;

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.getId() + ". "
                + this.getName() + ": "
                + this.getDescription() + " - ("
                + this.getStatus() + ") - "
                + this.getDeadline().format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return getId().equals(task.getId()) &&
                getName().equals(task.getName()) &&
                getDescription().equals(task.getDescription()) &&
                getUserId().equals(task.getUserId()) &&
                getStatus() == task.getStatus() &&
                getDeadline().equals(task.getDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(),
                getUserId(), getStatus(), getDeadline());
    }
}
