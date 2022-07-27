package org.liga.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Класс описывающий сущность - Проект
 */

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Project extends Identifiable {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;

    @ManyToMany
    @JoinTable(name = "projects_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<User> users;

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

}
