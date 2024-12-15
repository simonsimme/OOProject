package Controller;

import Model.Client.Client;
import View.components.IView;

/**
 * The {@code UIChannelController} class handles user interactions related to channel creation and joining,
 * as well as setting the nickname of the client.
 * <p>
 * This class interacts with the {@link IView} interface to gather user inputs and notify the user,
 * and with the {@link Client} class to execute channel-related operations.
 * </p>
 */
public class UIChannelController {
    private final IView view;
    private final Client reference;

    /**
     * Constructs a new {@code UIChannelController} with the given view and client reference.
     *
     * @param view     the view interface to interact with the user
     * @param reference the client instance to perform channel-related operations
     */
    public UIChannelController(IView view, Client reference) {
        this.view = view;
        this.reference = reference;
    }

    /**
     * Joins an existing channel. This method requests the user to input a channel name and password,
     * and calls the corresponding method on the {@link Client} to join the channel.
     * <p>
     * If the user input is invalid (empty channel name or cancelled input), an exception is thrown.
     * </p>
     */
    public void joinChannel() {
        try{
            String[] channelNameAndPassword = view.getChannelNameAndPasswordInput("Join");
            if (channelNameAndPassword == null) {
                throw new Exception("STOPPED");
            }
            String channelName = channelNameAndPassword[0];
            String password = channelNameAndPassword[1];
            System.out.println(password);
            if(channelName.isEmpty()){
                throw new Exception("Channel name cannot be empty");
            }
            reference.joinChannel(channelName, password); //TODO fix so if wrong pass not continue
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Creates a new channel with the given name and password.
     */
    public void createChannel() {
        try{
            String[] channelNameAndPassword = view.getChannelNameAndPasswordInput("Create");
            if (channelNameAndPassword == null) {
                throw new Exception("STOPPED");
            }
            String channelName = channelNameAndPassword[0];
            String password = channelNameAndPassword[1];
            if(channelName.isEmpty()){
                throw new Exception("Channel name cannot be empty");
            }
            reference.createChannel(channelName, password);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }

    /**
     * Sets the nickname for the client.
     * <p>
     * This method updates the client's nickname using the {@link Client} class, and then notifies
     * the user of the change.
     * </p>
     *
     * @param name the new nickname to set for the client
     */
    public void setNickName(String name) {
        reference.setNickName(name);
        view.showNotification("Your nickname is now: " + name);
        System.out.println("clientName is" + name);
    }
}
