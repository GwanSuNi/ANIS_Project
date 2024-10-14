package com.npt.anis.ANIS.member.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchByAdminDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import com.npt.anis.ANIS.member.service.FriendService;
import com.npt.anis.ANIS.member.service.MemberService;
import com.npt.anis.ANIS.member.service.TestMongoService;
import com.npt.anis.ANIS.member.service.TestUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class MemberController {
    private final MemberService memberService;
    private final TestMongoService testMongoService;
    private final TestUserService testUserService;
    private final FriendService friendService;
    private final MemberRepository memberRepository;

    @GetMapping("test")
    public String test() {
//        TestMongo testMongo = new TestMongo("sibjagun2","seosang","kyungmin");
//        TestUser testUser = new TestUser(1,"seosang");
//        testMongoService.saveUser(testMongo);
//        testUserService.saveUser(testUser);
        return "index";
    }

    @GetMapping("/")
    public ResponseEntity<String> getUsername() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (name == null || name.equals("anonymousUser")) {
            return ResponseEntity.status(403).body("로그인이 필요합니다.");
        }
        return ResponseEntity.ok(name);
    }


    // TODO: API Secure
    @GetMapping("/members")
    public ResponseEntity<List<MemberSearchByAdminDto>> getMembers() {
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
    public ResponseEntity<MemberSearchByAdminDto> updateMember(@RequestBody MemberSearchByAdminDto memberDTO) {
        MemberSearchByAdminDto result = memberService.updateMember(memberDTO);
        if (result == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    // 학생이 본인의 정보를 조회하는 컨트롤러
    @GetMapping("/member/myInfo")
    public ResponseEntity<MemberSearchDTO> myInfo() {
        String studentID = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberSearchDTO result = memberService.getMemberSearch(studentID);
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

    @GetMapping("/qr")
    public ResponseEntity<byte[]> StudentIDQr() throws WriterException, IOException {
        // QR 정보
        int width = 300;
        int height = 300;
        String studentID = SecurityContextHolder.getContext().getAuthentication().getName();

        // QR Code - BitMatrix: qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter()
                .encode(studentID, BarcodeFormat.QR_CODE, width, height);

        // QR Code - Image 생성. : 1회성으로 생성해야 하기 때문에
        // stream으로 Generate(1회성이 아니면 File로 작성 가능.)
        try {
            //output Stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //Bitmatrix, file.format, outputStream
            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());

        }catch (Exception e){log.warn("QR Code OutputStream 도중 Excpetion 발생, {}", e.getMessage());}

        return null;
    }
    
    // TODO 코드중복 해결하기
    @GetMapping("/members/friendSearch")
    public ResponseEntity<List<MemberSearchDTO>> searchFriendMembers(@RequestParam(name = "studentID", required = false) String studentID,
                                                                     @RequestParam(name = "studentName", required = false) String studentName,
                                                                     @RequestParam(name = "birth", required = false) String birth,
                                                                     @RequestParam(name = "departmentName", required = false) String departmentName) {
        log.info("{} {} {} {}", studentID, studentName, birth, departmentName);
        MemberSearchDTO memberSearchDTO = new MemberSearchDTO(studentID, studentName, birth, departmentName);
        String myID = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MemberSearchDTO> result = memberService.getAnyMembers(memberSearchDTO);
        // 현재 사용자의 친구 목록 가져오기
        List<MemberSearchDTO> myFriends = friendService.getMyFriends(myID);

        // 나의 정보 또한 myFriends 에 담기
        myFriends.add(memberService.getMemberSearch(myID));

        // 친구 목록을 사용하여 검색 결과 필터링
        result = result.stream()
                .filter(member -> myFriends.stream().noneMatch(friend -> friend.getStudentID().equals(member.getStudentID())))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }
}
