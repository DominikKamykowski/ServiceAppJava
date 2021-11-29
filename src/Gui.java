import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.*;
import java.time.LocalDateTime;

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
    private JComboBox cbPorts;
    private JEditorPane editorPane1;
    private JComboBox cbStopBits;
    private JSpinner spinnerDataBits;
    private JComboBox cbParity;
    private JComboBox cbFlowControl;

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
    SerialPortClass serial = new SerialPortClass((int)baudRateSpinner.getValue(), (SerialPort) cbPorts.getSelectedItem(),
                                                  cbParity.getSelectedIndex(),cbFlowControl.getSelectedIndex(),
                                                  (int)spinnerDataBits.getValue(),cbStopBits.getSelectedIndex()+1);

    public static Logger getLOGGER() {
        return LOGGER;
    }

    private static final Logger LOGGER = Logger.getLogger("MyLog");
    FileHandler fileHandler;


    public Gui() {

        try {
            fileHandler = new FileHandler("C:/Users/Dominik/IdeaProjects/ServiceApplicationJava/logs/"+java.time.LocalDate.now()+".log",true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            LOGGER.info("Init Gui");
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialization();

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Listener{");
                LOGGER.info("Button clicked.");
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
        cbStopBits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop Bit Index: "+cbStopBits.getSelectedIndex());
            }
        });
    }

    private void initialization(){
        comboBoxFill();

        initValues();
    }

    private void initValues() {
        baudRateSpinner.setValue(115200);
    }

    private void comboBoxFill() {
        SerialPort[] ports = SerialPortClass.fillSerialComboBox();
        for(SerialPort port : ports){
            cbPorts.addItem(port);
        }
        cbStopBits.addItem(1);
        cbStopBits.addItem(1.5);
        cbStopBits.addItem(2);

    }

    public static void main (String[] args){
        try{
            JFrame frame = new JFrame("Service Application");
            frame.setContentPane(new Gui().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(700,400);
            frame.setVisible(true);



        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void sendData(){
        serial.sendDataToSTM(stmDiodeCheckBox.isSelected(),greenDiodeCheckBox.isSelected(),redDiodeCheckBox.isSelected());

    }

    private void loginCorrect(){
        serial = new SerialPortClass((int)baudRateSpinner.getValue(), (SerialPort) cbPorts.getSelectedItem(),
                                    cbParity.getSelectedIndex(),cbFlowControl.getSelectedIndex(),
                                    (int)spinnerDataBits.getValue(),cbStopBits.getSelectedIndex()+1);
        serial.setPort(((SerialPort) cbPorts.getSelectedItem()));
        serial.openPort();
        editorPane1.setText("Make sure You set the properly Baud Rate!");
        if(!connectFlag){
            timer.schedule(task,100,50);
            connectFlag = true;
        }
    }
}