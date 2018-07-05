package moe.yiheng.service;

import moe.yiheng.dao.ChatDao;
import moe.yiheng.domain.Chat;

public class ChatService {
    public ChatDao dao = new ChatDao();

    public void add(Chat chat) {
        dao.add(chat);
    }

    public boolean exists(Long id){
        return dao.findById(id) != null;
    }

    public void update(Chat chat){
        dao.update(chat);
    }

    public Chat findById(Long id){
        return dao.findById(id);
    }
}
