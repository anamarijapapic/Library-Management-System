package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Integer> {
}
