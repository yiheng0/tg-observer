package moe.yiheng.domain;

import java.util.Objects;

public class Message {
    private Long id;
    private Long originMsgId;
    private Long fromGroupId;
    private String content;
    private Long messageIdSaved;
    private Long AskingMessageId;
    private boolean hasForwarded;


    public Message(Long originMsgId, Long fromGroupId, String content, Long messageIdSaved, Long askingMessageId, boolean hasForwarded) {
        this.originMsgId = originMsgId;
        this.fromGroupId = fromGroupId;
        this.content = content;
        this.messageIdSaved = messageIdSaved;
        AskingMessageId = askingMessageId;
        this.hasForwarded = hasForwarded;
    }

    public Long getOriginMsgId() {

        return originMsgId;
    }

    public void setOriginMsgId(Long originMsgId) {
        this.originMsgId = originMsgId;
    }

    public Long getAskingMessageId() {
        return AskingMessageId;
    }

    public void setAskingMessageId(Long askingMessageId) {
        AskingMessageId = askingMessageId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromGroupId() {
        return fromGroupId;
    }

    public void setFromGroupId(Long fromGroupId) {
        this.fromGroupId = fromGroupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMessageIdSaved() {
        return messageIdSaved;
    }

    public void setMessageIdSaved(Long messageIdSaved) {
        this.messageIdSaved = messageIdSaved;
    }

    public boolean isHasForwarded() {
        return hasForwarded;
    }

    public void setHasForwarded(boolean hasForwarded) {
        this.hasForwarded = hasForwarded;
    }


    public Message() {
    }
}
