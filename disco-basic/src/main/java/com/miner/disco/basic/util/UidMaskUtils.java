package com.miner.disco.basic.util;

import java.util.Random;

public class UidMaskUtils {

    private static final char[] BASE = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char SUFFIX_CHAR = '0';

    private static final int BIN_LEN = BASE.length;

    private static final int CODE_LEN = 6;

    public static String idToCode(Long id) {
        char[] buf = new char[BIN_LEN];
        int charPos = BIN_LEN;
        while (id / BIN_LEN > 0) {
            int index = (int) (id % BIN_LEN);
            buf[--charPos] = BASE[index];
            id /= BIN_LEN;
        }

        buf[--charPos] = BASE[(int) (id % BIN_LEN)];
        String result = new String(buf, charPos, BIN_LEN - charPos);
        int len = result.length();
        if (len < CODE_LEN) {
            StringBuilder sb = new StringBuilder();
            sb.append(SUFFIX_CHAR);
            Random random = new Random();
            for (int i = 0; i < CODE_LEN - len - 1; i++) {
                sb.append(BASE[random.nextInt(BIN_LEN)]);
            }

            result += sb.toString();
        }

        return result;
    }

    public static Long codeToId(String code) {
        char[] charArray = code.toCharArray();
        long result = 0L;
        for (int i = 0; i < charArray.length; i++) {
            int index = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (charArray[i] == BASE[j]) {
                    index = j;
                    break;
                }
            }

            if (charArray[i] == SUFFIX_CHAR) {
                break;
            }

            if (i > 0) {
                result = result * BIN_LEN + index;
            } else {
                result = index;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(UidMaskUtils.idToCode(3L));
    }

}
