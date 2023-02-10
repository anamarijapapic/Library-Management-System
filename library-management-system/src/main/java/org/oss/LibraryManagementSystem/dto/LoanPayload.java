package org.oss.LibraryManagementSystem.dto;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoanPayload {

    private Integer id;

    private Integer member;

    private Integer librarian;

    private Date dateIssued;

    private Date dateReturned;

    private Integer book;

}
