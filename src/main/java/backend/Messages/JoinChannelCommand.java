package backend.Messages;

import backend.toLater.User;

public class JoinChannelCommand extends Message implements Visitable {

    private String channelName;
    private String password;

    public JoinChannelCommand(User user, String channelName, String password){
        super(user);
        this.channelName = channelName;
        this.password = password;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getPassword(){
        return password;
    }

    public void accept(Visitor visitor) {

    }
}
