package com.npt.anis.ANIS.department.service;

import com.npt.anis.ANIS.department.dto.DepCreateDTO;
import com.npt.anis.ANIS.department.dto.DepartmentDTO;
import com.npt.anis.ANIS.department.entity.Department;
import com.npt.anis.ANIS.department.mapper.DepMapper;
import com.npt.anis.ANIS.department.repository.DepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DepServiceImpl implements DepService {
    private final DepRepository depRepository;
    private final DepMapper depMapper;
    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentByIndex(Long depIndex) {
        return depMapper.toDTO(depRepository.findByDepIndex(depIndex));
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentByName(String depName) {
        return depMapper.toDTO(depRepository.findByDepName(depName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getDepartments() {
        // DepMapper를 통해 List<Department>를 List<DepartmentDTO>로 변환하여 반환
        return depMapper.toDTOs(depRepository.findAll());
    }


    @Override
    public DepartmentDTO addDepartment(DepCreateDTO departmentDTO) {
        if (depRepository.existsByDepName(departmentDTO.getDepName())) {
            // 이미 존재하는 학과명일 경우 null 반환
            return null;
        }
        Department department = new Department(departmentDTO.getDepName());
        log.info("department: {} {}", department.getDepIndex(), department.getDepName());
        Department savedDepartment = depRepository.save(department);
        log.info("savedDepartment: {} {}", savedDepartment.getDepIndex(), savedDepartment.getDepName());
        return depMapper.toDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO updateDepartment(Long depIndex, DepCreateDTO departmentDTO) {
        Department department = depRepository.findByDepIndex(depIndex);
        if (department != null) {
            department.setDepName(departmentDTO.getDepName());
            Department updatedDepartment = depRepository.save(department);
            return depMapper.toDTO(updatedDepartment);
        } else {
            // 수정할 학과가 없을 경우 null 반환
            return null;
        }
    }

    @Override
    public void deleteDepartment(Long depIndex) {
        depRepository.deleteById(depIndex);
    }
}
