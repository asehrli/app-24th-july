package com.company.app24thjuly.bot;

import com.company.app24thjuly.bot.config.BotConfig;
import com.company.app24thjuly.bot.exception.MyBotException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Getter
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private boolean wait;

    public TelegramBot(BotConfig botConfig) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
    }

    String[] texts = {
            "Men",
            "seni",
            "sevaman",
            "afsuski",
            "shunday"
    };

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            Long id = update.getMessage().getFrom().getId();

            if (true) {
                tryExecute(SendMessage.builder()
                        .chatId(id)
                        .text("Hi")
                        .replyMarkup(InlineKeyboardMarkup.builder()
                                .keyboardRow(Collections.singletonList(InlineKeyboardButton.builder()
                                        .text("Hello")
                                        .webApp(new WebAppInfo("https://www.google.com"))
                                        .build()))
                                .build())
                        .build());
                return;
            }

            if (wait)
                return;


            wait = true;

            Message executedMessage = execute(SendMessage.builder().chatId(id).text("Hi").build());
            TimeUnit.SECONDS.sleep(1);

            for (String t : texts) {


                String s = "";
                for (char c : t.toCharArray()) {
                    s = s + c;
                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(id)
                            .text(s)
                            .messageId(executedMessage.getMessageId())
                            .build();

                    execute(editMessageText);
                    TimeUnit.MILLISECONDS.sleep(100);
                }


                TimeUnit.SECONDS.sleep(1);

            }


            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .messageId(executedMessage.getMessageId())
                    .chatId(id)
                    .build();

            execute(deleteMessage);

            wait = false;
        }
    }

    public Message tryExecute(SendMessage sendMessage) {
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new MyBotException(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }
}
