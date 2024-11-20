package backend.Messages;

import backend.toLater.User;

public class CreateChannelCommand extends Message implements Visitable {
    private String channelName;
    private String channelPassword;
    //TODO : Add channel settings ?

    public CreateChannelCommand(User user,String channelName){
     this(user,channelName,"");
    }

    public CreateChannelCommand(User user, String channelName, String channelPassword){
        super(user);
        this.channelName = channelName;
        this.channelPassword = channelPassword;
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    public String getChannelName() {
        return channelName;
    }
}
