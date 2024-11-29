package backend.saveSystem;
import backend.Server.ChatChannel;
import backend.Server.saving.ChatSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatSaverTest {

    ChatChannel channelTest = new ChatChannel("testChannel","testpassword");
    ChatSaver saverTest = new ChatSaver(channelTest);

    @BeforeEach
    public void setUp() {
        channelTest = new ChatChannel("testChannel","testpassword");
        saverTest = new ChatSaver(channelTest);
    }

    @Test
    public void testUpdate() {
        saverTest.update("testUser", "testChannel", "testMessage");
        saverTest.update("testUser", "testChannel", "testMessage");
    }
    @Test
    public void readTest() {
        saverTest.update("testUser", "testChannel", "testMessage");
       // saverTest.update("testUser", "testChannel", "testMessage");
        StringBuilder test = saverTest.getChatHistory(channelTest);
        assertTrue(test.equals("testChannel"));
    }
}
