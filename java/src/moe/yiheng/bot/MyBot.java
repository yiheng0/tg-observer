package moe.yiheng.bot;

import moe.yiheng.BotProperties;
import moe.yiheng.domain.Chat;
import moe.yiheng.domain.Message;
import moe.yiheng.service.ChatService;
import moe.yiheng.service.MessageService;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private ChatService chatService = new ChatService();
    private MessageService messageService = new MessageService();
    private final InlineKeyboardMarkup markupInline;

    public MyBot() {
        markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("转发到频道").setCallbackData("forward"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("forward")) {
                long AskingMessageId = update.getCallbackQuery().getMessage().getMessageId();
                Message message = messageService.findMessageByAskingMessageId(AskingMessageId);
                System.out.println(message);
                ForwardMessage forwardMessage = new ForwardMessage(
                        BotProperties.destinctionChannelId,
                        BotProperties.saveMsgChannelId,
                        Integer.parseInt(message.getMessageIdSaved().toString())
                );
                //1.要转发到的地方,2.存消息的channel
                Chat chat = chatService.findById(message.getFromGroupId());
                StringBuilder sb = generateMessage(message.getOriginMsgId(), message.getContent(), chat);

                sb.append("\n#已转发 #")
                        .append(update.getCallbackQuery().getFrom().getUserName());

                EditMessageText editMessageText = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .setParseMode("html")
                        .setText(sb.toString());
                message.setHasForwarded(true);
                messageService.update(message);
                try {
                    execute(forwardMessage);
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotProperties.botName;
    }

    @Override
    public String getBotToken() {
        return BotProperties.botToken;
    }

    public void handle(Long msgId, Long fromGroupId, String content, Long messageIdSaved, Chat chat) throws TelegramApiException {
        boolean isTextMessage = !content.isEmpty();

        StringBuilder sb = generateMessage(msgId, content, chat);

        sb.append("\n是否转发到频道");
        SendMessage sendMessage = new SendMessage(BotProperties.askUserGroupId, sb.toString())
                .setParseMode("html")
                .setReplyMarkup(markupInline);

        org.telegram.telegrambots.api.objects.Message executed = execute(sendMessage);
//        if (!isTextMessage) {
//            ForwardMessage forwardMessage = new ForwardMessage(-283456512L, -1001274377308L, Integer.parseInt(messageIdSaved.toString()));
//            execute(forwardMessage);
//        }
        Message message = new Message(msgId.longValue(), fromGroupId, content, messageIdSaved, Long.parseLong(executed.getMessageId().toString()), false);
        messageService.add(message);
    }

    private StringBuilder generateMessage(Long msgId, String content, Chat chat) {
        StringBuilder sb = new StringBuilder();
        sb.append("47 在 ");

        if (chat.isPrivate()) {
            sb.append("(私密群组)")
                    .append(chat.getTitle())
                    .append(" 中发送了一条消息,内容为\n")
                    .append(content);
        } else {
            sb.append("群组 <code>").append(chat.getTitle());
            if (msgId == null) {
                sb.append("</code> 中发送了一条消息,消息id未获取到");
            } else {
                sb.append("</code> 中发送了<a href=\"https://t.me/")
                        .append(chat.getUsername())
                        .append("/")
                        .append(msgId)
                        .append("\">一条消息</a>");
            }
        }
        return sb;
    }
}
