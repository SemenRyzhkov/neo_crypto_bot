package com.neoflex.telegram.bot;

import com.neoflex.telegram.client.CryptoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Timer;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramFacade {
    private final MessageSender messageSender;
//    private final CryptoClient cryptoClient;

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return null;
        } else {
            Message message = update.getMessage();

            if (message != null && message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getFirstName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {

        String inputMessage = message.getText().toLowerCase();
        long chatId = message.getChatId();
        SendMessage replyMessage = null;
        if (inputMessage.equals("/start")) {
            replyMessage = new SendMessage(String.valueOf(chatId), "Hello!");
        } else if (inputMessage.equals("q")) {
            Timer timer = new Timer();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
//            MessageSender messageSender = new MessageSender(neoBot, cryptoClient, sendMessage);
            messageSender.setSendMessage(sendMessage);
            timer.schedule(messageSender, 0, 10000);
//            replyMessage = new SendMessage(String.valueOf(chatId),
//                    String.valueOf(cryptoClient.getPrice("bitcoin", "usd").getBitcoin().getUsd()));

        }
        return replyMessage;

    }

}
