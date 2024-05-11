package com.npt.anis.ANIS.department.controller;

import com.npt.anis.ANIS.department.dto.DepCreateDTO;
import com.npt.anis.ANIS.department.dto.DepartmentDTO;
import com.npt.anis.ANIS.department.service.DepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepController {
    private final DepService depService;
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        List<DepartmentDTO> departments = depService.getDepartments();
        if (departments == null || departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(departments, HttpStatus.OK);
        }
    }

    @GetMapping("/department")
    public ResponseEntity<DepartmentDTO> getDepartment(@RequestParam(value = "name", required = false) String depName,
                                                       @RequestParam(value = "id", required = false) Long depIndex) {
        DepartmentDTO department = null;
        if (depName != null) {
            department = depService.getDepartmentByName(depName);
        } else if (depIndex != null) {
            department = depService.getDepartmentByIndex(depIndex);
        }

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepCreateDTO departmentDTO) {
        DepartmentDTO department = depService.addDepartment(departmentDTO);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(department, HttpStatus.CREATED);
        }
    }

    @PatchMapping("/department")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestParam("id") String depIndex, @RequestBody DepCreateDTO departmentDTO) {
        DepartmentDTO department = depService.updateDepartment(Long.valueOf(depIndex), departmentDTO);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

    @DeleteMapping("/department")
    public ResponseEntity<String> deleteDepartment(@RequestParam("id") String depIndex) {
        depService.deleteDepartment(Long.valueOf(depIndex));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
