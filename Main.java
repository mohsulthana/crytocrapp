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
}
