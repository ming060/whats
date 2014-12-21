package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric on 2014/12/21.
 */
public class Message {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    private String from;
    private String msg;
    private Date time;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return sdf.format(this.time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(String time) throws ParseException {
        Long timeTmp = Long.valueOf(time);
        this.setTime(new Date(timeTmp));
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }
}
