import com.fazecast.jSerialComm.SerialPort;

public class SerialPortClass {

    private static final int numberOfSendValues = 3;
    private int baudRate;

    private SerialPort port;

    public void setPort(SerialPort comPort) {
        this.port = comPort;
    }

    public void openPort(){
        try {
            port.setBaudRate(this.baudRate);
            port.openPort();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public SerialPortClass(int baudRate, SerialPort port) {
        this.baudRate = baudRate;
        this.port = port;
    }

    public void sendDataToSTM(boolean diodeStmState,boolean diodeGreenState, boolean diodeRedState){
        try{
            byte[] dataToWrite = {1,1,numberOfSendValues,(byte) ledState(diodeStmState),(byte) ledState(diodeGreenState),
                                 (byte)ledState(diodeRedState)};
            port.writeBytes(dataToWrite,dataToWrite.length);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int ledState(boolean diodeState){
        if(diodeState) return 11;
        else return 10;
    }

    public static SerialPort[] fillComboBox(){
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port :ports) {
            System.out.println(port.getSystemPortName());
        }return ports;
    }
}
