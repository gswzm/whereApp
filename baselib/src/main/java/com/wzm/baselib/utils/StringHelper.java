package com.wzm.baselib.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * <p>
 * 字符串帮助类
 * </p>
 *
 * @author ht
 * @version V2.0.0
 * @company 中国电信甘肃万维公司
 * @project nma-c-android
 * @date 2012-8-13 上午10:08:57
 * @class com.wzm.util.StringHelper
 */
public class StringHelper {
    /**
     * 过滤HTML标签
     *
     * @param content
     * @return
     */
    public static String dealHtml(String content) {
        Pattern pt = Pattern.compile("<[^>]*>");
        content = content.replaceAll(pt.pattern(), "");
        if (content.indexOf("&ldquo;") > -1) {
            content = content.replace("&ldquo;", "“");
        }
        if (content.indexOf("&rdquo;") > -1) {
            content = content.replace("&rdquo;", "”");
        }

        if (content.indexOf("&lsquo;") > -1) {
            content = content.replace("&lsquo;", "’");
        }
        if (content.indexOf("&rsquo;") > -1) {
            content = content.replace("&rsquo;", "‘");
        }

        if (content.indexOf("&sbquo;") > -1) {
            content = content.replace("&sbquo;", "，");
        }

        if (content.indexOf("&quot;") > -1) {
            content = content.replace("&quot;", "\"");
        }
        if (content.indexOf("&amp;") > -1) {
            content = content.replace("&amp;", "&");
        }
        if (content.indexOf("&lt;") > -1) {
            content = content.replace("&lt;", "<");
        }
        if (content.indexOf("&gt;") > -1) {
            content = content.replace("&gt;", ">");
        }
        if (content.indexOf("&nbsp;") > -1) {
            content = content.replace("&nbsp;", "");
        }
        return content;
    }

    /**
     * 处理正文中的特殊字符
     *
     * @param str
     * @return
     */
    public static String dealSpecial(String str) {
        char[] char_arr = str.toCharArray();
        int[] pos = new int[10000];
        for (int i = 0; i < pos.length; i++)
            pos[i] = -1;
        int j = 0;
        for (int i = 0; i < char_arr.length; i++) {
            if ((int) char_arr[i] == 10 || (int) char_arr[i] == 13) {
                pos[j++] = i;
            }
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] != -1) {
                sb.insert(pos[i] + i * (5 - 1), "<br/>");
                sb.replace(pos[i] + i * (5 - 1) + 5, pos[i] + i * (5 - 1) + 5
                        + 1, "");
            }
        }
        return sb.toString();
    }


    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    /**
     * 获取实际的value值
     *
     * @param key
     * @param maps
     * @return
     */
    public static String getRealValue(String key, Map<String, String> maps) {
        return maps.get(key);
    }

    /**
     * 字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * object 转 String
     *
     * @param obj
     * @return
     */
    public static String convertToString(Object obj) {
        if (obj == null)
            return "";
        String str = obj.toString().trim();
        if (str.equals("null") || str.equals("NULL"))
            return "";
        return str;
    }

    /**
     * object 转 null
     *
     * @param obj
     * @return
     */
    public static String convertToNullNumber(Object obj) {
        if (obj == null)
            return "--";
        String str = obj.toString().trim();
        if (str.equals("null") || str.equals("NULL"))
            return "--";
        return str;
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获取百分比
     *
     * @param p1
     * @param p2
     * @param decimals
     * @return
     */
    public static String getPercent(double p1, double p2, int decimals) {
        if (p2 <= p1) {
            return "100%";
        } else {
            double p3 = p1 / p2;
            NumberFormat nf = NumberFormat.getPercentInstance();
            if (decimals > 0)
                nf.setMinimumFractionDigits(decimals);
            return nf.format(p3);
        }
    }

    /**
     * 获取百分比
     *
     * @param p1
     * @param p2
     * @param decimals
     * @return
     */
    public static double getPercent2(double p1, double p2, int decimals) {
        double p3 = p1 / p2;
        // 设置位数
        int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = BigDecimal.valueOf(p3 * 100);

        bd = bd.setScale(decimals, roundingMode);
        return bd.doubleValue();
    }
    /**
     * 计算字符串的中文长度 * @param s * @return
     */
    public static int getChineseNum(String s) {
        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
