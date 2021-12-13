package App;

import java.util.logging.Logger;

public class Login {
    private final String login = "ZMT";
    private final String password = "ZMT";

    private static final Logger LOGGER = Gui.getLOGGER();

    public Login() {
    }

    public boolean tryToLogIn(String login,String password){
        if(login.equals(this.login) && password.equals(this.password)){
            System.out.println("Succesfull Login!");
            return true;
        }else{
            LOGGER.info("Login: "+login+" Password: "+password);
            return false;
        }
    }
}
