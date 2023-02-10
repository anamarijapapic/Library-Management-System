package org.oss.LibraryManagementSystem.dto;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookPayload {

    private Integer id;

    private Integer work;

    private String publisherName;

    private Date yearOfPublishing;

    private String isbn;

    private String bookStatus;

}
