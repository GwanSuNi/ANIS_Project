package com.npt.anis.ANIS.nosql.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TestMongo")
public class TestMongo {
    @Id
    private String id;
    private String username;
    private String email;

}
