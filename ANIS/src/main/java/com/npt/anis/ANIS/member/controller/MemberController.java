package com.npt.anis.ANIS.member.controller;

import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.service.MemberService;
import com.npt.anis.ANIS.member.service.TestMongoService;
import com.npt.anis.ANIS.member.service.TestUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class MemberController {
    private final MemberService memberService;
    private final TestMongoService testMongoService;
    private final TestUserService testUserService;

    @GetMapping("test")
    public String test() {
//        TestMongo testMongo = new TestMongo("sibjagun2","seosang","kyungmin");
//        TestUser testUser = new TestUser(1,"seosang");
//        testMongoService.saveUser(testMongo);
//        testUserService.saveUser(testUser);
        return "index";
    }

    // TODO: MemberService 사용 컨트롤러 작성
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getMembers() {
        return ResponseEntity.ok(memberService.getMembersByAdmin());
    }

    @GetMapping("/members/search")
    public ResponseEntity<List<MemberSearchDTO>> searchMembers(@RequestParam(name = "studentID", required = false) String studentID,
                                                               @RequestParam(name = "studentName", required = false) String studentName,
                                                               @RequestParam(name = "birth", required = false) String birth,
                                                               @RequestParam(name = "departmentName", required = false) String departmentName) {
        log.info("{} {} {} {}", studentID, studentName, birth, departmentName);
        MemberSearchDTO memberSearchDTO = new MemberSearchDTO(studentID, studentName, birth, departmentName);
        List<MemberSearchDTO> result = memberService.getAnyMembers(memberSearchDTO);
        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/member")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO result = memberService.updateMember(memberDTO);
        if (result == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    // 학과 이름으로 검색하는 테스트 컨트롤러 -> TODO: MemberDTO에 DepName을 포함하는 새로운 DTO를 만들어서, 활용할 수 있는 API로
    @GetMapping("/members/dep/")
    public ResponseEntity<List<MemberSearchDTO>> getMembersByDepName(@RequestParam("depName") String depName) {
        List<MemberSearchDTO> result = memberService.getMembersByDepName(depName);
        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }
}
