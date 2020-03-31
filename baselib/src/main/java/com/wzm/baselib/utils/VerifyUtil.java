package com.wzm.baselib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：校验工具类
 * 创建者： wangzm
 * 时间：2019/12/13
 * 修改者：
 * 时间：
 */
public class VerifyUtil {
    //是否整数或者小数
    public static boolean isNumberOrDecimal(String str) {
        Pattern pattern = Pattern.compile("^[-|+]?\\d*([.]\\d{0,2})?$"); // 整数，最多可以有两位小数
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 弱口令校验规则
     *
     * @param str
     * @return
     */
    public static boolean checkInput(String str) {
        int num = 0;
        num = Pattern.compile("\\d").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[a-z]").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[A-Z]").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[-~_.!@#$%^&*()+?><]").matcher(str).find() ? num + 1 : num;
        return num >= 3;
    }

    /**
     * 只能输入中文的判断
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

}
