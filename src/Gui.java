import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Gui {
    //Login
    private final int maxBadLoginNumber = 3;
    private int numberOfBadLogin;

    //GUI
    private JCheckBox stmDiodeCheckBox;
    private JCheckBox redDiodeCheckBox;
    private JCheckBox greenDiodeCheckBox;
    private JTextField loginField;
    private JButton connectButton;
    private JPanel panel1;
    private JComboBox<SerialPort> cbPorts;
    private JComboBox<Number> cbStopBits;
    private JComboBox<String> cbParity;
    private JComboBox<String> cbFlowControl;
    private JPasswordField passwordField;
    private JComboBox<Integer> cbBaudRate;
    private JComboBox<Integer> cbDataBits;

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
    SerialPortClass serial = new SerialPortClass();//(int)cbBaudRate.getSelectedItem(), (SerialPort) cbPorts.getSelectedItem(),
         //                                         cbParity.getSelectedIndex(),cbFlowControl.getSelectedIndex(),
         //                                         (int)cbDataBits.getSelectedItem(),cbStopBits.getSelectedIndex()+1);

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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.warning(sw.toString());
        }

        comboBoxFill();

        connectButton.addActionListener(e -> {
            System.out.println(cbDataBits.getSelectedItem());
            System.out.println(cbBaudRate.getSelectedItem());
            System.out.println(cbFlowControl.getSelectedItem());
            System.out.println(cbParity.getSelectedItem());


            LOGGER.info("connectButton clicked.");
            Login login = new Login();
            if(!login.tryToLogIn(loginField.getText(), new String((passwordField.getPassword())))){
                 loginCorrect();
            }else if((!(login.tryToLogIn(loginField.getText(), new String((passwordField.getPassword())))&&numberOfBadLogin<maxBadLoginNumber))){
                System.out.println("Login failed! Try number: "+(numberOfBadLogin+1));
                numberOfBadLogin++;
            }else{
                System.exit(0);
            }
        });
    }

    private void comboBoxFill() {
        SerialPort[] ports = SerialPortClass.fillSerialComboBox();
        for(SerialPort port : ports){
            cbPorts.addItem(port);
        }
        cbStopBits.addItem(1);
        cbStopBits.addItem(1.5);
        cbStopBits.addItem(2);

        cbParity.addItem("NO_PARITY");
        cbParity.addItem("ODD_PARITY");
        cbParity.addItem("EVEN_PARITY");
        cbParity.addItem("MARK_PARITY");
        cbParity.addItem("SPACE_PARITY");

        cbFlowControl.addItem("FLOW_CONTROL_DISABLED");
        cbFlowControl.addItem("FLOW_CONTROL_RTS");
        cbFlowControl.addItem("FLOW_CONTROL_CTS");
        cbFlowControl.addItem("FLOW_CONTROL_DSR");
        cbFlowControl.addItem("FLOW_CONTROL_DTR");
        cbFlowControl.addItem("FLOW_CONTROL_XONXOFF_IN");
        cbFlowControl.addItem("FLOW_CONTROL_XONXOFF_OUT");

        for (int i = 1;i<=8;i++) {
            cbDataBits.addItem(i);
        }

        for(int i = 1200;i<=28800;i*=2){
            cbBaudRate.addItem(i);
        }
        cbBaudRate.addItem(38400);
        cbBaudRate.addItem(57600);
        cbBaudRate.addItem(76800);
        cbBaudRate.addItem(115200);
    }

    public static void main (String[] args){
        try{
            JFrame frame1 = new JFrame("Test");
            frame1.setContentPane(new Gui().panel1);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.pack();
            frame1.setSize(700,400);
            frame1.setVisible(true);

        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.warning(sw.toString());
        }
    }

    public void sendData(){
        serial.sendDataToSTM(stmDiodeCheckBox.isSelected(),greenDiodeCheckBox.isSelected(),redDiodeCheckBox.isSelected());
    }

    private void loginCorrect(){
        Convert convert = new Convert();

        serial = new SerialPortClass(convert.baudRateConvert(cbBaudRate.getSelectedIndex()), (SerialPort) cbPorts.getSelectedItem(),
                                         cbParity.getSelectedIndex(),convert.flowControlConvert(cbFlowControl.getSelectedIndex()),
                                        (int)cbDataBits.getSelectedItem(),cbStopBits.getSelectedIndex()+1);
        serial.setPort(((SerialPort) cbPorts.getSelectedItem()));
        serial.openPort();
        if(!connectFlag){
            timer.schedule(task,100,50);
            connectFlag = true;
        }
    }
}