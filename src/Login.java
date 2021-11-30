public class Login {
    private final String login = "";
    private final String password = "";

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
