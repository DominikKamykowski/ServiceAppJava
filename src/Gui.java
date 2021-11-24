import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    //Login
    private final int maxBadLoginNumber = 3;
    private int numberOfBadLogin;

    //GUI
    private JCheckBox stmDiodeCheckBox;
    private JCheckBox redDiodeCheckBox;
    private JCheckBox greenDiodeCheckBox;
    private JTextField loginField;
    private JTextField passField;
    private JSpinner comSpinner;
    private JButton connectButton;
    private JPanel panel1;
    private JSpinner baudRateSpinner;

    //Other
    SerialPortClass serial = new SerialPortClass(115200, "COM"+comSpinner.getValue().toString());


    public Gui() {
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Listener{");

                Login login = new Login();
                if(!login.tryToLogIn(loginField.getText(), passField.getText())){
                    serial = new SerialPortClass(115200,"COM"+comSpinner.getValue().toString());
                    serial.setComPort("COM"+comSpinner.getValue().toString());
                    System.out.println(serial.getPort().getSystemPortName());
                    serial.openPort();
                }else if((!(login.tryToLogIn(loginField.getText(), passField.getText())))&&numberOfBadLogin<maxBadLoginNumber){
                    System.out.println("Login failed! Try number: "+(numberOfBadLogin+1));
                    numberOfBadLogin++;
                }else{
                    System.exit(0);
                }

                System.out.println("}");
            }
        });
    }

    public static void main (String[] args){
        try{
            JFrame frame = new JFrame("Service Application");
            frame.setContentPane(new Gui().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(400,400);
            frame.setVisible(true);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
