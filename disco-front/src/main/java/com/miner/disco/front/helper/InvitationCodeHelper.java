package com.miner.disco.front.helper;

/**
 * @author Created by lubycoder@163.com 2018/7/17
 */
public class InvitationCodeHelper {

    private static final String[] SEED = new String[]{"D", "5", "F", "C", "E", "G", "3", "H", "P", "A", "4", "B", "1", "N", "O", "Q", "I", "J", "2", "R", "S", "T", "U", "V", "6", "7", "M", "W", "X", "8", "9", "K", "L", "Y", "Z"};
    private static final int CODE_LENGTH = 6;

    private InvitationCodeHelper() {

    }

    public static String encode(long uid) {
        StringBuilder code = new StringBuilder();
        while (uid > 0) {
            long mod = uid % 35;
            uid = (uid - mod) / 35;
            code.insert(0, SEED[Long.valueOf(mod).intValue()]);
        }
        if (code.length() >= CODE_LENGTH) {
            return String.format("U%s", code.toString());
        }
        code = new StringBuilder(String.format("U%" + String.valueOf(CODE_LENGTH - code.length()) + "s%s", " ", code.toString()).replaceAll(" ", "0"));
        return code.toString();
    }

    public static long decode(String code) {
        StringBuilder c = new StringBuilder(code.substring(code.lastIndexOf("0") + 1)).reverse();
        long num = 0;
        for (int i = 0; i < c.toString().length(); i++) {
            String ts = c.toString().substring(i, i + 1);
            num += arrayIndexOf(SEED, ts) * (long) Math.pow(35, i);
        }
        return num;
    }

    private static long arrayIndexOf(String[] strs, String arg) {
        for (int i = 0; i < strs.length; i++) {
            if (arg.equals(strs[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String e = InvitationCodeHelper.encode(41441843749L);
        System.out.println(e);
        System.out.println(InvitationCodeHelper.decode(e));
    }

}
