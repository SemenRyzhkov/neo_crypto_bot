package com.neoflex.telegram.controller;

import com.neoflex.telegram.bot.MessageSender;
import com.neoflex.telegram.bot.NeoBot;
import com.neoflex.telegram.client.CryptoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;

@RestController
@RequiredArgsConstructor
public class WebHookController {
    private final NeoBot neoBot;
    private final CryptoClient cryptoClient;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) throws TelegramApiException {
        Message message = update.getMessage();
//        Timer timer = new Timer();
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(message.getChatId()));
//        MessageSender messageSender = new MessageSender(neoBot, cryptoClient, sendMessage);
//        messageSender.setSendMessage(sendMessage);
//        timer.schedule(messageSender, 0, 10000);
        return neoBot.onWebhookUpdateReceived(update);

    }
}
