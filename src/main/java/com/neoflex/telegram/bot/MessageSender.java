package com.neoflex.telegram.bot;

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

@Setter
@Component
@Scope("prototype")
public class MessageSender extends TimerTask {
    @Lazy
    @Autowired
    private NeoBot neoBot;
    @Autowired
    private CryptoClient cryptoClient;
    @Autowired
    private BitcoinService bitcoinService;
    private SendMessage sendMessage;
    private Message message;

    @Override
    public void run() {
        try {
            Crypto crypto= cryptoClient.getPrice("bitcoin", "usd");
            Bitcoin bitcoin = crypto.getBitcoin();
            long userId = message.getFrom().getId();
            bitcoinService.save(bitcoin, userId);
            sendMessage.setText(String.format("BTC - %s USD", bitcoin.getUsd()));
            neoBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
