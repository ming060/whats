package util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Eric on 2014/12/21.
 */
public class CloseableTool {
    public static void close(Closeable c) {
        try {
            c.close();
        } catch (IOException e) {
//			e.printStackTrace();
        } catch (NullPointerException e) {
//			e.printStackTrace();
        }
    }
}
