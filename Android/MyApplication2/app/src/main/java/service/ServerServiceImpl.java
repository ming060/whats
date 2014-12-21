package service;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.HashMap;

import debug.LogManager;
import entity.User;
import util.HttpUtil;

/**
 * Created by user on 2014/12/21.
 */
public class ServerServiceImpl implements IServerService {
    private LogManager log = null;
    private boolean isLogEnable = true;
    private String ip = "http://192.168.1.69:3000";
    private String msg;

    public ServerServiceImpl() {
        log = LogManager.getLogger(this.getClass(), isLogEnable);
    }

    @Override
    public boolean register(String username, String password) {
        log.d("register()");
        HttpUtil httpUtil = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        String msg = httpUtil.connectPost(this.ip+"/register/", map);
        log.d(httpUtil.getResponseState() +" -  " +msg);
        if (httpUtil.getResponseState() == HttpURLConnection.HTTP_OK) {
            User.me.setUsername(username);
            User.me.setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean login(String username, String password) {
        log.d("login()");
        HttpUtil httpUtil = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        String msg = httpUtil.connectPost(this.ip+"/login/", map);
        log.d(httpUtil.getResponseState() +" -  " +msg);
        if (httpUtil.getResponseState() == HttpURLConnection.HTTP_OK) {
            User.me.setUsername(username);
            User.me.setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean read() throws ParseException {
        log.d("read()");
        HttpUtil httpUtil = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", User.me.getUsername());
        map.put("password", User.me.getPassword());
        msg = httpUtil.connectPost(this.ip+"/read/", map);
        log.d(httpUtil.getResponseState() +" -  " +msg);
        if (httpUtil.getResponseState() == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean write(String receiver, String message) {
        log.d("write()");
        HttpUtil httpUtil = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", User.me.getUsername());
        map.put("password", User.me.getPassword());
        map.put("receiver", receiver);
        map.put("message", message);
        String msg = httpUtil.connectPost(this.ip+"/write/", map);
        log.d(httpUtil.getResponseState() +"  -  " +msg);
        if (httpUtil.getResponseState() == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
