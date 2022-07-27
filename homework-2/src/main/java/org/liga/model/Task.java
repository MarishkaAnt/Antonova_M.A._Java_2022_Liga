package org.liga.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.liga.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс описывающий сущность - Задача
 */

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task extends Identifiable {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @FutureOrPresent
    private LocalDate deadline;

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.getUser() + " - "
                + this.getId() + ". "
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
                getUser().equals(task.getUser()) &&
                getStatus() == task.getStatus() &&
                getDeadline().equals(task.getDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(),
                getUser(), getStatus(), getDeadline());
    }
}
