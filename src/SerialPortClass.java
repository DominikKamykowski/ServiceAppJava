import com.fazecast.jSerialComm.SerialPort;

import java.io.Serial;

public class SerialPortClass {

    private static final int numberOfSendValues = 3;
    private int baudRate;
    private int parity;
    private int flowControl;
    private int dataBits;
    private int stopBits;


    private SerialPort port;

    public void setPort(SerialPort comPort) {
        this.port = comPort;
    }

    public void openPort(){
        try {
            port.setComPortParameters(this.baudRate,this.dataBits,this.stopBits,this.parity);
            port.openPort();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public SerialPortClass(int baudRate, SerialPort port, int parity,int flowControl, int dataBits, int stopBits) {
        this.parity = parity;
        this.flowControl = flowControl;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
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

    public static SerialPort[] fillSerialComboBox(){
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
        }return ports;
        
    }

}
