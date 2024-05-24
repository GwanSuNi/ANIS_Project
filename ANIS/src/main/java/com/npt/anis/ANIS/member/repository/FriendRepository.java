package com.npt.anis.ANIS.member.repository;

import com.npt.anis.ANIS.member.domain.dto.FriendDto;
import com.npt.anis.ANIS.member.domain.entity.Friend;
import com.npt.anis.ANIS.member.domain.entity.Member;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long>  {
    boolean existsByFrStuIDAndMyStuID(String friendID, String myID);

    Friend findByFrStuIDAndMyStuID(String friendID, String myID);

    /**
     * 유저와 친구가 되어있는 ID를 찾기위한 메서드
     * @param myID 유저의 ID
     * @return
     */
    @Query("SELECT f FROM Friend f WHERE f.addState = true AND f.myStuID = :myID")
    List<Friend> findByMyStuID(@Param("myID") String myID);

}
