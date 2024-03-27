package com.npt.anis.ANIS.nosql.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    @Id
    private String id;
    private String name;
    private String link;
    private String html;
    /***
     * gpt 말로는 mongodb에서 LocalDateTime 을 가장 많이 사용한다고함
     */
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
