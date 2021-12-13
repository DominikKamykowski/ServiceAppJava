package Test;
import App.*;

import com.fazecast.jSerialComm.SerialPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SerialPortClassTest {

    @Test
    void serialPort(){
        //given
        int baudRate = 115200;
        int parity = 0;
        int flowControl = 0;
        int dataBits = 8;
        int stopBits = 1;

        SerialPortClass serial = new SerialPortClass(baudRate,SerialPort.getCommPort("COM5"),parity,flowControl,dataBits,stopBits);

        //when

        //then
        Assertions.assertEquals(baudRate,serial.getBaudRate());
        Assertions.assertEquals(parity,serial.getParity());
        Assertions.assertEquals(flowControl,serial.getFlowControl());
        Assertions.assertEquals(dataBits,serial.getDataBits());
        Assertions.assertEquals(stopBits,serial.getStopBits());
    }

}