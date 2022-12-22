package cn.yznu.common.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ClassName StringUtil
 * @Description 字符串工具类
 * @author hele
 * @date 2019年08月14日
 */
public class StringUtil {

    /**
     *
     * @Title: isEmpty
     * @Description: 判断传入对象是否为空
     * @param obj 需要判断的对象，Object类型
     * @return 字符串类型：如果为null或''空字符串，则返回true; 否则返回false
     *         集合：如果为null或集合长度size()为0，返回true; 否则返回false
     *         Map：如果为null或没有装入值，返回true; 否则返回false 数组：如果为null或每一个对象都为空，返回true;
     *         否则返回false
     * @throws
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] objArray = (Object[]) obj;
            boolean flag = true;
            for (Object o : objArray) {
                if (isNotEmpty(o)) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }
        return false;
    }

    /**
     *
     * @Title: isNotEmpty
     * @Description: 判断传入对象是否不为空
     * @param obj
     * @return
     * @throws
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     *
     * @Title: nvl
     * @Description: 如果对象为null，则返回空字符串，否则返回原字符串
     * @param str 需要判断的字符串
     * @return 处理后的字符串
     * @throws
     */
    public static String nvl(String str) {
        return nvl(str, "");
    }

    /**
     *
     * @Title: nvl
     * @Description: 如果对象为null，则返回指定的值，否则返回原字符串
     * @param str  需要判断的字符串
     * @param value  如果str为null，将要被替换为的结果字符串
     * @return  处理后的字符串
     * @throws
     */
    public static String nvl(String str, String value) {
        if (str == null) {
            return value;
        }
        return str;
    }

    /**
     *
     * @Title: trim
     * @Description: 去掉字符串前后的空格及全角空格
     * @param str 需要处理的字符串，如果为空，直接返回
     * @return 去掉前后空格后，返回的字符串
     * @throws
     */
    public static String trim(String str) {
        if (isNotEmpty(str)) {
            // 首先去掉前后正常空格
            str = str.trim();

            if (str.indexOf('　') >= 0) {
                // 去掉左边空格及全角空格
                int index = 0;
                while (index < str.length()) {
                    if (str.charAt(index) != '　' && str.charAt(index) != ' ') {
                        break;
                    }
                    index++;
                }
                str = str.substring(index);

                // 去掉后边空格及全角空格
                index = str.length() - 1;
                while (index >= 0) {
                    if (str.charAt(index) != '　' && str.charAt(index) != ' ') {
                        break;
                    }
                    index--;
                }
                return str.substring(0, index + 1);
            }
        }
        return str;
    }

    /**
     *
     * @Title: subStr
     * @Description: 截取最长为byteLength字节的字符串 如果截取的最后一个字节是半个汉字，则略去该字节，保证返回前边完整的汉字
     * @param str    需要截取的原字符串
     * @param encode  编码方式，如：UTF-8、GBK
     * @param byteLength 截取的字节长度
     * @return 截取后的字符串 例：subStr("ab中国", "UTF-8", 3) 返回 "ab" subStr("ab中国",
     *         "UTF-8", 7) 返回 "ab中"
     * @throws
     */
    public static String subStr(String str, String encode, int byteLength) {
        if (str == null || str == "" || byteLength <= 0) {
            return "";
        }

        String result = str;
        if (byteLength < result.length()) {
            result = result.substring(0, byteLength);
        }

        int tmpByteLength = getByteLength(result, encode);
        while (tmpByteLength > byteLength) {
            result = result.substring(0, result.length() - 1);
            tmpByteLength = getByteLength(result, encode);
        }
        return result;
    }

