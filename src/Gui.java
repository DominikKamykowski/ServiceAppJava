import javax.swing.*;

public class Gui {
    private JCheckBox stmDiodeCheckBox;
    private JCheckBox redDiodeCheckBox;
    private JCheckBox greenDiodeCheckBox;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JSpinner comSpinner;
    private JButton connectButton;
    private JPanel panel1;

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
