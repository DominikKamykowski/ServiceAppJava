import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.io.IOException;
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
    private JTextField passField;
    private JButton connectButton;
    private JPanel panel1;
    private JSpinner baudRateSpinner;
    private JComboBox<SerialPort> cbPorts;
    private JComboBox<Number> cbStopBits;
    private JSpinner spinnerDataBits;
    private JComboBox<Integer> cbParity;
    private JComboBox<Integer> cbFlowControl;

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

        connectButton.addActionListener(e -> {

            LOGGER.info("connectButton clicked.");
            Login login = new Login();
            if(!login.tryToLogIn(loginField.getText(), passField.getText())){
                loginCorrect();
            }else if((!(login.tryToLogIn(loginField.getText(), passField.getText())))&&numberOfBadLogin<maxBadLoginNumber){
                System.out.println("Login failed! Try number: "+(numberOfBadLogin+1));
                numberOfBadLogin++;
            }else{
                System.exit(0);
            }
        });
        cbStopBits.addActionListener(e -> System.out.println("Stop Bit Index: "+cbStopBits.getSelectedIndex()));
        stmDiodeCheckBox.addActionListener(e -> {
            if(stmDiodeCheckBox.isSelected()){
                LOGGER.info("stmDiodeCheckBox selected");
            }else{
                LOGGER.info("stmDiodeCheckBox unselected");
            }
        });
    }

    private void initialization(){
        comboBoxFill();

        initValues();
    }

    private void initValues() {
        baudRateSpinner.setValue(115200);
        spinnerDataBits.setValue(8);
    }

    private void comboBoxFill() {
        SerialPort[] ports = SerialPortClass.fillSerialComboBox();
        for(SerialPort port : ports){
            cbPorts.addItem(port);
        }
        cbStopBits.addItem(1);
        cbStopBits.addItem(1.5);
        cbStopBits.addItem(2);

        cbParity.addItem(SerialPort.NO_PARITY);
        cbParity.addItem(SerialPort.ODD_PARITY);
        cbParity.addItem(SerialPort.EVEN_PARITY);
        cbParity.addItem(SerialPort.MARK_PARITY);
        cbParity.addItem(SerialPort.SPACE_PARITY);

        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_DISABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_RTS_ENABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_CTS_ENABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_DSR_ENABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_DTR_ENABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED);
        cbFlowControl.addItem(SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED);
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
        if(!connectFlag){
            timer.schedule(task,100,50);
            connectFlag = true;
        }
    }
}