import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

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
    private JButton connectButton;
    private JPanel panel1;
    private JSpinner baudRateSpinner;
    private JComboBox comboBox1;
    private JEditorPane editorPane1;

    //Timer
    private boolean connectFlag = false;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            sendData();
        }
    };

    //Other
    SerialPortClass serial = new SerialPortClass((int)baudRateSpinner.getValue(), (SerialPort) comboBox1.getSelectedItem());


    public Gui() {

        comboBoxFill();

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Listener{");

                Login login = new Login();
                if(!login.tryToLogIn(loginField.getText(), passField.getText())){
                    loginCorrect();
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

    private void comboBoxFill() {
        SerialPort[] ports = SerialPortClass.fillComboBox();
        for(SerialPort port : ports){
            comboBox1.addItem(port);
        }
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
    public void sendData(){
        serial.sendDataToSTM(stmDiodeCheckBox.isSelected(),greenDiodeCheckBox.isSelected(),redDiodeCheckBox.isSelected());
    }

    private void loginCorrect(){
        serial = new SerialPortClass((int)baudRateSpinner.getValue(), (SerialPort) comboBox1.getSelectedItem());
        serial.setPort(((SerialPort) comboBox1.getSelectedItem()));
        serial.openPort();
        editorPane1.setText("Make sure You set the properly Baud Rate!");
        if(!connectFlag){
            timer.schedule(task,100,50);
            connectFlag = true;
        }
    }
}