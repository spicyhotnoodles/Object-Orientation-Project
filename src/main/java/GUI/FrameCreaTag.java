package GUI;

import javax.swing.*;

public class FrameCreaTag extends JFrame {
    private Controller c;
    private JPanel mainPanel;
    private JTextField textField1;

    public FrameCreaTag(String titolo, Controller c) {
        super(titolo);
        this.c = c;
        this.setContentPane(mainPanel);
        this.pack();
        this.setBounds(800, 400, 400, 200);
    }


}
