package View.CLI;

import Model.Client.Client;

import java.io.InputStream;
import java.util.Scanner;

public class CLIController implements Runnable{
    private Client client;
    private CLIView view;
    private InputStream in;
    private Scanner scanner;
    private boolean running = true;

    public CLIController(InputStream in, CLIView view, Client client){
        this.in = in;
        this.view = view;
        this.client = client;
        this.scanner = new Scanner(in);
    }

    @Override
    public void run() {
        view.displayMessage("Type /help for help.");
        while(running){
            String msg = scanner.nextLine();
             processInput(msg);

        }
    }

    private void processInput(String input){
        String sep = " ";
        if(input.startsWith("/createChannel")){
            String[]inputList = input.split(sep);
            if(inputList.length == 3){
                client.createChannel(inputList[1],inputList[2]);
            } else{
                view.displayMessage("Syntax error, try : /createChannel name password");
            }
        } else if (input.startsWith("/joinChannel")){
            String[]inputList = input.split(sep);
            if(inputList.length == 3){
                client.joinChannel(inputList[1],inputList[2]);
            } else{
                view.displayMessage("Syntax error, try : /joinChannel name password");
            }
        } else if(input.startsWith("/switchChannel")){
            String[] inputList = input.split(sep);
            if(inputList.length == 2){
                client.switchChannel(inputList[1]);
            } else{
                view.displayMessage("Syntax error, try : /switchChannel name");
            }
        }else if(input.startsWith("/help")){
            view.displayMessage(helpMenu());
        }else if(input.startsWith("/leave")){
            String[]inputList = input.split(sep);
            if(inputList.length == 1){
                client.leaveChannel();
            } else{
                view.displayMessage("Syntax error, try : /leave");
            }
        } else {
            client.sendMessage(input);
        }
    }

    private String helpMenu(){
        return "Enter text to send a message in the current channel \n" +
                "Commands start with /\n" +
                "List of implemented commands:\n" +
                "/createChannel name password\n" +
                "/joinChannel name password\n" +
                "/switchChannel name : Switch current channel\n" +
                "/leave : Leaves current channel\n" +
                "----------------------------------";
    }
}
