/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

/**
 *
 * @author sulthan
 */

class PrintDemo {
    public void printCount() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Counter -- " + i);
            }
        } catch (Exception e) {
            System.out.println("Thread intteruped");
        }
    }
}

class DemoSynchronized extends Thread {
    private Thread t;
    private String threadName;
    PrintDemo PD;
    
    DemoSynchronized (String name, PrintDemo pd) {
        threadName = name;
        PD = pd;
    }
    
    public void run() {
        synchronized(PD) {
            PD.printCount();
        }
        System.out.println("Thread " + threadName + " exiting.");
    }
    
    public void start() {
        System.out.println("Starting " + threadName);
        
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

public class Synchronized {
    public static void main(String args[]) {
        PrintDemo PD = new PrintDemo();
        
        DemoSynchronized S1 = new DemoSynchronized("Thread-1", PD);
        DemoSynchronized S2 = new DemoSynchronized("Thread-2", PD);
        
        S1.start();
        S2.start();
        
        // wait for threads to end
        try {
            S1.join();
            S2.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
    
    //    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
//        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
//        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//        return cipher.doFinal(plainText.getBytes("UTF-8"));
//    }
    
//    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
//        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
//        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//        return new String(cipher.doFinal(cipherText), "UTF-8");
//    }
}