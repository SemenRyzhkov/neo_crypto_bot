package com.neoflex.telegram.bot.senders;

import com.neoflex.telegram.bot.NeoBot;
import com.neoflex.telegram.cache.DataCache;
import com.neoflex.telegram.client.CryptoClient;
import com.neoflex.telegram.model.Bitcoin;
import com.neoflex.telegram.model.Crypto;
import com.neoflex.telegram.service.BitcoinService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

@Setter
@Component
public class CheckIndicators extends TimerTask {
    @Lazy
    @Autowired
    private NeoBot neoBot;
    @Autowired
    private CryptoClient cryptoClient;
    @Autowired
    private DataCache dataCache;
    private Message message;
    private SendMessage sendMessage;

    @Override
    public void run() {
        try {
            long userId = Long.parseLong(sendMessage.getChatId());
            Double current = dataCache.getCurrent(userId);
            Crypto crypto= cryptoClient.getPrice("bitcoin", "usd");
            Bitcoin bitcoin = crypto.getBitcoin();
            if ((current - bitcoin.getUsd()) > percent(current)){
                sendMessage.setText(String.format("!!!!!WARNING!!!!!%n" +
                        "Биткоин упал больше чем на 0.01 процент%n" +
                        "!!!!!!WARNING!!!!!"));
                neoBot.execute(sendMessage);
                dataCache.setCurrent(userId, 1.0);
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private Double percent(Double current) {
        return current/10000;
    }
}
