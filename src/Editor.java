
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;



public class Editor extends JPanel implements ActionListener {

    File file;
    JButton save = new JButton("Save");
    JButton savec = new JButton("Save and Close");
    JTextArea text = new JTextArea(20,40);
    public Editor(String s) throws IOException{
        file = new File(s);
        save.addActionListener(this);
        savec.addActionListener(this);
        if(file.exists()){
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line = input.readLine();
                while(line!=null){
                    text.append(line+"\n");
                    line=input.readLine();
                    
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        add(save);
        add(savec);
        add(text);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //FileWriter out;
        try {
          FileWriter out = new FileWriter(file);
          out.write(text.getText());
          out.close();
          if(e.getSource() == savec){
              Login login = (Login) getParent();
              login.c1.show(login,"fb");
          }
        } catch (IOException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         
    }
    
}
