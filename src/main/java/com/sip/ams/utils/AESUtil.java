package com.sip.ams.utils;

import javax.crypto.Cipher; 
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    // Secret key (you should store this securely, in an environment variable or a secure vault)
    private static final String SECRET_KEY = "mysecretkey12345";  // 128 bit key (16 characters)

    // Encrypt the email
    public static String encrypt(String email) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedEmail = cipher.doFinal(email.getBytes());
        return Base64.getEncoder().encodeToString(encryptedEmail);
    }

    // Decrypt the email
    public static String decrypt(String encryptedEmail) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedEmail = Base64.getDecoder().decode(encryptedEmail);
        byte[] decryptedEmail = cipher.doFinal(decodedEmail);
        return new String(decryptedEmail);
    }
}
