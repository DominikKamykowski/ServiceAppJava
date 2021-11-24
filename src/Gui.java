import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    private JCheckBox stmDiodeCheckBox;
    private JCheckBox redDiodeCheckBox;
    private JCheckBox greenDiodeCheckBox;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JSpinner comSpinner;
    private JButton connectButton;
    private JPanel panel1;

    SerialPortClass serial = new SerialPortClass(115200, "COM"+comSpinner.getValue().toString());

    public Gui() {
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Listener{");
                serial = new SerialPortClass(115200,"COM"+comSpinner.getValue().toString());
                serial.setComPort("COM"+comSpinner.getValue().toString());
                System.out.println(serial.getPort().getSystemPortName());
                serial.openPort();

                System.out.println("}");
            }
        });
    }

    public static void main (String[] args){
        try{
            JFrame frame = new JFrame("Gui");
            frame.setContentPane(new Gui().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(300, 300);
            frame.setVisible(true);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
