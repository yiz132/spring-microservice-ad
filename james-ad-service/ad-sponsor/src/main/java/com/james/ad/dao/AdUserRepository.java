package com.james.ad.dao;

import com.james.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdUserRepository extends JpaRepository<AdUser,Long> {
    AdUser findByUsername(String username);
}
