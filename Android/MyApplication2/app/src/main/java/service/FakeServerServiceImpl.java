package service;

import java.text.ParseException;

import debug.LogManager;
import entity.Message;
import entity.User;

/**
 * Created by Eric on 2014/12/21.
 */
public class FakeServerServiceImpl implements IServerService {
    protected LogManager log = null;
    protected boolean isLogEnable = true;

    public FakeServerServiceImpl() {
        this.log = LogManager.getLogger(this.getClass(), this.isLogEnable);
    }


    @Override
    public boolean register(String username, String password) {
        User.me.setUsername(username);
        User.me.setPassword(password);
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        User.me.setUsername(username);
        User.me.setPassword(password);
        return true;
    }

    @Override
    public boolean read() throws ParseException {
        String username = User.me.getUsername();
        String password = User.me.getPassword();
        Message msg = new Message();
        msg.setFrom("ming");
        msg.setMsg("Test msg");
        msg.setTime("2014/12/21 09:30:00");
        User.me.addMsg(msg);
        return true;
    }

    @Override
    public boolean write(String receiver, String message) {
        String username = User.me.getUsername();
        String password = User.me.getPassword();

        return true;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
