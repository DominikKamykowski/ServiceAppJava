import com.fazecast.jSerialComm.SerialPort;


public class SerialPortClass {

    private static final int numberOfSendValues = 3;

    SerialPort port = SerialPort.getCommPort("COM6");


    public SerialPortClass(int baudRate){
        System.out.println("SerialPort BaudRate: "+baudRate);
        try{
            port.openPort();
            port.setBaudRate(baudRate);
            System.out.println("Port name: "+port.getSystemPortName());

        }catch(Exception e){
            e.printStackTrace();
        }
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
