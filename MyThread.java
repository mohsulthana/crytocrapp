/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

/**
 *
 * @author sulthan
 */

public class MyThread extends Thread{
   public Thread t;
   public String threadName;
   public String plain;
   public byte[] cipher;
   public String process;
   SecretKey secret;
   public boolean status = true;
   
   private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
   private static final int TAG_LENGTH_BIT = 128;
   private static final int IV_LENGTH_BYTE = 12;
   private static final int AES_KEY_BIT = 128;
   
    MyThread(String name, String str, String p, SecretKey key) {
        threadName    = name;
        process       = p;
        secret        = key;
        System.out.println("Creating " +  threadName );
        
        // JIKA ENKRIPSI, MAKA STR ADALAH PLAINTEXT
        if("e".equals(process))
            plain = str;
        // JIKA DEKRIPSI, MAKA STR ADALAH CIPHERTEXT
        else
            cipher = str.getBytes();
        
   }
   
   @Override
    public void run() {
        byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);
        try {
            // DO ENCRYPTION
            if("e".equals(process)) {
                cipher = EncryptorAesGcm.encryptWithPrefixIV(plain.getBytes(UTF_8), secret, iv);
            }
            //DO DECRYPTION
            else
            {
                plain = EncryptorAesGcm.decryptWithPrefixIV(cipher, secret);
            }
            
            status = false;
        } catch (Exception ex) {
           Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   @Override
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}