    /**
     *
     * @Title: toHexStr
     * @Description: 得到字符串的十六进制码，每个字符之间用\\u隔开
     * @param str 源字符串
     * @return 字符串的十六进制码
     * @throws
     */
    public static String toHexStr(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] ch = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : ch) {
            sb.append("\\u").append(Long.toHexString(Long.valueOf(c)));
        }
        return sb.toString();
    }

    /**
     *
     * @Title: toByteStr
     * @Description: 得到字符串的二进制码，每个字符之间用 | 隔开
     * @param str 源字符串
     * @return 字符串的二进制码
     * @throws
     */
    public static String toByteStr(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] ch = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : ch) {
            sb.append(Long.toBinaryString(Long.valueOf(c))).append("|");
        }
        if (isNotEmpty(sb)) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     *
     * @Title: toUpperCaseFirstOne
     * @Description:  首字母转大写
     * @param str
     * @return
     * @throws
     */
    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            return new StringBuilder()
                    .append(Character.toUpperCase(str.charAt(0)))
                    .append(str.substring(1)).toString();
        }
    }

    /**
     *
     * @Title: toUTF8String
     * @Description: 转UTF-8编码
     * @param object
     * @return
     * @throws
     */
    public static String toUTF8String(String object) {
        String utf8String = "";

        if (StringUtil.isEmpty(object)) {
            return utf8String;
        }

        try {
            utf8String = URLDecoder.decode(object, "UTF-8");
            object.getBytes("UTF-8").toString();
            utf8String = StringEscapeUtils.escapeJava(object);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return utf8String;
    }

    /**
     * 使用 UTF-8 编码格式取得字符串字节长度
     *
     * @author zhanglun
     * @param str
     *            源字符串，如果为空，返回字节长度为 0
     * @return 字符串字节长度
     */
    public static int getByteLength(String str) {
        return getByteLength(str, "UTF-8");
    }

    /**
     *
     * @Title: getByteLength
     * @Description: 使用指定的编码格式取得字符串字节长度
     * @param str 源字符串，如果为空，返回字节长度为 0
     * @param encode 编码方式，如：UTF-8、GBK
     * @return 字符串字节长度
     * @throws
     */
    public static int getByteLength(String str, String encode) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes(encode).length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @Title: toEncodeStr
     * @Description: 得到转换编码后的字符串
     * @param str  源字符串
     * @param code 需要转换为哪种编码，如：UTF-8、GBK、GB2312
     * @return 按指定编码转换后的字符串
     * @throws
     */
    public static String toEncodeStr(String str, String code) {
        if (isEmpty(str)) {
            return str;
        }

        try {
            return new String(str.getBytes("ISO-8859-1"), code);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("转换编码异常！");
        }
    }

    /**
     *
     * @Title: toEncodeStr
     * @Description: 得到UTF-8编码的字符串
     * @param str 源字符串
     * @return 转换为UTF-8编码的字符串
     * @throws
     */
    public static String toEncodeStr(String str) {
        return toEncodeStr(str, "UTF-8");
    }

    /**
     *
     * @Title: encode
     * @Description: 使用 UTF-8 格式对字符串进行 URL 编码
     * @param str 需要编码的字符串
     * @return 编码后的字符串
     * @throws
     */
    public static String encode(String str) {
        return encode(str, "UTF-8");
    }

    /**
     *
     * @Title: encode
     * @Description: 使用指定的编码格式对字符串进行 URL 编码
     * @param str 需要编码的字符串
     * @param enc 编码方式，如：UTF-8、GBK
     * @return 编码后的字符串
     * @throws
     */
    public static String encode(String str, String enc) {
        if (isEmpty(str)) {
            return str;
        }
        try {
            return URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @Title: decode
     * @Description: 使用 UTF-8 编码格式对字符串进行 URL 解码
     * @param str 需要解码的字符串
     * @return 解码后的字符串
     * @throws
     */
    public static String decode(String str) {
        return decode(str, "UTF-8");
    }

    /**
     *
     * @Title: decode
     * @Description: 使用指定的编码格式对字符串进行 URL 解码
     * @param str  需要解码的字符串
     * @param enc 解码格式，如：UTF-8、GBK
     * @return
     * @throws
     */
    public static String decode(String str, String enc) {
        if (isEmpty(str)) {
            return str;
        }

        try {
            return URLDecoder.decode(str, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @Title: formatNum
     * @Description: 按千位分割格式化数字
     * @param obj  需要按千位分割格式化的数值
     * @param scale 精度，精确到几位小数
     * @return
     * @throws
     */
    public static String formatNum(Object obj, int scale) {
        StringBuffer pattern = new StringBuffer(
                "###,###,###,###,###,###,###,##0");
        if (scale > 0) {
            pattern.append(".");
            for (int i = 0; i < scale; i++) {
                pattern.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(obj).toString();
    }

    /**
     * 过滤特殊字符为HTML可解析的标识
     *
     * @author zhanglun
     * @param str
     *            需要过滤为html串的字符串
     * @return 过滤后的字符串
     */
    public static String filterHtml(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        str = trim(str);
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '"':
                    sb.append("&quot;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '%':
                    sb.append("&pc;");
                    break;
                case '_':
                    sb.append("&ul;");
                    break;
                case '#':
                    sb.append("&shap;");
                    break;
                case '?':
                    sb.append("&ques;");
                    break;
                case ' ':
                    sb.append("&nbsp;");
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(str.charAt(i));
                    break;
            }
        }
        return sb.toString();
    }

    /**
     *
     * @Title: filterSpace
     * @Description: 去除字符串中空白字符（空格、换行、制表符等）
     * @param str 源字符串
     * @return 过滤后的字符串
     * @throws
     */
    public static String filterSpace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] ch = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : ch) {
            if (!Character.isWhitespace(c)) {
                sb.append(String.valueOf(c));
            }
        }
        return sb.toString();
    }

    /**
     *
     * @Title: filterEmoji
     * @Description:  替换字符串中的表情
     * @param source
     * @return
     * @throws
     */
    public static String filterEmoji(String source) {
        if(source != null){
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE );

            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find()){
                source = emojiMatcher.replaceAll("");
                return source ;
            }
            return source;
        }
        return source;
    }

    /**
     *
     * @Title: array2List
     * @Description: 字符串数组转换成List集合
     * @param strArray   字符串数组
     * @return  字符串list集合
     * @throws
     */
    public static List<String> array2List(String[] strArray) {
        List<String> list = null;
        if (isNotEmpty(strArray)) {
            list = new ArrayList<String>();
            for (String str : strArray) {
                list.add(str);
            }
        }
        return list;
    }

    /**
     *
     * @Title: list2Array
     * @Description: List集合转换成数组
     * @param strList 字符串list集合
     * @return 字符串数组
     * @throws
     */
    public static String[] list2Array(List<String> strList) {
        String[] strArray = null;
        if (isNotEmpty(strList)) {
            strArray = new String[strList.size()];
            for (int i = 0; i < strList.size(); i++) {
                strArray[i] = strList.get(i);
            }
        }
        return strArray;
    }

    /**
     *
     * @Title: list2Str
     * @Description: 将字符串集合转为字符串，用逗号隔开
     * @param strList 字符串list集合
     * @return 用逗号隔开的字符串
     * @throws
     */
    public static String list2Str(List<String> strList) {
        return list2Str(strList, ",");
    }

    /**
     *
     * @Title: list2Str
     * @Description: 将字符串集合转为指定分隔符连接的字符串
     * @param strList  字符串list集合
     * @param separator 分隔符，如：","
     * @return  按指定分隔符连接的字符串
     * @throws
     */
    public static String list2Str(List<String> strList, String separator) {
        if (isNotEmpty(strList)) {
            StringBuffer sb = new StringBuffer();
            if (isEmpty(separator)) {
                separator = ",";
            }
            for (String str : strList) {
                sb.append(str).append(separator);
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }

    /**
     *
     * @Title: joinList
     * @Description: 使用指定的连接符链接list
     * @param arrayList
     * @param separator
     * @return
     * @throws
     */
    @SuppressWarnings("rawtypes")
    public static String joinList(List arrayList, String separator) {
        Object[] array = arrayList.toArray();
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
                .toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     *
     * @Title: array2Str
     * @Description: 将字符串数组转为字符串，用逗号隔开
     * @param strArray 字符串数组
     * @return 用逗号隔开的字符串
     * @throws
     */
    public static String array2Str(String[] strArray) {
        return array2Str(strArray, ",", "");
    }

    /**
     *
     * @Title: array2Str
     * @Description: 将字符串数组转为指定分隔符连接的字符串
     * @param strArray 字符串数组
     * @param separator 分隔符，如：","
     * @return 按指定分隔符连接的字符串
     * @throws
     */
    public static String array2Str(String[] strArray, String separator) {
        return array2Str(strArray, separator, "");
    }

    /**
     *
     * @Title: array2Str
     * @Description: 将字符串数组转为按指定分隔符连接的字符串，转变后的字符串以around包含
     * @param strArray 字符串数组
     * @param separator 分隔符，如：" , "
     * @param around 包含每个字符串的符号，如：" ' "
     * @return  按指定分隔符、包围符连接的字符串 例：array2Str({"中国","人民abc","万岁"},",","'") 返回
     *         "'中国','人民abc','万岁'
     * @throws
     */
    public static String array2Str(String[] strArray, String separator,
                                   String around) {
        if (isNotEmpty(strArray)) {
            StringBuffer sb = new StringBuffer();
            if (isEmpty(separator)) {
                separator = ",";
            }
            around = nvl(around);
            for (String str : strArray) {
                sb.append(around).append(str).append(around).append(separator);
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }



    /**
     *
     * @Title: big2Small
     * @Description: 大写数字转换成阿拉伯数字，仅支持0-9以内的单个字符，如：一 转换成 1，九 转换成 9
     * @param c  单个大写数字，如：三
     * @return 对应的阿拉伯数字，如果输入的字符不在处理范围内，返回原字符
     * @throws
     */
    public static char big2Small(char c) {
        char chr = c;
        switch (c) {
            case '零':
                chr = '0';
                break;
            case '一':
                chr = '1';
                break;
            case '二':
                chr = '2';
                break;
            case '三':
                chr = '3';
                break;
            case '四':
                chr = '4';
                break;
            case '五':
                chr = '5';
                break;
            case '六':
                chr = '6';
                break;
            case '七':
                chr = '7';
                break;
            case '八':
                chr = '8';
                break;
            case '九':
                chr = '9';
                break;
        }
        return chr;
    }










    /**
     *
     * @Title: num2Cn
     * @Description: 阿拉伯数字转为中文
     * @param num 需要转换的数字
     * @return 转换后的中文字符串 例：215084 返回 "二十一万五千零八十四"
     * @throws
     */
    public static String num2Cn(int num) {
        String[] units = { "", "十", "百", "千", "万", "十", "百", "千", "亿" };
        String[] numCns = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
        String result = "";
        if (num < 0) {
            result = "负";
            num = Math.abs(num);
        }
        String t = String.valueOf(num);
        for (int i = t.length() - 1; i >= 0; i--) {
            int r = (int) (num / Math.pow(10, i));
            if (r % 10 != 0) {
                String s = String.valueOf(r);
                String l = s.substring(s.length() - 1, s.length());
                result += numCns[Integer.parseInt(l) - 1];
                result += units[i];
            } else {
                if (!result.endsWith("零")) {
                    result += "零";
                }
            }
        }
        String tmpNum = num + "";
        if (num == 10) {
            return "十";
        } else if (num > 10 && num < 20) {
            return result.substring(1);
        } else if (tmpNum.endsWith("0")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     *
     * @Title: money2Big
     * @Description: 人民币转成大写
     * @param money 需要转换的人民币数值，只考虑两位小数，两位以后的小数部分，直接略去
     * @return  人民币转换成大写后的字符串 例：790 返回 "柒佰玖拾圆整" 1289650.2484 返回
     *         "壹佰贰拾捌万玖仟陆佰伍拾圆贰角肆分"
     * @throws
     */
    public static String money2Big(double money) {
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
        char[] vunit = { '万', '亿' }; // 段名表示
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
        long midVal = (long) (money * 100); // 转化成整形
        if (midVal < 0) {
            throw new RuntimeException("金额不能为负！");
        }
        String valStr = String.valueOf(midVal); // 转化成字符串
        if (valStr.equals("0")) {
            return "零圆整";
        }
        if (valStr.length() > 14) {
            throw new RuntimeException("值过大！");
        }
        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0) {
                prefix += hunit[idx - 1];
            }
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }
        if (prefix.length() > 0) {
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        }
        // 特殊处理10-20之间，如：15 将 "壹拾伍圆整" 修改为 "拾伍圆整" 比较顺口
        if (money >= 10 && money < 20) {
            prefix = prefix.substring(1);
        }
        return prefix + suffix; // 返回最终结果
    }

    /**
     *
     * @Title: isMobile
     * @Description: 是否手机号码格式，只支持以13、15、18开头的号码
     * @param str  需要验证的字符串，如：13012345678、15929224344、18201234676
     * @return 满足手机号码格式返回true，否则返回false
     * @throws
     */
    public static boolean isMobile(String str) {
        return isPatternMatch("^1[3,4,5,7,8]\\d{9}$", str);
    }

    /**
     *
     * @Title: isPhone
     * @Description: 是否固定电话号码格式
     * @param str  需要验证的字符串，支持：010-12345678、0912-1234567、(010)-12345678、(0912)
     *            1234567、 (010)12345678、(0912)-1234567、01012345678 等格式
     * @return  满足固定电话号码格式，返回true，否则返回false
     * @throws
     */
    public static boolean isPhone(String str) {
        return isPatternMatch(
                "(^0\\d{2}-?\\d{8}$)|(^0\\d{3}-?\\d{7,8}$)|(^\\(0\\d{2}\\)-?\\d{8}$)|(^\\(0\\d{3}\\)-?\\d{7,8}$)",
                str);
    }

    /**
     *
     * @Title: isMobileOrPhone
     * @Description: 是否手机号码或固定电话号码格式
     * @param str  需要验证的字符串，如：13012345678、010-12345678 都算满足
     * @return 满足返回true，否则返回 false
     * @throws
     */
    public static boolean isMobileOrPhone(String str) {
        return isMobile(str) || isPhone(str);
    }

    /**
     *
     * @Title: isEmail
     * @Description: 是否Email邮箱格式
     * @param str  需要验证的字符串，如：zhangsan@163.com、li-si@236.net、wan_gwu999@SEED.NET.
     * @return  满足邮箱格式返回true，否则返回false
     * @throws
     */
    public static boolean isEmail(String str) {
        return isPatternMatch(
                "^([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+(\\.[a-zA-Z]{2,3})+$",
                str);
    }

    /**
     *
     * @Title: isNum
     * @Description: 是否为正整数
     * @param str  需要验证的字符串
     * @return 全部为正整数返回true，否则返回false
     * @throws
     */
    public static boolean isNum(String str) {
        return isPatternMatch("^\\d+$", str);
    }

    /**
     *
     * @Title: isPostCode
     * @Description: 是否为邮政编码
     * @param str  需要验证的字符串
     * @return  满足邮政编码格式返回true，否则返回false
     * @throws
     */
    public static boolean isPostCode(String str) {
        return isPatternMatch("^\\d{6}$", str);
    }

    /**
     *
     * @Title: isUserName
     * @Description: 是否常用用户名格式，只能是字母数字下划线，并且以字母开头(5-16位)
     * @param str 需要验证的字符串
     * @return 满足常用用户名格式返回true，否则返回false
     * @throws
     */
    public static boolean isUserName(String str) {
        return isPatternMatch("^[a-zA-Z]\\w{4,15}$", str);
    }

    /**
     *
     * @Title: isIP
     * @Description: 是否为IP地址
     * @param str 需要验证的字符串
     * @return 满足IP地址格式返回true，否则返回false
     * @throws
     */
    public static boolean isIP(String str) {
        return isPatternMatch(
                "^(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))$",
                str);
    }

    /**
     * 判断输入的字符串是否为纯汉字
     *
     * @author zhanglun
     * @param str
     *            需要验证的字符串
     * @return 如果是纯汉字返回true，否则返回false
     */
    public static boolean isChinese(String str) {
        return isPatternMatch("^[\\u4e00-\\u9fa5]+$", str);
    }

    /**
     *
     * @Title: isWeb
     * @Description: 判断输入的字符串是否为网址，只支持http、https这两种。注意：www.baidu.com也不算通过，必须加协议前缀
     * @param str  需要验证的字符串，如：http://www.baidu.com
     * @return 如果是合法的网址返回true，否则返回false
     * @throws
     */
    public static boolean isWeb(String str) {
        return isPatternMatch(
                "^([hH][tT]{2}[pP][sS]?)\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$",
                str);
    }

    /**
     *
     * @Title: isDate
     * @Description: 判断输入的字符串是否为日期格式
     * @param str 需要验证的字符串，如：2012-05-14、2012/05/6、2012.5.14、20120528
     * @return 如果是日期格式返回true，否则返回false
     * @throws
     */
    public static boolean isDate(String str) {
        return isPatternMatch(
                "^[1-9]\\d{3}([-|/|\\.])?((0[1-9])|([1-9])|(1[0-2]))([-|/|\\.])?(([0-2]\\d)|([1-9])|3[0-1])$",
                str);
    }

    /**
     *
     * @Title: isPatternMatch
     * @Description: 字符串str是否满足pattern正则表达式
     * @param pattern 正则表达式
     * @param str  需要验证的字符串
     * @return 如果满足，返回true，否则返回false
     * @throws
     */
    public static boolean isPatternMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            throw new RuntimeException();
        }
        Pattern p = Pattern.compile(pattern);
        return p.matcher(str).matches();
    }

    /**
     *
     * @Title: isIdCard
     * @Description: 是否合法的身份证号码格式，支持15位和18位两种格式
     * @param str  需要验证的字符串，如：612724199005061234
     * @return 合法的身份证号码返回true，否则返回false
     * @throws
     */
    public static boolean isIdCard(String str) {
        /*
         * 身份证15位编码规则：dddddd yymmdd xx p dddddd：6位地区编码 yymmdd:
         * 出生年(两位年)月日，如：910215 xx: 顺序编码，系统产生，无法确定 p: 性别，奇数为男，偶数为女
         *
         * 身份证18位编码规则：dddddd yyyymmdd xxx y dddddd：6位地区编码 yyyymmdd:
         * 出生年(四位年)月日，如：19910215 xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女 y:
         * 校验码，该位数值可通过前17位计算获得
         *
         * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
         * 2 ] 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
         * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
         * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
         */

        // 15位和18位身份证号码的正则表达式
        String regIdCard = "(^[1-9]\\d{7}((0[1-9])|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(([0-2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)";

        // 如果通过该验证，说明身份证格式正确，但准确性还需计算
        if (isPatternMatch(regIdCard, str)) {
            if (str.length() == 18) {
                int[] idCardWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                        8, 4, 2 }; // 将前17位加权因子保存在数组里
                int[] idCardY = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 }; // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
                int idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
                for (int i = 0; i < 17; i++) {
                    idCardWiSum += Integer.parseInt(str.substring(i, i + 1))
                            * idCardWi[i];
                }
                int idCardMod = idCardWiSum % 11;// 计算出校验码所在数组的位置
                String idCardLast = str.substring(17);// 得到最后一位身份证号码
                // 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if (idCardMod == 2) {
                    if (idCardLast.equalsIgnoreCase("X")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    // 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (idCardLast.equals(idCardY[idCardMod])) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @Title: getUTF8String
     * @Description: 把中文转化为UTF8格式
     * @param str 中文字符串
     * @return string
     * @throws
     */
    public static String getUTF8String(String str) {
        String utf8String = "";

        try {
            utf8String = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8String;
    }

    /**
     *
     * @Title: getRandom4Bit
     * @Description: 随机生成4位数字
     * @return 随机生成4位数字
     * @throws
     */
    public static int getRandom4Bit() {
        return (int) (Math.random() * 9000 + 1000); // 随机生成4位数字
    }

    /**
     *
     * @Title: getNonceStr
     * @Description: 得到指定长度的字符串
     * @param length 指定长度
     * @return
     * @throws
     */
    public static String getNonceStr(int length){
        String letter[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
                "P","Q","R","S","T","U","V","W","S","Y","Z"};

        String nonce_str="";
        for(int i = 0; i < length;i++){
            int num = new Random().nextInt(26);
            nonce_str+= letter[num];
        }
        return nonce_str;
    }

    /**
     *
     * @Title: getTelePhone
     * @Description: 国内电话，去掉前缀的+86  国际完全保留
     * @param telephone
     * @return
     * @throws
     */
    public static String getTelePhone(String telephone) {
        if( (telephone.length() == 14 && telephone.startsWith("+86")) ||
                (telephone.length() == 13 && telephone.startsWith("86")) ){
            telephone = telephone.replaceAll("\\+", ""); //去掉电话前边可能有+号
            telephone = telephone.substring(telephone.indexOf("86")+2);
        }
        return telephone;
    }

    /**
     *
     * @Title: getUUIDStr
     * @Description: 获得自动生成的UUID
     * @return
     * @throws
     */
    public static String getUUIDStr(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * JSON字符串特殊字符处理，比如：“\A1;1300”
     *
     * @param s
     * @return String
     */
    public static String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                // case '\n':
                // sb.append("\\n");
                // break;
                // case '\r':
                // sb.append("\\r");
                // break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 判定对象是否相同
     *
     * @param object1
     *            对象1
     * @param object2
     *            对象2
     * @return boolean
     */
    public static boolean equals(String object1, String object2) {
        if (object1 == null) {
            return false;
        }

        return object1.equals(object2);
    }

    /**
     *
     * @Title: createNonceStr
     * @Description: 创建
     * @return
     * @throws
     */
    public static String createNonceStr(){
        String letter[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","S","Y","Z"};
        String nonce_str="";
        for(int i=0;i<26;i++){
            int num = new Random().nextInt(26);
            nonce_str+= letter[num];
        }
        return nonce_str;
    }

    /**
     * str是空或者空字符串
     * @param s
     * @return
     */
    public static boolean strIsNullOrEmpty(String s) {
        return (null == s || s.trim().length() < 1);
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */

    public static String UnderlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s);
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1));
            }
        }
        return result.toString();
    }



    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        for(int i=0;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toLowerCase();
    }
}
