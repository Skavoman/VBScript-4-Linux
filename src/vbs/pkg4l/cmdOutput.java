package src.vbs.pkg4l;
import java.awt.Image;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.PrintStream;

public class cmdOutput extends JFrame
{
    cmdOutput() throws java.io.IOException {
        setTitle("VBS4L Cmd Output");
        setSize(450, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Image icon = ImageIO.read(cmdOutput.class.getResource("/duriancmd.png"));
        setIconImage(icon);
                    
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JTextArea commandprompt = new JTextArea(21, 60);
                    
        commandprompt.setBackground(Color.BLACK);
        commandprompt.setForeground(Color.LIGHT_GRAY);
        commandprompt.setFont(new java.awt.Font("Noto Mono", 0, 12));
        PrintStream printStream = new PrintStream(new CustomOutputStream(commandprompt));
        System.setOut(printStream);
        System.setErr(printStream);
        JScrollPane sp = new JScrollPane(commandprompt);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                
        panel.add(sp);
        add(panel);
    }
}
