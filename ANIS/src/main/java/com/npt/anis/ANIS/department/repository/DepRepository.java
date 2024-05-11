package com.npt.anis.ANIS.department.repository;

import com.npt.anis.ANIS.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepRepository extends JpaRepository<Department, Long> {
    Department findByDepName(String depName);
    Department findByDepIndex(Long depIndex);

    boolean existsByDepName(String depName);

}
