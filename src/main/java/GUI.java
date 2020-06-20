import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI implements ActionListener {
    private JLabel label;
    private JFrame frame;
    private JPanel panel;
    public GUI() {
        frame = new JFrame();
        JButton button = new JButton("Click me!");
        button.addActionListener(this);
        label = new JLabel("Total Number of Analyzed Problems: 0" );
        panel = new JPanel();
        panel.add(button);
        panel.add(label);
        panel.setBorder(BorderFactory.createEmptyBorder(300,300,400,300));
        panel.setLayout(new GridLayout(0,1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("Total Number of Analyzed Problems: ");
        try {
            Main.initialBoot();
            Main.runEngine();
            Main.loopResultAnalyzer();
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
