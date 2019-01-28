package controllers.elisisplay.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import play.exceptions.UnexpectedException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.UUID;

/**
 * Some cryptography utils.
 */
public class Crypto {

    /**
     * Encryption Method Md5 string value.
     */
    private static final String hexMd5 = "hexmd5";

    /**
     * Encryption Method Base64 string value.
     */
    private static final String base64 = "base64";

    /**
     * Encryption Method SHA1 string value.
     */
    private static final String hexSha1 = "hexsha1";

    /**
     * Default Encryption method used string value.
     */
    private static final String defaultEncryptMethodStr = hexMd5;

    /**
     * Default Encryption method used enum value.
     */
    private static final EncryptMethod defaultEncryptMethod = EncryptMethod.HEXMD5;

    private static String encrypt(String toEncrypt, String encryptMethod) {
        switch (encryptMethod.toLowerCase()) {
            case hexMd5 :
                return hexMD5(toEncrypt);
            case base64 :
                return encodeBASE64(toEncrypt);
            case hexSha1 :
                return hexSHA1(toEncrypt);
            default:
                return encrypt(toEncrypt, defaultEncryptMethodStr);
        }
    }

    public static String encrypt(String toEncrypt, EncryptMethod hashMethod) {
        return (encrypt(toEncrypt, hashMethod.name().toLowerCase()));
    }

    public static String encrypt(String toEncrypt) {
        return (encrypt(toEncrypt, defaultEncryptMethodStr));
    }

    public static String encrypt(Object toEncrypt) {
        return (encrypt(toEncrypt.toString(), defaultEncryptMethodStr));
    }

    public static String encrypt(Object toEncrypt, EncryptMethod hashMethod) {
        return (encrypt(toEncrypt.toString(), hashMethod.name().toLowerCase()));
    }

    private static boolean isHash(String hash, String original, String hashMethod) {
        switch (hashMethod.toLowerCase()) {
            case hexMd5 :
                return Objects.equals(hash, hexMD5(original));
            case base64 :
                return Objects.equals(hash, encodeBASE64(original));
            case hexSha1 :
                return Objects.equals(hash, hexSHA1(original));
            default:
                return isHash(hash, original, defaultEncryptMethodStr);
        }
    }

    public static boolean isHash(String hash, String original, EncryptMethod encryptMethod) {
        return (isHash(hash, original, encryptMethod.name().toLowerCase()));
    }

    public static boolean isHash(Object hash, String original) {
        return (isHash(hash.toString(), original, defaultEncryptMethodStr));
    }

    public static boolean isHash(String hash, String original) {
        return (isHash(hash, original, defaultEncryptMethodStr));
    }

    public static boolean isHash(Object hash, String original, EncryptMethod encryptMethod) {
        return (isHash(hash.toString(), original, encryptMethod.name().toLowerCase()));
    }

    /**
     * Generate an UUID String
     *
     * @return an UUID String
     */
    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate an UUID String hash.
     *
     * @return an UUID String
     */
    public static String UUIDHash() {
        return encrypt(UUID.randomUUID().toString());
    }

    /**
     * Generate an UUID String hash.
     *
     * @return an UUID String
     */
    public static String UUIDHash(EncryptMethod encryptMethod) {
        return encrypt(UUID.randomUUID().toString(), encryptMethod);
    }

    /**
     * Encode a String to base64
     *
     * @param value The plain String
     * @return The base64 encoded String
     */
    public static String encodeBASE64(String value) {
        try {
            return new String(Base64.encodeBase64(value.getBytes("utf-8")));
        } catch (UnsupportedEncodingException ex) {
            throw new UnexpectedException(ex);
        }
    }

    /**
     * Encode binary data to base64
     *
     * @param value The binary data
     * @return The base64 encoded String
     */
    public static String encodeBASE64(byte[] value) {
        return new String(Base64.encodeBase64(value));
    }

    /**
     * Decode a base64 value
     *
     * @param value The base64 encoded String
     * @return decoded binary data
     */
    public static byte[] decodeBASE64(String value) {
        try {
            return Base64.decodeBase64(value.getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new UnexpectedException(ex);
        }
    }

    /**
     * Build an hexadecimal MD5 hash for a String
     *
     * @param value The String to hash
     * @return An hexadecimal Hash
     */
    public static String hexMD5(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            return byteToHexString(digest);
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }

    /**
     * Build an hexadecimal SHA1 hash for a String
     *
     * @param value The String to hash
     * @return An hexadecimal Hash
     */
    public static String hexSHA1(String value) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes("utf-8"));
            byte[] digest = md.digest();
            return byteToHexString(digest);
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }

    /**
     * Write a byte array as hexadecimal String.
     *
     * @param bytes byte array
     * @return The hexadecimal String
     */
    public static String byteToHexString(byte[] bytes) {
        return String.valueOf(Hex.encodeHex(bytes));
    }

    /**
     * Transform an hexadecimal String to a byte array.
     *
     * @param hexString Hexadecimal string to transform
     * @return The byte array
     */
    public static byte[] hexStringToByte(String hexString) {
        try {
            return Hex.decodeHex(hexString.toCharArray());
        } catch (DecoderException e) {
            throw new UnexpectedException(e);
        }
    }
}
