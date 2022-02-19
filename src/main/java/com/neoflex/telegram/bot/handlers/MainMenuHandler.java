package com.neoflex.telegram.bot.handlers;


import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.model.User;
import com.neoflex.telegram.service.MenuService;
import com.neoflex.telegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class MainMenuHandler implements InputMessageHandler {
    private final MenuService mainMenuService;
    private final UserService userService;

    @Override
    public SendMessage handle(Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();

        User user = new User(userId, message.getFrom().getFirstName(), message.getFrom().getUserName());
        userService.save(user);
        return mainMenuService.getMainMenuMessage(chatId,"Воспользуйтесь главным меню");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
