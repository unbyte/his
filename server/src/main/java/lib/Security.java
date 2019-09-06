package lib;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 为{@link model.Staff}提供密码hash功能
 */
public class Security {
    private static final String salt = "%%H3L1O5%%";

    /**
     * 对字符串进行hash
     *
     * @param string 待hash的字符串
     * @return hash结果
     */
    public static String encrypt(String string) {
        try {
            string += salt;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(string.getBytes(StandardCharsets.UTF_8));
            byte[] s = md5.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            return "";
        }
    }
}

