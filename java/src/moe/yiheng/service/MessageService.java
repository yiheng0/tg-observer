package moe.yiheng.service;

import moe.yiheng.dao.MessageDao;
import moe.yiheng.domain.Message;

public class MessageService {

    MessageDao dao = new MessageDao();

    public void add(Message message) {
        dao.add(message);
    }

    public Message findMessageByAskingMessageId(Long askingMessageId) {
        return dao.findMessageByAskingMessageId(askingMessageId);
    }

    public void update(Message message) {
        dao.update(message);
    }
}
