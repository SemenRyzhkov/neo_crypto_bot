package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class AskDowngradeHandler implements InputMessageHandler {
    private final MenuService menuService;
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();
        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), "Выберите индикатор понижения");
        replyMessage.setReplyMarkup(menuService.getInlineMessageButtons());
        return replyMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DOWNGRADE;
    }
}
