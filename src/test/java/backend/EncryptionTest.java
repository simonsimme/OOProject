package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class EncryptionTest {
    EncryptionLayer encryptionLayer = new EncryptionLayer();
    SecretKey key;


    @BeforeEach
    void setUp() throws Exception {
        key = EncryptionLayer.generateKey();

    }
@Test
 public void testEncryption() {
        try {
            String message = "My name is test and i hate programs without tests";
            Object encryptedMessage = encryptionLayer.encrypt(message, key);
            String decryptedMessage = encryptionLayer.decrypt(encryptedMessage, key);
            assert message.equals(decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this test will be fixed with a if statment cheacking if the object is null. At this time it will throw an exception the message is null
    @Test
    public void testEncryptionNullCase() {
        try {
            String message = null;
            Object encryptedMessage = encryptionLayer.encrypt(message, key);
            String decryptedMessage = encryptionLayer.decrypt(encryptedMessage, key);
            assert message.equals(decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
