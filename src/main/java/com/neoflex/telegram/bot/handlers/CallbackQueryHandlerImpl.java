package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.CheckIndicators;
import com.neoflex.telegram.cache.DataCache;
import com.neoflex.telegram.client.CryptoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Timer;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {
    private final DataCache dataCache;
    private final CryptoClient cryptoClient;
    private final CheckIndicators checkIndicators;
    private Timer timer = new Timer();

    @Override
    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final long userId  = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = null;
        String data = buttonQuery.getData();
        switch (data) {
            case ("1"):
                dataCache.setCurrent(userId, cryptoClient.getPrice("bitcoin", "usd").getBitcoin().getUsd());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                checkIndicators.setSendMessage(sendMessage);
                checkIndicators.setMessage(buttonQuery.getMessage());
                timer.schedule(checkIndicators, 0, 10000);
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Напоминание создано");
        }
        return callBackAnswer;
    }
}
