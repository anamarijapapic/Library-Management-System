package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "name shouldn't be null")
    private String name;

    public Category(String name) {
        this.name = name;
    }

}
