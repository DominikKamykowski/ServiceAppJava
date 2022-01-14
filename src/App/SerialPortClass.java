package App;

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
        /*! \brief Metoda otwierająca port szeregowy.
         *
         */
        try {
            port.setComPortParameters(this.baudRate,this.dataBits,this.stopBits,this.parity);
            port.setFlowControl(this.flowControl);
            port.openPort();
        }
        catch(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.warning(sw.toString());
        }
    }

    public SerialPortClass(int baudRate, SerialPort port, int parity,int flowControl, int dataBits, int stopBits) {
        /*! \brief Konstruktor ustawiający parametry portu szeregowego.
         *
         */
        this.parity = parity;
        this.flowControl = flowControl;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.baudRate = baudRate;
        this.port = port;
        LOGGER.info("Init Serial, BaudRade: "+baudRate);
    }
    public SerialPortClass(){
        /*! \brief Konstruktor bezparametrowy.
         *
         */
    }

    public void sendDataToSTM(boolean diodeStmState,boolean diodeGreenState, boolean diodeRedState){
        /*! \brief Metoda wysyłająca ramkę danych do portu szeregowego.
         *
         */
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
        /*! \brief Metoda statyczna zwracająca listę dostępnych portów.
         *
         */
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            LOGGER.info(port.getDescriptivePortName());
        }return ports;
    }

    public int getParity() {
        return parity;
    }

    public int getStopBits() {
        return stopBits;
    }

    public int getFlowControl() {
        return flowControl;
    }

    public int getDataBits() {
        return dataBits;
    }

    public int getBaudRate() {
        return baudRate;
    }

}
