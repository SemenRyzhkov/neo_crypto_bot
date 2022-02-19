package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.bot.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Timer;

@Component
@RequiredArgsConstructor
public class ShowBitcoinHandler implements InputMessageHandler {
    private final MessageSender messageSender;
    private Timer timer = new Timer();

    @Override
    public SendMessage handle(Message message) {
        SendMessage replyMessage = null;
        if (message.getText().equalsIgnoreCase("подписаться на биткоин")) {
            replyMessage = openBitcoin(message, timer);
        } else if (message.getText().equalsIgnoreCase("отписаться от биткоина")) {
            replyMessage = closeBitcoin(message, timer);
        }
        return replyMessage;
    }

    private SendMessage closeBitcoin(Message message, Timer timer) {
        timer.cancel();
        return new SendMessage(String.valueOf(message.getChatId()), "Подписка отменена");
    }

    private SendMessage openBitcoin(Message message, Timer timer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        messageSender.setSendMessage(sendMessage);
        timer.schedule(messageSender, 0, 10000);
        return new SendMessage(String.valueOf(message.getChatId()), "Подписка оформлена");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_BITCOIN;
    }
}
