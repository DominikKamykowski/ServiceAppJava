import com.fazecast.jSerialComm.SerialPort;

import java.util.logging.Logger;

public class Convert {
    private int returnValueFlowControl;
    private int returnValueBaudRate;


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
        switch(cbIndex) {
            case 0:
                returnValueBaudRate = 1200;
            case 1:
                returnValueBaudRate = 2400;
            case 2:
                returnValueBaudRate = 4800;
            case 3:
                returnValueBaudRate = 9600;
            case 4:
                returnValueBaudRate = 19200;
            case 5:
                returnValueBaudRate = 38400;
            case 6:
                returnValueBaudRate = 57600;
            case 7:
                returnValueBaudRate = 76800;
            case 8:
                returnValueBaudRate = 115200;

            default: {
                returnValueBaudRate = 115200;
                LOGGER.info("returnValueBaudRate = default");
            };
        }return returnValueBaudRate;
    }

    public boolean compare(byte[] byte1, byte[] byte2){
        if(byte1[2]-byte2[2]==0){
            if(byte1[3]-byte2[3]==0){
                if(byte1[4]-byte2[4]==0){
                    if(byte1[5]-byte2[5]==0)return false;
                    else return true;
                }else return true;
            }else return true;
        } else return true;
    }
}
