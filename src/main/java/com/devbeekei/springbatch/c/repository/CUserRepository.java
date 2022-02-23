package com.devbeekei.springbatch.c.repository;

import com.devbeekei.springbatch.c.entity.CUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CUserRepository extends JpaRepository<CUser, Long> {
}
