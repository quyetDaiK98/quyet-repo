package com.personal.requestmanagement.utils;

import java.util.Random;

public class StringUtil {
    protected static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static final char[] SECURE_CHARS = { 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9' };

    public static String blank = "empty";

    public static boolean isEmpty(String str) {
        if (null != str && !"".equals(str)) {
            return false;
        }
        return true;
    }

    public static String byteArrayIdToStringId(byte[] bytes) {
        return new String(bytesToHex(bytes));
    }

    public static byte[] stringIdToByteArrayId(String id) {
        if (!StringUtil.isEmpty(id)) {
            return hexToBytes(id);
        }
        return null;
    }

    public static char[] bytesToHex(byte[] raw) {
        if (raw != null) {
            int length = raw.length;
            char[] hex = new char[length * 2];
            for (int i = 0; i < length; i++) {
                int value = (raw[i] + 256) % 256;
                int highIndex = value >> 4;
                int lowIndex = value & 0x0f;
                hex[i * 2 + 0] = HEX_CHARS[highIndex];
                hex[i * 2 + 1] = HEX_CHARS[lowIndex];
            }
            return hex;
        } else
            return "".toCharArray();
    }

    public static byte[] hexToBytes(char[] hex) {
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            raw[i] = (byte) value;
        }
        return raw;
    }

    public static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
    }

    public static boolean validateEmail(String email) {
        // String regex =
        // "^[_A-Za-z0-9-\\.]+[_A-Za-z0-9-\\.]+@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String regex = "^[^\\s\\t@]+@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regex);
    }

    public static boolean validateEmailPolicy(String email) {
        // String regex =
        // "^[_A-Za-z0-9-\\.]+[_A-Za-z0-9-\\.]+@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String regex2=".*(m1s.vn)$";
        if (email.matches(regex2))
        {
            return false;
        }
        String regex3="^([Aa][Dd][Mm][Ii][Nn]).*";
        if (email.matches(regex3))
        {
            return false;
        }

        String regex = "^[^\\s\\t@]+@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regex);
    }


    public static boolean validatePhone(String phone) {
        String regex = "[1-9][0-9]{10,14}";
        return phone.matches(regex);
    }

    public static boolean validatePhoneVn(String phone) {
        String regex = "^0\\d{9,10}$";
        return phone.matches(regex);
    }
//    public static boolean validateMobileVn(String phone){
//        String regex = "^0[3,5,7,8,9]\\d{8}$";
//    }

    public static boolean validateEmailPhone(String phone) {
        String regexEmail = "^[^\\s\\t@]+@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//		String regexPhone = "[1-9][0-9]{10,14}";
        String regexPhone = "^\\+{0,1}\\d{7,20}$";
        return (phone.matches(regexEmail)||phone.matches(regexPhone));
    }


    public static int length(String str){
        if(str==null|| "".equals(str)){
            return 0;
        }
        return str.length();
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public  static  double randomInRange(double min , double max){
        double random = (new Random().nextDouble())*2 -1;
        double result = min + (random * (max - min));

        return  result;
    }

    public static int randomNumber(int min, int max){
        Random random = new Random();
        int rand = random.nextInt(max-min+1) + min;
        return rand;
    }

    /* check if string can convert to int java*/
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isExistComma(String s){
        if(s==null){
            return true;
        }
        int i = s.indexOf(",");
        if(i<0){
            return false;
        }

        return true;
    }

    public static boolean isComplicatedPassword(String password)
    {
        String pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

//		^                 # start-of-string
//		(?=.*[0-9])       # a digit must occur at least once
//		(?=.*[a-z])       # a lower case letter must occur at least once
//		(?=.*[A-Z])       # an upper case letter must occur at least once
//		(?=.*[@#$%^&+=])  # a special character must occur at least once
//		(?=\S+$)          # no whitespace allowed in the entire string
//		.{8,}             # anything, at least eight places though
//		$                 # end-of-string

        return password.matches(pattern);
    }

    public static Integer versionCompare(String version1, String version2) {
        //version example: 1.7.0.150909
        String[] vals1 = version1.split("\\.");
        String[] vals2 = version2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i]))
        {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length)
        {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        else
        {
            return Integer.signum(vals1.length - vals2.length);
        }
    }

    public static String cutString(String content, int len){
        if(!isEmpty(content) && content.length() > len){
            return content.substring(0, len);
        }
        return content;
    }

}
