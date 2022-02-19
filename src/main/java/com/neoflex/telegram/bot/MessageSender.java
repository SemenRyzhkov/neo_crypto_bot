package com.neoflex.telegram.bot;

import com.neoflex.telegram.client.CryptoClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.TimerTask;

@Setter
@Component
@Scope("prototype")
public class MessageSender extends TimerTask {
    @Lazy
    @Autowired
    private NeoBot neoBot;
    @Autowired
    private CryptoClient cryptoClient;
    private SendMessage sendMessage;

//    public MessageSender(NeoBot neoBot, CryptoClient cryptoClient, SendMessage sendMessage) {
//        this.neoBot = neoBot;
//        this.cryptoClient = cryptoClient;
//        this.sendMessage = sendMessage;
//    }

    @Override
    public void run() {
        try {
            String price = String.valueOf(cryptoClient.getPrice("bitcoin", "usd").getBitcoin().getUsd());
            sendMessage.setText(String.format("BTC - %s USD", price));
            neoBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
