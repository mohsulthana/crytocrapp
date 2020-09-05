/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static rijndael.Main.IV;
import static rijndael.Main.decrypt;
import static rijndael.Main.encrypt;
import static rijndael.Main.encryptionKey;
import static rijndael.Main.plaintext;

/**
 *
 * @author sulthan
 */
class Cryptography {
    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }
    
    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }
}


class Multithread implements Runnable {
    private Thread t;
    private String threadName;
    private String plainText;
    
    Multithread(String name, String plain) {
        threadName = name;
        plainText = plain;
        System.out.println("Creating " + threadName);
    }

    @Override
    public void run() {
        System.out.append("Running " + threadName + "\n");
        try {
            
            Cryptography crypto = new Cryptography();
            byte[] chip = crypto.encrypt(plainText, encryptionKey);
            
            System.out.print("Chiper :  ");
            for (int i=0; i<chip.length; i++)
                System.out.print(new Integer(chip[i])+" ");
            System.out.println("");
            
            String decrypted = decrypt(chip, encryptionKey);
            System.out.println("decrypt: " + decrypted);
            
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted");
        } catch (Exception ex) {
            Logger.getLogger(Multithread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Thread " + threadName + " exiting.");
    }
    
    public void start () {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}

public class StartThread {
    public static void main(String args[]) {
        try {
            System.out.println("===JAVA===");
            System.out.println("Plain: " + plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int plainLength = plaintext.length();
        
        String firstPart = plaintext.substring(0, plainLength / 2);
        String secondPart = plaintext.substring(plainLength / 2, plainLength);
                
        Multithread R1 = new Multithread("Thread first", firstPart);
        R1.start();
        
        Multithread R2 = new Multithread("Thread second", secondPart);
        R2.start();
    }
}
