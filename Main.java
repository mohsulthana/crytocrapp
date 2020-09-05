/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author sulthan
 */
public class Main {
    static String IV = "AAAAAAAAAAAAAAAA";
    static String plaintext = "1234567890123456789009876543210987654321";
    static String encryptionKey = "0123456789abcdef";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("===JAVA===");
            System.out.println("Plain: " + plaintext);
            
            byte[] cipher = encrypt(plaintext, encryptionKey);
            
            System.out.print("cipher:  ");
            
            for (int i=0; i<cipher.length; i++)
                System.out.print(new Integer(cipher[i])+" ");
            
            System.out.println("");
            
            String decrypted = decrypt(cipher, encryptionKey);
            
            System.out.println("decrypt: " + decrypted);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }
    
    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }
}
