import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class SerialPortClass {

    private static final int numberOfSendValues = 3;
    private int baudRate;
    private int parity;
    private int flowControl;
    private int dataBits;
    private int stopBits;
    private int numberOfDataSend = 0;
    private byte[] previousData;
    private boolean firstDataSendFlag=true;

    private static final Logger LOGGER = Gui.getLOGGER();

    private SerialPort port;

    public void setPort(SerialPort comPort) {
        this.port = comPort;
    }

    public void openPort(){
        try {
            port.setComPortParameters(this.baudRate,this.dataBits,this.stopBits,this.parity);
            port.setFlowControl(this.flowControl);
            port.openPort();

        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.warning(sw.toString());
        }
    }

    public SerialPortClass(int baudRate, SerialPort port, int parity,int flowControl, int dataBits, int stopBits) {
        this.parity = parity;
        this.flowControl = flowControl;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.baudRate = baudRate;
        this.port = port;
        LOGGER.info("Init Serial, BaudRade: "+baudRate);
    }
    public SerialPortClass(){

    }

    public void sendDataToSTM(boolean diodeStmState,boolean diodeGreenState, boolean diodeRedState){
        try{

            Convert byteCompare = new Convert();
            byte[] dataToWrite = {1,1,numberOfSendValues,(byte) ledState(diodeStmState),(byte) ledState(diodeGreenState),
                                 (byte)ledState(diodeRedState)};
            if(firstDataSendFlag){
                previousData = dataToWrite;
                firstDataSendFlag = false;
            }
            if(byteCompare.compare(dataToWrite,previousData)){
                LOGGER.info("numberOfDataSend: "+numberOfDataSend+" Data Send: StmDiode: "+diodeStmState+
                            " greenDiode: "+diodeGreenState+" redDiode: "+diodeRedState);
            }
            port.writeBytes(dataToWrite,dataToWrite.length);
            previousData=dataToWrite;
            numberOfDataSend++;
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.warning(sw.toString());

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
