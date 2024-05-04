package com.npt.anis.ANIS.department.service;

import com.npt.anis.ANIS.department.dto.DepCreateDTO;
import com.npt.anis.ANIS.department.dto.DepartmentDTO;
import com.npt.anis.ANIS.department.entity.Department;

import java.util.List;

public interface DepService {
    DepartmentDTO getDepartmentByIndex(Long depIndex);
    DepartmentDTO getDepartmentByName(String depName);
    List<DepartmentDTO> getDepartments();
    DepartmentDTO addDepartment(DepCreateDTO departmentDTO);
    DepartmentDTO updateDepartment(Long depIndex, DepCreateDTO departmentDTO);
    void deleteDepartment(Long depIndex);
}
