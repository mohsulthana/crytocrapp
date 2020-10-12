/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 *
 * @author sulthan
 */

public class MyThread extends Thread{
   public Thread t;
   public String threadName;
   public String plain;
   public String cipher;
   public String process;
   public String secretKey;
   public boolean status = true;
   
    MyThread( String name, String str, String p, String key) {
        threadName    = name;
        process       = p;
        secretKey     = key;
        System.out.println("Creating " +  threadName );
        
        // JIKA ENKRIPSI, MAKA STR ADALAH PLAINTEXT
        if("e".equals(process))
            plain = str;
        // JIKA DEKRIPSI, MAKA STR ADALAH CIPHERTEXT
        else
            cipher = str;
        
   }
   
   @Override
    public void run() {
        try {
            // DO ENCRYPTION
            if("e".equals(process))
                cipher = Securing.encrypt(plain, secretKey);
            //DO DECRYPTION
            else
                plain = Securing.decrypt(cipher, secretKey);
            
            status = false;
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e);
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