package util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2014/12/21.
 */
public class Util {
    public static String encodeParams(Map<String, String> params) {
        //TODO
        throw new IllegalArgumentException("This method not finish!");

    }


    public static String toJSONObjectString(Map<String, String> params) {
        JSONObject jsonObject = new JSONObject(params);
        return jsonObject.toString();
    }
}
