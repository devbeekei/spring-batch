package com.devbeekei.springbatch.b.repository;

import com.devbeekei.springbatch.b.entity.BUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BUserRepository extends JpaRepository<BUser, Long> {
}
