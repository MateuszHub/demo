package uj.mateusz.demo.dto;

public class ChatMessageJSON {

    private String content;

    private String date;

    private String user;

    public ChatMessageJSON(String content, String date, String user) {
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public String toString() {
        return "{"
                + "\"Content\":" + this.content + ", "
                + "\"Date\":\"" + this.date + "\", "
                + "\"User\":\"" + this.user
                + "\"}";
    }
}
