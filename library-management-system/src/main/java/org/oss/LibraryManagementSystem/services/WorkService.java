package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.WorkPayload;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Work;
import org.springframework.data.domain.Page;

public interface WorkService {

    Page<Work> getAllWorks(String keyword, String categoryName, int page, int size, String[] sort);

    void deleteWorkById(Integer id);

    Work createWork(WorkPayload workPayload);

    Work editWork(Integer id, WorkPayload workPayload);

    Page<Book> getBooksByWorkId(Integer workId, String keyword, String statusName, int page, int size);

}
