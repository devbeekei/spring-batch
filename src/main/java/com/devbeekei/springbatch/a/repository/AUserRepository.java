package com.devbeekei.springbatch.a.repository;

import com.devbeekei.springbatch.a.entity.AUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AUserRepository extends JpaRepository<AUser, Long> {
}
