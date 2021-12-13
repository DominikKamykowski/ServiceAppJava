package App;

import com.fazecast.jSerialComm.SerialPort;

import java.util.logging.Logger;

public class Convert {
    private int returnValueFlowControl;
    private int returnValueBaudRate;
    private final int startFrameOffset = 2;
    private final int frameLenght = 6;

    private static final Logger LOGGER = Gui.getLOGGER();

    public int flowControlConvert(int cbIndex){
        switch(cbIndex) {
            case 0:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_DISABLED;
            case 1:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_RTS_ENABLED;
            case 2:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_CTS_ENABLED;
            case 3:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_DSR_ENABLED;
            case 4:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_DTR_ENABLED;
            case 5:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED;
            case 6:
                returnValueFlowControl = SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED;
            default: {
                returnValueFlowControl = 0;
                LOGGER.info("flowControlConvert = default");
                };
        }return returnValueFlowControl;
    }

    public int baudRateConvert(int cbIndex){
            switch (cbIndex) {
                case 0:
                    returnValueBaudRate = 1200;
                    break;
                case 1:
                    returnValueBaudRate = 2400;
                    break;
                case 2:
                    returnValueBaudRate = 4800;
                    break;
                case 3:
                    returnValueBaudRate = 9600;
                    break;
                case 4:
                    returnValueBaudRate = 19200;
                    break;
                case 5:
                    returnValueBaudRate = 38400;
                    break;
                case 6:
                    returnValueBaudRate = 57600;
                    break;
                case 7:
                    returnValueBaudRate = 76800;
                    break;
                case 8:
                    returnValueBaudRate = 115200;
                    break;
                default: {
                    LOGGER.info("returnValueBaudRate = default");
                    throw  new IllegalArgumentException("Invalid cb index!");
                }
            }


        return returnValueBaudRate;
    }

    public boolean compare(byte[] byte1, byte[] byte2){
        for(int i = startFrameOffset; i < frameLenght; i++){
            if(byte1[i] - byte2[i] != 0) return true;
        }return false;
    }
}
