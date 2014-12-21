package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import debug.LogManager;

/**
 * Created by Eric on 2014/12/21.
 */
public class HttpUtil {
    protected LogManager log = null;
    protected boolean isLogEnable = true;
    private int responseState;

    public HttpUtil() {
        this.log = LogManager.getLogger(this.getClass(), this.isLogEnable);
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1024);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String connectGet(String url, HashMap<String, String> params) {
        String response = "";
        URL urlGet;
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            if (params != null) url = url + "?" + Util.encodeParams(params);
            urlGet = new URL(url);
            connection = (HttpURLConnection) urlGet.openConnection();

            this.responseState = connection.getResponseCode();
            if (this.responseState == HttpURLConnection.HTTP_OK) is = connection.getInputStream();
            else is = connection.getErrorStream();
            response = read(is);

        } catch (IOException e) {
            log.e(e);
        } finally {
            CloseableTool.close(is);
        }
        return response;
    }

    public String connectPost(String url, HashMap<String, String> params) {
        String content = Util.toJSONObjectString(params);
        log.d("Post content = " + content);

        String response = "";
        URL urlPost;
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            urlPost = new URL(url);
            connection = (HttpURLConnection) urlPost.openConnection();

            // 设定请求的方法为"POST"，默认是GET
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);  //设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            connection.setDoInput(true);  // 设置是否从httpUrlConnection读入，默认情况下是true;   ---- 這個实际上沒有必要设置
            connection.setUseCaches(false); //Post 请求不能使用缓存，因为要保证post数据安全
            connection.setConnectTimeout(5000);// （单位：毫秒）jdk
            connection.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            // 所以在开发中不调用上述的connect()也可以)。
            os = connection.getOutputStream();
            os.write(content.getBytes()); // 这里数据的组织形式在你
            os.flush();

            this.responseState = connection.getResponseCode();
            log.d("responseState=" + responseState);

            //这里为止，所有的http请求设置，包括数据都应经完毕，下面一布进行从httpUrlConnection读数据
            //HttpURLConnection的connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。
            //无论是post还是get，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去。
            is = connection.getInputStream();
            response = read(is);
//            response = connection.getResponseMessage();
        } catch (IOException e) {
            log.e(e);
        } finally {
            CloseableTool.close(os);
            CloseableTool.close(is);
        }
        return response;
    }

    public int getResponseState() {
        return responseState;
    }

}
