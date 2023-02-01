package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    Page<Work> findAll (Pageable pageable);

    Page<Work> findByTitleContaining (String title, Pageable pageable);
}
