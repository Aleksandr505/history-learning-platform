package ru.gladun.historylearningplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gladun.historylearningplatform.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
