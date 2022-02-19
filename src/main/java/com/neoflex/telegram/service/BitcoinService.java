package com.neoflex.telegram.service;

import com.neoflex.telegram.dao.BitcoinRepository;
import com.neoflex.telegram.dao.UserRepository;
import com.neoflex.telegram.model.Bitcoin;
import com.neoflex.telegram.model.Crypto;
import com.neoflex.telegram.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BitcoinService {
    private final BitcoinRepository bitcoinRepository;
    private final UserRepository userRepository;

    public Bitcoin save(Bitcoin bitcoin, long userId){
        User user = userRepository.getById(userId);
        bitcoin.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        bitcoin.setUser(user);
        return bitcoinRepository.save(bitcoin);
    }

    public Bitcoin getFirstByDateTime(){
        return bitcoinRepository.getFirstByOrderByDateTime();
    }

    public Bitcoin getLastByDateTime(){
        return bitcoinRepository.getFirstByOrderByDateTimeDesc();
    }

    public Bitcoin getMaxPrice(){
        return bitcoinRepository.getTopByOrderByUsdDesc();
    }

}
