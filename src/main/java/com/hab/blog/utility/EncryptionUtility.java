package com.hab.blog.utility;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtility {

    // Method to generate an AES key using KeyGenerator
    public static SecretKeySpec generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); // Get an instance of KeyGenerator for AES
        keyGenerator.init(256); // Initialize the key generator with key size of 256 bits for AES-256
        SecretKey secretKey = keyGenerator.generateKey(); // Generate the AES key
        return new SecretKeySpec(secretKey.getEncoded(), "AES"); // Create a SecretKeySpec from the generated key
    }

    // Method to generate a secure random Initialization Vector (IV)
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16]; // Create a byte array to hold the IV, AES uses a block size of 16 bytes
        new SecureRandom().nextBytes(iv); // Generate random bytes and store them into the iv array
        return new IvParameterSpec(iv); // Create an IvParameterSpec from the random bytes
    }

    // Method to encrypt a string value using AES key and IV
    public static String encrypt(String value, SecretKeySpec key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Get a Cipher instance using AES with CBC mode and PKCS5 padding
            cipher.init(Cipher.ENCRYPT_MODE, key, iv); // Initialize the cipher for encryption with the key and IV

            byte[] encrypted = cipher.doFinal(value.getBytes()); // Encrypt the value and obtain the encrypted byte array
            return Base64.getEncoder().encodeToString(encrypted); // Encode the encrypted bytes to Base64 and return as string
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Method to decrypt a string value using AES key and IV
    public static String decrypt(String encrypted, SecretKeySpec key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Get a Cipher instance using AES with CBC mode and PKCS5 padding
            cipher.init(Cipher.DECRYPT_MODE, key, iv); // Initialize the cipher for decryption with the key and IV

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted)); // Decode Base64 encrypted string and decrypt it
            return new String(original); // Return the original string from the decrypted byte array
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Main method to test encryption and decryption
    public static void main(String[] args) {
        try {
            String originalString = "key-to-encrypt"; // Original string to be encrypted
            System.out.println("Original: " + originalString);

            SecretKeySpec secretKey = generateKey(); // Generate the AES key
            IvParameterSpec iv = generateIv(); // Generate the IV

            String encryptedString = encrypt(originalString, secretKey, iv); // Encrypt the original string
            System.out.println("Encrypted: " + encryptedString);

            String decryptedString = decrypt(encryptedString, secretKey, iv); // Decrypt the encrypted string
            System.out.println("Decrypted: " + decryptedString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
