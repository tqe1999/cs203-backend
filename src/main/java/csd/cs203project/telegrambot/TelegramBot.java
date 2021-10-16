package csd.cs203project.telegrambot;

import csd.cs203project.model.User;
import csd.cs203project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UserRepository userRepository;

    @Autowired
    public TelegramBot(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Value("${telegramBotToken}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return "SpringTestBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message updateMessage = update.getMessage();
        if (updateMessage != null) {
            String chatId = Long.toString(updateMessage.getChatId());
            String text = updateMessage.getText();
            String[] textArray = text.split(" ");
            if (textArray[0].equals("/start") && textArray.length == 2) {
                System.out.println(textArray[1]);
                try {
                    User user = userRepository.findByTelegramSignUpToken(textArray[1]).orElseThrow();
                    user.setTelegramChatId(chatId);
                    userRepository.save(user);
                }
                catch (NoSuchElementException e) {
                    System.out.println("There is no user with this SignUpToken");
                }


            }
            System.out.println(update.getMessage().getChatId());
            System.out.println(update.getMessage().getText());

        }
    }

    public void sendMessage(String message, String chatId) {
        SendMessage telegramMessage = new SendMessage();
        telegramMessage.setText(message);
        telegramMessage.setChatId(chatId);
        try{
            execute(telegramMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String generateSignUpToken(Long userId) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
