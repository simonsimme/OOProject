package Model.Server;

import Model.EncryptionLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link EncryptionLayer} class
 * Tests the functionality of the methods in the {@code Model.EncryptionLayer} class
 */
public class EncryptionTest {
    EncryptionLayer encryptionLayer;
    SecretKey key;

    /**
     * Set up method that runs before each test.
     * Initializes the EncryptionLayer instance and generates a secret key.
     *
     * @throws Exception if key generation fails
     */
    @BeforeEach
    void setUp() throws Exception {
        this.encryptionLayer = new EncryptionLayer();
        this.key = EncryptionLayer.generateKey();

    }

    /**
     * Test case for encrypting and decrypting a message.
     * Ensures that the decrypted message matches the original message after encryption.
     *
     */
    @Test
    public void testEncryption() {
        try {
            String message = "My name is test and i hate programs without tests";
            String encryptedMessage = EncryptionLayer.encrypt(message, key);
            String decryptedMessage = EncryptionLayer.decrypt(encryptedMessage, key);
            assert message.equals(decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that encrypting and decrypting null message throws an Exception.
     */
    @Test
    public void testEncryptionNullCase() {
        String message = null;
        // Using assertThrows to check for the expected exception
        assertThrows(Exception.class, () -> {
        String encryptedMessage = EncryptionLayer.encrypt(message, key);
        String decryptedMessage = EncryptionLayer.decrypt(encryptedMessage, key);
        assert message.equals(decryptedMessage); // This line won't be reached if the exception is thrown
        });
    }
}
