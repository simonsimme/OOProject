package Model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * The {@code EncryptionLayer} class provides methods to encrypt and decrypt messages using the AES encryption algorithm.
 * This class is designed to be used statically to generate a secret key, encrypt data, and decrypt data. The key can be
 * specific to a channel or user, ensuring that if a key is compromised, only the messages of the affected channel or user
 * are exposed.
 * <p>
 * AES (Advanced Encryption Standard) is a symmetric encryption algorithm, meaning the same key is used for both encryption
 * and decryption. Both the sender and receiver need to share the same encryption key securely.
 * </p>
 */

public class EncryptionLayer {
    /*
    AES is a symmetric encryption algorithm and a block cipher.
    The former means that it uses the same key to encrypt and decrypt data.
    The sender and the receiver must both know -- and use -- the same secret encryption key.
     */
    private static final String ALGORITHM = "AES";
    private static final int KEYSIZE = 128;

    /**
     * Generates a new AES secret key with the specified key size.
     *
     * @return a {@link SecretKey} object representing the generated AES key.
     * @throws Exception if the key generation process fails.
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEYSIZE);
        return keyGen.generateKey();
    }

    /**
     * Encrypts the specified data using the given AES secret key.
     * The data is first converted to a byte array, encrypted, and then encoded in Base64 to return a string representation.
     *
     * @param encryptedData the data to be encrypted (can be any object).
     * @param key           the AES secret key to be used for encryption.
     * @return a Base64 encoded string representing the encrypted data.
     * @throws Exception if the encryption process fails or the data is null.
     */
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

    /**
     * Decrypts the specified Base64 encoded encrypted data using the given AES secret key.
     *
     * @param encryptedData the Base64 encoded string representing the encrypted data.
     * @param key           the AES secret key to be used for decryption.
     * @return the decrypted message as a string.
     * @throws Exception if the decryption process fails.
     */
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}