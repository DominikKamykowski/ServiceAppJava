public class Login {
    private final String login = "ZMT";
    private final String password = "!QAZ2wsx";

    private int numberOfBadLogin=0;

    public Login() {
    }

    public boolean tryToLogIn(String login,String password){
        if(login==this.login && password==this.password){
            System.out.println("Succesfull Login!");
            return true;
        }else{
            return false;
        }
    }

}
