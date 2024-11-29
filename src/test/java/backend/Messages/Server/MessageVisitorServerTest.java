package backend.Messages.Server;

import backend.Server.ClientHandler;
import backend.Server.ServerVisitor;
import backend.Server.Server;
import org.junit.jupiter.api.BeforeEach;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * I am not totatlly sure if this is the correct way to test this class but
 * but will look into it more.
 * It verifies the proper handling of server messages by the server.
 */
public class MessageVisitorServerTest {
        ClientHandler clientHandler;
        ServerVisitor serverVisitor;
         LeaveChannelCommand leaveChannelCommand;
         Server server;
         Socket testClientSocket;

    @BeforeEach
        public void setUp() {
            Server.createServerInstance(8080);
            testClientSocket = new Socket();
            //this is not correct but it is just for testing purposes
            clientHandler = new ClientHandler(testClientSocket, server);
            serverVisitor = new ServerVisitor(clientHandler);

            //also just for testing purposes


            // fake names just for testing
            leaveChannelCommand = new LeaveChannelCommand("user1", "channelName");
        }
        /*  Null error
        @Test
        public void testHandleLeaveChannelCommand() {
            messageVisitorServer.handle(leaveChannelCommand);
            assertEquals("channelName", leaveChannelCommand.getChannelName());
        }*/

}
