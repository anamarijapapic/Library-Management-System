package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title")
    @NotNull(message = "title shouldn't be null")
    private String title;

    @Column(name = "description")
    @NotNull(message = "description shouldn't be null")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "work_author",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "work_category",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    public Work(String title, String description, Set<Author> authors, Set<Category> categories) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.categories = categories;
    }

}
