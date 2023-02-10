package org.oss.LibraryManagementSystem.dto;

import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPayload {

    private Integer id;

    private String title;

    private String description;

    private Set<Integer> authors;

    private Set<Integer> categories;

}
