package debug;

import android.util.Log;

/**
 * Created by Eric on 2014/12/20.
 */
public class LogManager {
    private boolean m_isEnable = false;
    private String m_tag;

    public LogManager(int iEnable) {
        setEnable(iEnable);
    }

    public LogManager(int iEnable, String s) {
        setEnable(iEnable);
        setTag(s);
    }

    public LogManager(boolean isEnable) {
        setEnable(isEnable);
    }

    public LogManager(boolean isEnable, String s) {
        setEnable(isEnable);
        setTag(s);
    }

    public static LogManager getLogger(Class<?> c, boolean isEnable) {
        String className =c.getSimpleName();
        return new LogManager(isEnable, "MyLog: " + className);
    }

    // =========================================================================

    public void setEnable(int iEnable) {
        if (iEnable == 0)
            m_isEnable = false;
        else
            m_isEnable = true;
    }

    public void setEnable(boolean enable) {
        m_isEnable = enable;
    }

    public void setTag(String s) {
        m_tag = s;
    }

    public void d(String strMsg) {
        if (m_tag == null)
            d(" ", strMsg);
        else
            d(m_tag, strMsg);
    }

    public void d(String strTag, String strMsg) {
        if (m_isEnable)
            Log.d(strTag, strMsg);
    }

    public void i(String strMsg) {
        if (m_tag == null)
            i(" ", strMsg);
        else
            i(m_tag, strMsg);
    }

    public void i(String strTag, String strMsg) {
        if (m_isEnable)
            Log.i(strTag, strMsg);
    }

    public void w(String strMsg) {
        if (m_tag == null)
            w(" ", strMsg);
        else
            w(m_tag, strMsg);
    }

    public void w(String strTag, String strMsg) {
        if (m_isEnable)
            Log.w(strTag, strMsg);
    }

    public void w(Throwable e){
        this.w(Log.getStackTraceString(e));
    }

    public void e(String strMsg) {
        if (m_tag == null)
            e(" ", strMsg);
        else
            e(m_tag, strMsg);
    }

    public void e(String strTag, String strMsg) {
        if (m_isEnable)
            Log.e(strTag, strMsg);
    }

    public void e(Throwable e) {
        this.e(Log.getStackTraceString(e));
    }



}
