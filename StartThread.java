/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static rijndael.Main.IV;

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
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }
}


class Multithread implements Runnable {
    private Thread t;
    private String threadName;
    protected String plainText;
    protected String encKey;
    
    Multithread(String name, String plain, String encryptionKey) {
        threadName = name;
        plainText = plain;
        encKey = encryptionKey;
        
        System.out.println("Creating " + threadName);
    }

    Multithread() {
        
    }

    @Override
    public void run() {
        System.out.append("Running " + threadName + "\n");
        try {
            
            Cryptography crypto = new Cryptography();
            System.out.println(encKey);
            byte[] chip = crypto.encrypt(plainText, encKey);
            
            System.out.print("Chiper :  ");
            for (int i=0; i<chip.length; i++)
                System.out.print(new Integer(chip[i])+" ");
            System.out.println("");
            
            String decrypted = crypto.decrypt(chip, encKey);
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

public class StartThread  {
    public static void main(String args[]) {
        Multithread multi = new Multithread();
        try {
            System.out.println("===JAVA===");
            System.out.println("Plain: " + multi.plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int plainLength = multi.plainText.length();
        String firstPart = multi.plainText.substring(0, plainLength / 2);
        String secondPart = multi.plainText.substring(plainLength / 2, plainLength);
        
//        Single thread
//        Multithread single = new Multithread("Single", plaintext);
                
//        Multithread
//        Multithread firstThread = new Multithread("Thread first", firstPart);
//        firstThread.start();
//        
//        Multithread secondThread = new Multithread("Thread second", secondPart);
//        secondThread.start();
        
//        3 Thread
//            Multithread firstThread = new Multithread("Thread first", firstPart, multi.encKey);
//            firstThread.start();
//
//            Multithread secondThread = new Multithread("Thread second", secondPart, multi.encryptionKey);
//            secondThread.start();
//            
//            Multithread thirdThread = new Multithread("Thread third", firstPart, multi.encryptionKey);
//            thirdThread.start();
//            
//            Multithread fourthThread = new Multithread("Thread fourth", secondPart, multi.encryptionKey);
//            fourthThread.start();
    }
    
    public StartThread(String plaintext, int part) {
        int strSize = plaintext.length();
        int partSize;
        
        if (strSize % part != 0)
        {
            System.out.println("Invalid Input: String size is not divisible by n"); 
            return;
        }
        
        partSize = strSize / part;
        
        for (int i = 0; i < strSize; i++) {
            if (i % partSize == 0)
                System.out.println();
            System.out.println(plaintext.charAt(i));
        }
    }
}
