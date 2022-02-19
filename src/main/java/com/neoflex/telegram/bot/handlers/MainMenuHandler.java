package com.neoflex.telegram.bot.handlers;


import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.service.MenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainMenuHandler implements InputMessageHandler {
    private MenuService mainMenuService;

    public MainMenuHandler(MenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        final long chatId = message.getChatId();
        return mainMenuService.getMainMenuMessage(chatId,"Воспользуйтесь главным меню");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
