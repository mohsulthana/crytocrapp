/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

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
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "SunJCE");
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
    
    Multithread(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    @Override
    public void run() {
        System.out.append("Running " + threadName + "\n");
        try {
            for (int i = 10; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted");
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
                
        Multithread R1 = new Multithread("Thread w");
        R1.start();
        
        Multithread R2 = new Multithread("Thread q");
        R2.start();
    }
}
