package src.vbs.pkg4l;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;

/**
 *
 * @author moocher
 */
public class Main {
    static JTextArea textArea = new JTextArea(15, 31);
    static JTextField wsh = new JTextField(10);
    static JTextField fileName = new JTextField(15);
    public static void main(String[] args) throws IOException {
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InstantiationException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalAccessException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        JFrame frame = new JFrame("VBScript For Linux");
        frame.setSize(350, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Image icon = ImageIO.read(Main.class.getResource("/durian.png"));
        
        frame.setIconImage(icon);
        
        JPanel panel = new JPanel();
        JButton save = new JButton("Save");
        JButton run = new JButton("Run");
        JButton exit = new JButton("Exit");
        wsh.setFont(new java.awt.Font("Noto Mono", 0, 12));
        wsh.setText("wscript.exe");
        
        save.setToolTipText("<html>Save file:<br>program.vbs</html>");
        run.setToolTipText("Execute file");
        exit.setToolTipText("Leave VBS-4L");
        wsh.setToolTipText("<html>Enviornment for executing VBScript files<br> <br>wscript.exe for GUI based scripts<br>cscript.exe for command-line based scripts<br>Or use a custom enviornment</html>");
        
        JScrollPane sp = new JScrollPane(textArea);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new java.awt.Font("Noto Mono", 0, 12));
        textArea.setText("'made in VBScript 4 Linux \n"+"'@author "+System.getProperty("user.name")+"\n"+"\n"+"X=Msgbox(\"Hello, World!\", 0+0, \"Linux\")");
    
        
        panel.add(save);
        panel.add(run);
        panel.add(exit);
        panel.add(wsh);
        panel.add(sp);
        frame.add(panel);
        frame.setVisible(true);
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    File file = new File("program.vbs");
                    file.createNewFile();
                    
                    BufferedWriter fileOut = new BufferedWriter(new FileWriter("program.vbs"));
                    textArea.write(fileOut);
                } catch (IOException ex) {
                    System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        });
        run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("wine", wsh.getText(), "program.vbs");
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while((line = reader.readLine()) != null){
                        
                    }
                    process.waitFor();
                } catch (IOException ex) {
                    System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (InterruptedException ex) {
                    System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                
            }
        });
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });   
    }
}
