import backend.client_model.Client;
import backend.client_model.ClientCommunicationManager;
import backend.client_model.ClientChannelRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import backend.client_model.ClientObserver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientTest {

    private Client client;
    private ClientCommunicationManager mockCommunicationManager;
    private ClientChannelRecord mockChannelRecord;

    @BeforeEach
    void setUp() {
        mockCommunicationManager = mock(ClientCommunicationManager.class);
        mockChannelRecord = mock(ClientChannelRecord.class);
        client = new Client("localhost", 8080) {

            protected ClientCommunicationManager createCommunicationManager(String address, int port, ClientChannelRecord channelRecord, List<ClientObserver> observers) {
                return mockCommunicationManager;
            }


            protected ClientChannelRecord createChannelRecord() {
                return mockChannelRecord;
            }
        };
    }

    @Test
    void joinChannelTest() {
        String channelName = "testChannel";
        String password = "password";

        client.joinChannel(channelName, password);

        verify(mockCommunicationManager, times(1)).joinChannel(client.getUserName(), channelName, password);
    }
}