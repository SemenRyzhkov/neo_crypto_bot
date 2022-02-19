package com.neoflex.telegram.bot.handlers;

import com.neoflex.telegram.bot.BotState;
import com.neoflex.telegram.cache.DataCache;
import com.neoflex.telegram.client.CryptoClient;
import com.neoflex.telegram.model.Bitcoin;
import com.neoflex.telegram.model.User;
import com.neoflex.telegram.service.BitcoinService;
import com.neoflex.telegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ShowReportHandler implements InputMessageHandler {
    private final DataCache userDataCache;
    private final BitcoinService bitcoinService;
    private final CryptoClient cryptoClient;

    @Override
    public SendMessage handle(Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();
        userDataCache.setBotState(userId, BotState.SHOW_MAIN_MENU);
        return new SendMessage(String.valueOf(chatId), report()
        );
    }

    private String report() {
        Bitcoin firstBitcoin = bitcoinService.getFirstByDateTime();
        Bitcoin maxBitcoin = bitcoinService.getMaxPrice();
        Bitcoin lastBitcoin = cryptoClient.getPrice("bitcoin", "usd").getBitcoin();
        String difference = getDifference(firstBitcoin, lastBitcoin);
        return String.format("%s%n -------------------%nStart price: %s usd%nMax price: %s usd%n" +
                        "Current price: %s usd%n%s",
                "Отчет по биткоину за все время стрима",
                firstBitcoin.getUsd(), maxBitcoin.getUsd(), lastBitcoin.getUsd(), difference);
    }

    private String getDifference(Bitcoin firstBitcoin, Bitcoin lastBitcoin) {
        String difference;
        Double firstPrice = firstBitcoin.getUsd();
        Double lastPrice = lastBitcoin.getUsd();
        if (firstPrice > lastPrice) {
            difference = String.format("За время стрима биткоин упал на %s usd", firstPrice - lastPrice);
        } else if (firstPrice < lastPrice) {
            difference = String.format("За время стрима биткоин вырос на %s usd", lastPrice - firstPrice);
        } else {
            difference = "Биткоин по отношению к доллару не изменился>";
        }
        return difference;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_REPORT;
    }
}
