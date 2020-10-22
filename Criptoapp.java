package rijndael;


import java.util.ArrayList;
import java.util.List;
import rijndael.MyThread;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sulthan
 */
public class Criptoapp {
    String plaintext;
    int jlh_thread;
    
    private static void waitThreadEnd(List<MyThread> R) {
        // UNTUK TUNGGU THREAD SELESAI
        for (int i = 0; i < R.size(); i++) {
            while(R.get(i).status == true){
                System.out.print("");
            }
        }
    }
    
    static List<String> doSplit(String plaintext, int jumlah_thread){
        
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
}
