package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
