package com.secproject;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Arrays;
import java.util.Base64;

public class CryptoService {


    public String encrypt(String data, String password) throws Exception{
        byte[] salt = generateSalt();
        byte[] iv = generateIV();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKey key = deriveKey(password, salt);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

        byte[] encrypted = cipher.doFinal(data.getBytes());
        byte[] combined = new byte[salt.length + iv.length + encrypted.length];

        //copiar bytes de um array para outro em uma posicao especifica
        System.arraycopy(salt,      0, combined, 0,                        salt.length);
        System.arraycopy(iv,        0, combined, salt.length,              iv.length);
        System.arraycopy(encrypted, 0, combined, salt.length + iv.length,  encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String data, String password) throws Exception{
        int tam = Base64.getDecoder().decode(data).length;
        byte[] combined = Base64.getDecoder().decode(data);
        byte[] salt = Arrays.copyOfRange(combined, 0, 16);
        byte[] iv = Arrays.copyOfRange(combined, 16, 28);
        byte[] dados = Arrays.copyOfRange(combined, 28, tam);

        SecretKey key = deriveKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] decrypted = cipher.doFinal(dados);

        return new String(decrypted);
    }

    private byte[] generateSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return salt;
    }

    private byte[] generateIV(){
        SecureRandom srn = new SecureRandom();
        byte[] iv = new byte[12];
        srn.nextBytes(iv);

        return iv;
    }

    private SecretKey deriveKey(String password, byte[] salt) throws Exception{
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 310000, 256);
        SecretKey tmp = factory.generateSecret(spec);

        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}