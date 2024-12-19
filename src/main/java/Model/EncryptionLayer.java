package Model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * This class provides methods to encrypt and decrypt messages using the AES encryption algorithm. The idea of usage is to
 * use the class staticly to generate a key, encrypt a message and decrypt a message.
 * a solution to insure a layer of security is that we create a key per channel or per user.
 * This way, if a key is compromised, only the messages of that channel or user are compromised.
 */

public class EncryptionLayer {
    /*
    AES is a symmetric encryption algorithm and a block cipher.
    The former means that it uses the same key to encrypt and decrypt data.
    The sender and the receiver must both know -- and use -- the same secret encryption key.
     */
    private static final String ALGORITHM = "AES";
    private static final int KEYSIZE = 128;

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEYSIZE);
        return keyGen.generateKey();
    }

    public static String encrypt(Object encryptedData, SecretKey key) throws Exception {
        if(encryptedData != null) {

            Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(encryptedData.toString().getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);}

        else {
            throw new Exception("The message is null, you can't encrypt a null message");
        }
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}