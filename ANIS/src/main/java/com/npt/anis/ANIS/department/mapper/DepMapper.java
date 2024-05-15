package com.npt.anis.ANIS.department.mapper;

import com.npt.anis.ANIS.department.dto.DepCreateDTO;
import com.npt.anis.ANIS.department.dto.DepartmentDTO;
import com.npt.anis.ANIS.department.entity.Department;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepMapper {
    DepartmentDTO toDTO(Department department);
    Department toEntity(DepCreateDTO depCreateDTO);
    Department toEntity(DepartmentDTO departmentDTO);
    List<DepartmentDTO> toDTOs(List<Department> departments);
    List<Department> toEntities(List<DepartmentDTO> departmentDTOs);
}
