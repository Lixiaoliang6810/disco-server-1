package com.miner.disco.basic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by lubycoder@163.com on 2018/3/16.
 */
public class Encrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encrypt.class);

    private static final String[] TOKEN_SALT = new String[]{"s", "t", "y", "j", "k", "P", "c", "D", "v", "x"};

    public static String getTokenSalt(long arg) {
        StringBuffer sb = new StringBuffer();
        String temp = String.valueOf(arg);
        for (int i = 0; i < temp.length(); i++) {
            sb.append(TOKEN_SALT[Integer.parseInt(temp.substring(i, i + 1))]);
        }
        return String.format("%10s", sb.toString()).replace(" ", "A");
    }

    /**
     * MD5加密
     */
    public static class MD5 {

        private static final String ENCRYPT_MD5 = "MD5";

        public static String encrypt(File file) {
            InputStream in = null;
            try {
                MessageDigest digest = MessageDigest.getInstance(ENCRYPT_MD5);
                in = new FileInputStream(file);
                byte[] buffer = new byte[1024];//10k
                int readLen;
                while ((readLen = in.read(buffer)) != -1) {
                    digest.update(buffer, 0, readLen);
                }
                return toHex(digest.digest());
            } catch (Exception e) {
                LOGGER.error("MD5 encrypt ex", e);
                throw new CryptoException("MD5 encrypt ex", e);
            } finally {
                try {
                    assert in != null;
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public static String encrypt(String text) {
            try {
                MessageDigest digest = MessageDigest.getInstance(ENCRYPT_MD5);
                digest.update(text.getBytes(Charset.forName("UTF-8")));
                return toHex(digest.digest());
            } catch (Exception e) {
                LOGGER.error("MD5 encrypt ex", e);
                throw new CryptoException("MD5 encrypt ex", e);
            }
        }

        public static String encrypt(byte[] bytes) {
            try {
                MessageDigest digest = MessageDigest.getInstance(ENCRYPT_MD5);
                digest.update(bytes);
                return toHex(digest.digest());
            } catch (Exception e) {
                LOGGER.error("MD5 encrypt ex", e);
                throw new CryptoException("MD5 encrypt ex", e);
            }
        }

        public static String encrypt(String text, Charset charset) {
            try {
                MessageDigest digest = MessageDigest.getInstance(ENCRYPT_MD5);
                digest.update(text.getBytes(charset));
                return toHex(digest.digest());
            } catch (Exception e) {
                LOGGER.error("MD5 encrypt ex", e);
                throw new CryptoException("MD5 encrypt ex", e);
            }
        }
    }

    /**
     * HmacSha 加密
     */
    public static class HMACSHA {

        private static final String HMAC_SHA1 = "HmacSHA1";

        public static String hmacSha1(String data, String encryptKey) {
            SecretKeySpec signingKey = new SecretKeySpec(encryptKey.getBytes(Charset.forName("UTF-8")), HMAC_SHA1);
            try {
                Mac mac = Mac.getInstance(HMAC_SHA1);
                mac.init(signingKey);
                mac.update(data.getBytes(Charset.forName("UTF-8")));
                return toHex(mac.doFinal());
            } catch (Exception e) {
                LOGGER.error("hmacSha1 encrypt ex", e);
                throw new CryptoException("hmacSha1 encrypt ex", e);
            }
        }
    }

    /**
     * sha 加密
     */
    public static class SHA {

        private static final String SHA1 = "SHA-1";

        private static final String SHA256 = "SHA-256";

        public static String sha1(String data) {
            try {
                MessageDigest digest = MessageDigest.getInstance(SHA1);
                return toHex(digest.digest(data.getBytes(Charset.forName("UTF-8"))));
            } catch (Exception e) {
                LOGGER.error("sha1 encrypt ex");
                throw new CryptoException("sha1 decrypt ex", e);
            }
        }

        public static String sha256(String str) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance(SHA256);
                byte[] hash = messageDigest.digest(str.getBytes(Charset.forName("UTF-8")));
                return toHex(hash);
            } catch (NoSuchAlgorithmException e) {
                throw new CryptoException("sha256 decrypt ex", e);
            }
        }
    }

    /**
     * AES 加密
     */
    public static class AES {

        private static final String KEY_ALGORITHM = "AES";
        private static final String KEY_ALGORITHM_PADDING = "AES/CBC/PKCS7Padding";

        public static SecretKey getSecretKey(byte[] seed) throws Exception {
            SecureRandom secureRandom = new SecureRandom(seed);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(secureRandom);
            return keyGenerator.generateKey();
        }

        public static byte[] encrypt(byte[] data, byte[] encryptKey, byte[] iv) {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey, KEY_ALGORITHM);
            return encrypt(data, zeroIv, key);
        }

        public static byte[] decrypt(byte[] data, byte[] decryptKey, byte[] iv) {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey, KEY_ALGORITHM);
            return decrypt(data, zeroIv, key);
        }

        private static byte[] encrypt(byte[] data, IvParameterSpec zeroIv, SecretKeySpec keySpec) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PADDING);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, zeroIv);
                return cipher.doFinal(data);
            } catch (Exception e) {
                LOGGER.error("AES encrypt ex, iv={}, key={}", Arrays.toString(zeroIv.getIV()), Arrays.toString(keySpec.getEncoded()), e);
                throw new CryptoException("AES encrypt ex", e);
            }
        }

        private static byte[] decrypt(byte[] data, IvParameterSpec zeroIv, SecretKeySpec keySpec) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PADDING);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, zeroIv);
                return cipher.doFinal(data);
            } catch (Exception e) {
                LOGGER.error("AES decrypt ex, iv={}, key={}", Arrays.toString(zeroIv.getIV()), Arrays.toString(keySpec.getEncoded()), e);
                throw new CryptoException("AES decrypt ex", e);
            }
        }
    }

    /**
     * RC4加密
     */
    public static class RC4 {

        public static String decry_RC4(byte[] data, String key) {
            if (data == null || key == null) {
                return null;
            }
            return asString(RC4Base(data, key));
        }

        public static String decry_RC4(String data, String key) {
            if (data == null || key == null) {
                return null;
            }
            return new String(RC4Base(HexString2Bytes(data), key));
        }

        public static byte[] encry_RC4_byte(String data, String key) {
            if (data == null || key == null) {
                return null;
            }
            byte b_data[] = data.getBytes();
            return RC4Base(b_data, key);
        }

        public static String encry_RC4_string(String data, String key) {
            if (data == null || key == null) {
                return null;
            }
            return toHexString(asString(encry_RC4_byte(data, key)));
        }

        private static String asString(byte[] buf) {
            StringBuffer strbuf = new StringBuffer(buf.length);
            for (int i = 0; i < buf.length; i++) {
                strbuf.append((char) buf[i]);
            }
            return strbuf.toString();
        }

        private static byte[] initKey(String aKey) {
            byte[] b_key = aKey.getBytes();
            byte state[] = new byte[256];

            for (int i = 0; i < 256; i++) {
                state[i] = (byte) i;
            }
            int index1 = 0;
            int index2 = 0;
            if (b_key == null || b_key.length == 0) {
                return null;
            }
            for (int i = 0; i < 256; i++) {
                index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
                byte tmp = state[i];
                state[i] = state[index2];
                state[index2] = tmp;
                index1 = (index1 + 1) % b_key.length;
            }
            return state;
        }

        private static String toHexString(String s) {
            String str = "";
            for (int i = 0; i < s.length(); i++) {
                int ch = (int) s.charAt(i);
                String s4 = Integer.toHexString(ch & 0xFF);
                if (s4.length() == 1) {
                    s4 = '0' + s4;
                }
                str = str + s4;
            }
            return str;// 0x表示十六进制
        }

        private static byte[] HexString2Bytes(String src) {
            int size = src.length();
            byte[] ret = new byte[size / 2];
            byte[] tmp = src.getBytes();
            for (int i = 0; i < size / 2; i++) {
                ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
            }
            return ret;
        }

        private static byte uniteBytes(byte src0, byte src1) {
            char _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
            _b0 = (char) (_b0 << 4);
            char _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
            byte ret = (byte) (_b0 ^ _b1);
            return ret;
        }

        private static byte[] RC4Base(byte[] input, String mKkey) {
            int x = 0;
            int y = 0;
            byte key[] = initKey(mKkey);
            int xorIndex;
            byte[] result = new byte[input.length];

            for (int i = 0; i < input.length; i++) {
                x = (x + 1) & 0xff;
                y = ((key[x] & 0xff) + y) & 0xff;
                byte tmp = key[x];
                key[x] = key[y];
                key[y] = tmp;
                xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
                result[i] = (byte) (input[i] ^ key[xorIndex]);
            }
            return result;
        }
    }


    static class CryptoException extends RuntimeException {

        private static final long serialVersionUID = 368277451733324220L;

        CryptoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static String toHex(byte[] bytes) {
        StringBuilder buffer = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            buffer.append(Character.forDigit((aByte & 240) >> 4, 16));
            buffer.append(Character.forDigit(aByte & 15, 16));
        }
        return buffer.toString();
    }

}
