package moe.yiheng.domain;

import java.util.Objects;

public class Chat {
    private Long id;
    private String title;
    private boolean isPrivate;
    private String username;

    public Chat(Long id, String title, boolean isPrivate, String username) {
        this.id = id;
        this.title = title;
        this.isPrivate = isPrivate;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return isPrivate == chat.isPrivate &&
                Objects.equals(id, chat.id) &&
                Objects.equals(title, chat.title) &&
                Objects.equals(username, chat.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, isPrivate, username);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isPrivate=" + isPrivate +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Chat() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
