package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.cache.DataCache;
import com.neoflex.telegram.model.User;
import com.neoflex.telegram.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ShowProfileHandler implements InputMessageHandler {
    private DataCache userDataCache;
    private UserService userService;

    public ShowProfileHandler(DataCache userDataCache,
                              UserService userService
    ) {
        this.userDataCache = userDataCache;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();
        final User profile = userService.getByChatId(chatId);

        userDataCache.setBotState(userId, BotState.SHOW_MAIN_MENU);
        return new SendMessage(String.valueOf(message.getChatId()),
                String.format("%s%n -------------------%nFirstName: %s%nUserName: %s%n",
                        "Данные по вашему профилю", profile.getFirstName(), profile.getUserName()));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_PROFILE;
    }
}
