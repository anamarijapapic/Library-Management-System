package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAll (Pageable pageable);

    Category findByName (String name);

    Page<Category> findByNameContaining (String name, Pageable pageable);

}
