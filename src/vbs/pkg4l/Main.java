package src.vbs.pkg4l;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.util.List;

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
        exit.setToolTipText("Leave VBS4L");
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
        
        cmdOutput cOut = new cmdOutput();
        cOut.setVisible(true);
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("vbs4l- saving...");
                try {
                    File file = new File("program.vbs");
                    file.createNewFile();
                    
                    BufferedWriter fileOut = new BufferedWriter(new FileWriter("program.vbs"));
                    textArea.write(fileOut);
                    System.out.println("vbs4l- saved program.vbs");
                } catch (IOException ex) {
                    System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        });
        run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                SwingWorker<Void, String> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        System.out.println("vbs4l- starting...");
                        String cmd = "wine "+wsh.getText()+" program.vbs";
                        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", cmd);
                        processBuilder.redirectErrorStream(true);
                        Process process = processBuilder.start();
                        
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                publish(line);  // This triggers process() on the EDT
                            }
                            process.waitFor();
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<String> chunks) {
                        for (String line : chunks) {
                            System.out.println(line);
                        }
                    }
                };

                worker.execute();

            }
        });
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });   
    }
}
