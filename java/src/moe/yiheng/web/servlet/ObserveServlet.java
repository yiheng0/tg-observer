package moe.yiheng.web.servlet;

import moe.yiheng.bot.BotManager;
import moe.yiheng.domain.Chat;
import moe.yiheng.service.ChatService;
import moe.yiheng.service.MessageService;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "observeServlet", urlPatterns = {"/observeServlet"})
public class ObserveServlet extends HttpServlet {

    ChatService chatService = new ChatService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("收到请求");
        request.getParameterMap().forEach((s, strings) -> System.out.println(s + ":" + strings[0]));

        Chat chat = addChat(request.getParameterMap());
        handleMessage(request.getParameterMap(),chat);

        response.getWriter().write("success");
    }

    private void handleMessage(Map<String, String[]> parameterMap,Chat chat) {
        Long fromGroupId = Long.parseLong(parameterMap.get("from_group_id")[0]);
        String content = parameterMap.get("content")[0];
        Long messageIdSaved = Long.parseLong(parameterMap.get("message_id_saved")[0]);
        Long msgId = Long.parseLong(parameterMap.get("msg_id")[0]);
        try {
            BotManager.bot.handle(msgId,fromGroupId,content,messageIdSaved,chat);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Chat addChat(Map<String, String[]> parameterMap) {
        Long id = Long.parseLong(parameterMap.get("from_group_id")[0]);
        boolean isPrivate = parameterMap.get("is_private")[0].equals("True");
        String title = parameterMap.get("chat_title")[0];
        String username = parameterMap.get("username")[0];
        Chat chat = new Chat(id, title, isPrivate, username);
        if (!chatService.exists(id)) {
            chatService.add(chat);
        } else if (!chatService.findById(id).equals(chat)) { // 群组信息改变
            chatService.update(chat);
        }
        return chat;
    }
}
