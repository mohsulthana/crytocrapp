/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rijndael;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sulthan
 */
public class Rijndael {
    public static String teks;
    public static SecretKey key;
    
    public Rijndael(String plaintext, SecretKey secretKey) {
        teks = plaintext;
        key  = secretKey;
    } 

    static void waitThreadEnd(List<MyThread> R) {
        // UNTUK TUNGGU THREAD SELESAI
        for (int i = 0; i < R.size(); i++) {
            while(R.get(i).status == true){
                System.out.print("");
            }
        }
    }
    
    private static List<String> doSplit(String plaintext, int jumlah_thread){
        
        List<String> teks_part = new ArrayList<>(); 
        int split              = plaintext.length()/jumlah_thread;
        int start              = 0;
        for (int i = 0; i < jumlah_thread-1; i++) {
            int end = start+split;
            teks_part.add(plaintext.substring(start, end));
            start = end;
        }
        teks_part.add(plaintext.substring(start));
        return teks_part;
    }
    
    public static void main(String[] args) throws InterruptedException {
        // INIT VARIABLE
        int jumlah_thread   = 3;
        
        // SPLIT TEKS
        List<String> teks_part = doSplit(teks, jumlah_thread);
        
        // CREATE THREAD FOR ENCRYPTION
        List<MyThread> ThreadEncrypt = Securing.doEncrypt(teks_part, key);
        // TUNGGU THREAD SELESAI
        waitThreadEnd(ThreadEncrypt);
        
        
        // HASIL ENKRIPSI
        for (int i = 0; i < jumlah_thread; i++)
            System.out.println("CIPHER: "+ ThreadEncrypt.get(i).cipher);
        
        
        // CREATE THREAD FOR DECRYPTION
        List<MyThread> ThreadDecrypt = Securing.doDecrypt(ThreadEncrypt, key);
        // TUNGGU THREAD SELESAI
        waitThreadEnd(ThreadDecrypt);
        
        
        // HASIL DEKRIPSI
        for (int i = 0; i < jumlah_thread; i++)
            System.out.println("PLAIN: "+ThreadDecrypt.get(i).plain);
    }
}