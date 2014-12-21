package service;

import java.text.ParseException;

/**
 * Created by Eric on 2014/12/21.
 */
public interface IServerService {
    public boolean register(String username, String password);
    public boolean login(String username, String password);
    public boolean read() throws ParseException;
    public boolean write(String receiver, String message);
    public String getMsg();
}
