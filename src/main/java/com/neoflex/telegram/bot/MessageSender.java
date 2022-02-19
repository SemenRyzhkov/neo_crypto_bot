package com.neoflex.telegram.bot;

import com.neoflex.telegram.client.CryptoClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.TimerTask;

@Setter
@Component
public class MessageSender extends TimerTask {
    @Lazy
    @Autowired
    private NeoBot neoBot;
    @Autowired
    private CryptoClient cryptoClient;
    private SendMessage sendMessage;

    @Override
    public void run() {
        try {
            sendMessage.setText(String.valueOf(cryptoClient.getPrice("bitcoin", "usd").getBitcoin().getUsd()));
            neoBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
