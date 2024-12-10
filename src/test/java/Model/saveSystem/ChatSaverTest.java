package Model.saveSystem;
import Model.Server.ChatChannel;
import Model.Server.saving.ChatSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatSaverTest {

    ChatChannel channelTest = new ChatChannel("test1Channel","testpassword");
    ChatSaver saverTest = new ChatSaver(channelTest);

    @BeforeEach
    public void setUp() {
        channelTest = new ChatChannel("testChannel","testpassword");
        saverTest = new ChatSaver(channelTest);
    }

    @Test
    public void testUpdate() {
        //saverTest.update("testChannel", "testMessage");
      //  saverTest.update("testUser", "testChannel", "testMessage");
    }
    @Test
    public void readTest() {
        //saverTest.update("testUser", "testChannel", "testMessage");
       // saverTest.update("testUser", "testChannel", "testMessage");
        StringBuilder test = saverTest.getChatHistory(channelTest);
        String result = test.toString();
        //There is no logic behind clearing the testChannel.txt, this document is just appended everytime update is called
        assertTrue(result.contains("testChannel"));
    }
}
