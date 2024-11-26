package backend;

import backend.Messages.Server.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MessageCommandTesting {
    CreateChannelCommand command;

    @BeforeEach
    void setCreateChannelCommand (){
        command = new CreateChannelCommand("user1", "channel1");
    }

    @Test
    void testDefaultConstructor() {
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        assertEquals("", command.getChannelPassword());
    }

    @Test
    void testParameterizedConstructor() {
        command = new CreateChannelCommand("user1", "channel1", "password123");
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        assertEquals("password123", command.getChannelPassword());
    }
    @Test
    void testParameterizedConstructor2() {
        //Breake the test
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        //this should fail beacuse the password object is not initialized
        assertNotEquals("password123", command.getChannelPassword());
    }


}
