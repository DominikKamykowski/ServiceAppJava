import java.util.logging.Logger;

public class Login {
    private final String login = "ZMT";
    private final String password = "!QAZ2wsx";

    private static final Logger LOGGER = Gui.getLOGGER();

    public Login() {
    }

    public boolean tryToLogIn(String login,String password){
        if(login==this.login && password==this.password){
            System.out.println("Succesfull Login!");
            return true;
        }else{
            LOGGER.info("Login: "+login+" Password: "+password);
            return false;
        }
    }

}
