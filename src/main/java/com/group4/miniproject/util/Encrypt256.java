package com.group4.miniproject.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
@Convert
public class Encrypt256 implements AttributeConverter<String,String> {
        public static String alg = "AES/CBC/PKCS5Padding";
        private final String key = "abcdefghabcdefghabcdefghabcdefgh"; // 32byte
        private String iv = "0123456789abcdef"; // 16byte

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if(attribute == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(attribute.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(dbData);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

    // 암호화
//        public String encryptAES256(String text) throws Exception {
//            Cipher cipher = Cipher.getInstance(alg);
//            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//
//            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
//            return Base64.getEncoder().encodeToString(encrypted);
//        }
//
//        // 복호화
//        public String decryptAES256(String cipherText) throws Exception {
//            Cipher cipher = Cipher.getInstance(alg);
//            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
//
//            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
//            byte[] decrypted = cipher.doFinal(decodedBytes);
//            return new String(decrypted, "UTF-8");
//        }

}
