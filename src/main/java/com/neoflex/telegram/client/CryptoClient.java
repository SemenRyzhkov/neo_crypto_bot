package com.neoflex.telegram.client;


import com.neoflex.telegram.model.Crypto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "crypto",
        url = "https://api.coingecko.com/api/v3/simple/price")
public interface CryptoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    Crypto getPrice(@RequestParam(value = "ids") String ids,
                    @RequestParam(value = "vs_currencies") String vs_currencies);
}
