
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;



public class FileBrowser extends JPanel implements ActionListener{
    
    JLabel label = new JLabel("File list:");
    JButton newFile = new JButton("New file");
    JButton open = new JButton("Open");
    JTextField newFileTF = new JTextField(10);
    
    ButtonGroup bg;
    File directory;
   
    
    public FileBrowser(String dir){
        directory = new File(dir);
        directory.mkdir();
        JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3,1));
        fileList.add(label);
        bg = new ButtonGroup();
        for(File file: directory.listFiles()){
             JRadioButton radio = new JRadioButton(file.getName());
            radio.setActionCommand(file.getName());
            bg.add(radio);
            fileList.add(radio);
            
        }
        JPanel newP = new JPanel();
        newP.add(newFileTF);
        newP.add(newFile);
        newFile.addActionListener(this);
        open.addActionListener(this);
        fileList.add(open);
        fileList.add(newP);
        add(fileList);
        
    }


    
    @Override
    public void actionPerformed(ActionEvent e) {
       Login login = (Login) getParent();
        if(e.getSource()==open){
           try {
               login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()),"editor");
           } catch (IOException ex) {
               Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
           }
            login.c1.show(login, "editor");
           
        }
        if(e.getSource() == newFile){
            String file = directory.getName()+"\\"+newFileTF.getText()+".txt";
            if(newFileTF.getText().length() >0 && !(new File(file).exists())){
                try {
                    login.add(new Editor(file), "editor");
                } catch (IOException ex) {
                    Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
                }
                login.c1.show(login, "editor");
                
            }
        }
    }
    
}
