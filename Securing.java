/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;

/**
 *
 * @author sulthan
 */
class Securing {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    
    public static String encrypt(String strToEncrypt, String secret) throws java.security.InvalidKeyException 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    
    public static String decrypt(String strToDecrypt, String secret) throws java.security.InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    public static List<MyThread> doEncrypt(List<String> text, String secret) {
        List<MyThread> ThreadEncrypt = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            ThreadEncrypt.add(new MyThread("Thread-" + (i+1), text.get(i), "e", secret));
            ThreadEncrypt.get(i).start();
        }
        return ThreadEncrypt;
    }
    
    public static List<MyThread> doDecrypt(List<MyThread> ThreadEncrypt, String secret) {
        List<MyThread> ThreadDecrypt = new ArrayList<>();
        for (int i = 0; i < ThreadEncrypt.size(); i++) {
            ThreadDecrypt.add(new MyThread("Thread-" + (i+1), ThreadEncrypt.get(i).cipher, "d", secret));
            ThreadDecrypt.get(i).start();
        }
        return ThreadDecrypt;
    }
    
    static List<String> splitText(String plaintext, int jumlahThread) {
            List<String> teks       = new ArrayList<>();
            int bagian              = plaintext.length() / jumlahThread;
            int start               = 0;

            for (int i = 0; i < jumlahThread - 1; i++) {
                int end = start + bagian;
                teks.add(plaintext.substring(start, end));
                start = end;
            }
            teks.add(plaintext.substring(start));
            return teks;
        }
}