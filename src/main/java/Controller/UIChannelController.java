package Controller;

import Model.Client.Client;
import View.components.IView;

public class UIChannelController {
    private final IView view;
    private final Client reference;

    public UIChannelController(IView view, Client reference) {
        this.view = view;
        this.reference = reference;
    }


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
     * @param name the nickname to set.
     */

    public void setNickName(String name) {
        reference.setNickName(name);
        //we want to show the user that he is changed his/here name
        view.showNotification("Your nickname is now: " + name);
        System.out.println("clientName is" + name);

    }
}
