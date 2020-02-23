package com.example.fontreszie;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public final class StringUtil {
    //格式 月日 星期 时间
    public static SimpleDateFormat SPECIAL_SDF = new SimpleDateFormat("MMM d EEE aaa hh:mm", Locale.getDefault());

    public static String md5(String rawStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(rawStr.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                sb.append(h);
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return rawStr;
        }
    }
    //DES加密
    public static String encrypt(String value, String cryptoPass) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Base64.encodeToString(cipher.doFinal(clearText),
                    Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }
   //Des
    public static String decrypt(String value, String cryptoPass) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedBytes = Base64.decode(value, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedBytes));

            return new String(decrypedValueBytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回 html 文字带font的标签
     * 
     * @param color
     * @param text
     * @return like this "<font color='#FF0000'>Hello</font> "
     */
    public static Spanned getTextWithColor(int color, String text) {

        if (text == null) {
            text = "";
        }
        String R, G, B;
        StringBuffer sb = new StringBuffer();
        sb.append("<font color='");
        R = Integer.toHexString(Color.red(color));
        G = Integer.toHexString(Color.green(color));
        B = Integer.toHexString(Color.blue(color));

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        sb.append("0x");
        sb.append(R);
        sb.append(G);
        sb.append(B);
        sb.append("'>");
        sb.append(text);
        sb.append("</font>");

        return Html.fromHtml(sb.toString());
    }

    /**
     * 计算字符串占用位数
     * @param str1
     * @param str2
     * @return
     */
    public static int calculatePlaces(String str1, String str2) {
        int m = 0;
        StringBuilder total = new StringBuilder();
        total.append(str1);
        total.append(str2);
        char arr[] = total.toString().toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ((c >= 0x0391 && c <= 0xFFE5))  //中文字符
            {
                m = m + 2;
            } else if ((c >= 0x0000 && c <= 0x00FF)) //英文字符
            {
                m = m + 1;
            }
        }
        return m;
    }

    /**
     * 邮箱全称（昵称+邮箱地址）转换成邮箱地址 例如 wenlin<wenlin@zhizhangyi.com> - > <wenlin@zhizhangyi.com>
     * @param str
     * @return
     */
    public static String rawAddrToEmailAddr(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        // 截取以左括号的数组
        String temp[] = str.split("<");
        StringBuilder result = new StringBuilder();
        if (temp.length == 2) {
            return result.append("<").append(temp[1]).toString();
        } else {
            return str;
        }
    }

}
