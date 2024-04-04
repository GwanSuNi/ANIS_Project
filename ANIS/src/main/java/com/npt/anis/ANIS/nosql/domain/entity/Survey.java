package com.npt.anis.ANIS.nosql.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document( collection = "survey" )
public class Survey {
    private String name;
    private String link;
    private String html;
    /***
     * gpt 말로는 mongodb에서 LocalDateTime 을 가장 많이 사용한다고함
     */
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
