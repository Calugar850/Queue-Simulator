import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private JLabel label1= new JLabel("Introduceți numărul clienților: ");
    private JLabel label2= new JLabel("Introduceți numărul cozilor: ");
    private JLabel label3= new JLabel("Introduceți intervalul de simulare: ");
    private JLabel label4= new JLabel("Introduceți valoarea minimă a timpului de sosire: ");
    private JLabel label5= new JLabel("Introduceți valoarea maximă a timpului de sosire: ");
    private JLabel label6= new JLabel("Introduceți valoarea minimă a timpului de servire: ");
    private JLabel label7= new JLabel("Introduceți valoarea maximă a timpului de servire: ");
    private JTextField textField1= new JTextField();
    private JTextField textField2= new JTextField();
    private JTextField textField3= new JTextField();
    private JTextField textField4= new JTextField();
    private JTextField textField5= new JTextField();
    private JTextField textField6= new JTextField();
    private JTextField textField7= new JTextField();
    private JRadioButton button1= new JRadioButton("START");
    private JLabel emptyLabel1= new JLabel("");
    private JButton submitButton= new JButton("Submit");
    ButtonGroup group= new ButtonGroup();
    public JTextArea text=new JTextArea(100,80);
    public JScrollPane scrollPane=new JScrollPane(text);

    public View(){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(900,625));
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JPanel panel1=new JPanel();
        panel1.setLayout(new GridLayout(0,2));

        panel1.add(label1);
        label1.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField1);

        panel1.add(label2);
        label2.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField2);

        panel1.add(label3);
        label3.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField3);

        panel1.add(label4);
        label4.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField4);

        panel1.add(label5);
        label5.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField5);

        panel1.add(label6);
        label6.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField6);

        panel1.add(label7);
        label7.setFont(new Font("Serif", Font.PLAIN, 18));
        panel1.add(textField7);
        panel1.add(button1);
        button1.setFont(new Font("Serif",Font.PLAIN,18));
        panel1.add(emptyLabel1);
        panel.add(panel1);

        JPanel panel2=new JPanel();
        panel2.add(submitButton);
        submitButton.setFont(new Font("Serif",Font.PLAIN,18));
        panel.add(panel2);

        group.add(button1);

        JPanel panel3=new JPanel();
        panel3.add(scrollPane);
        panel.add(panel3);

        Controller buttonListener=new Controller(this);
        submitButton.addActionListener(buttonListener);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public JTextField getTextField7() {
        return textField7;
    }

    public JRadioButton getButton1() {
        return button1;
    }

    public JTextArea getText() {
        return text;
    }


}
