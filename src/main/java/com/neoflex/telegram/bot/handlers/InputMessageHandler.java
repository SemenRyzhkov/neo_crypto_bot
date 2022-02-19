package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {
    SendMessage handle(Message message);

    BotState getHandlerName();
}
