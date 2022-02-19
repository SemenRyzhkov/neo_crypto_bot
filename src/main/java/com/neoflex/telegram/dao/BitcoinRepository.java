package com.neoflex.telegram.dao;

import com.neoflex.telegram.model.Bitcoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinRepository extends JpaRepository<Bitcoin, Long> {
    Bitcoin getFirstByOrderByDateTime();
    Bitcoin getTopByOrderByUsdDesc();
    Bitcoin getFirstByOrderByDateTimeDesc();
}
