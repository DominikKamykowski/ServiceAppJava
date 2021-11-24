import com.fazecast.jSerialComm.SerialPort;


public class SerialPortClass {

    private static final int numberOfSendValues = 3;
    private int baudRate;
    private String comPort;

    private SerialPort port;

    public void setComPort(String comPort) {
        this.comPort = comPort;
    }

    public SerialPort getPort() {
        return port;
    }

    public void openPort(){
        try {
            port.setBaudRate(this.baudRate);
            port = SerialPort.getCommPort(comPort);
            port.openPort();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public SerialPortClass(int baudRate, String comPort) {
        this.baudRate = baudRate;
        this.comPort = comPort;
        port = SerialPort.getCommPort(comPort);
    }

    private void sendDataToSTM(boolean diodeStmState,boolean diodeGreenState, boolean diodeRedState){
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
}
