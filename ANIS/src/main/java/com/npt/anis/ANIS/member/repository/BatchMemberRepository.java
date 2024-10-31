package com.npt.anis.ANIS.member.repository;

import com.npt.anis.ANIS.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchMemberRepository {
    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int BATCH_SIZE;
    @Transactional
    public void saveAll(List<Member> memberList) {
        String sql = "INSERT INTO MEMBER" +
                "(STUDENTID, STUDENT_NAME, PASSWORD, DEPARTMENTID, GRADE, BIRTH, ROLE, LAST_LOGIN, IS_QUIT)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<List<Member>> partitions = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i += BATCH_SIZE) {
            partitions.add(memberList.subList(i, Math.min(i + BATCH_SIZE, memberList.size())));
        }

        for (List<Member> partition : partitions) {
            jdbcTemplate.batchUpdate(sql, partition, partition.size(),
                    (PreparedStatement ps, Member member) -> {
                        ps.setString(1, member.getStudentID());
                        ps.setString(2, member.getStudentName());
                        ps.setString(3, member.getPassword());
                        ps.setLong(4, member.getDepartmentID());
                        ps.setInt(5, member.getGrade());
                        ps.setString(6, member.getBirth());
                        ps.setString(7, member.getRole());
                        ps.setTimestamp(8, Timestamp.valueOf(member.getLastLogin()));
                        ps.setBoolean(9, member.isQuit());
                    });
        }
    }
}
