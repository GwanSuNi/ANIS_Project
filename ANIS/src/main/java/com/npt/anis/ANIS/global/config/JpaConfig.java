package com.npt.anis.ANIS.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.npt.anis.ANIS.assessment.*","com.npt.anis.ANIS.lecture.*",
                                        "com.npt.anis.ANIS.member.*"})
public class JpaConfig {
}
