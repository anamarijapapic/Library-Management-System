package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAll(Pageable pageable);

    Category findByNameIgnoreCase(String name);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT * FROM category ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Category getOneRandom();

}
