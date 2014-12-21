package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2014/12/21.
 */
public class User {
    public static final User me;

    private String username;
    private String password;

    private ArrayList<Message> msgList = new ArrayList<Message>();

    static {
        me = new User();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Message removeFirstMsg() {
        return this.msgList.remove(0);
    }

    public Message readMsg(int index) {
        return this.msgList.remove(index);
    }

    public void removeAllMsg() {
        this.msgList.clear();
    }

    public List<Message> getAllMsgList() {
        return this.msgList;
    }

    public void addMsg(Message msg) {
        this.msgList.add(msg);
    }


}
