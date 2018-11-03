
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.*;
import java.util.StringTokenizer;
import java.util.logging.*;
import javax.swing.*;


public class Register extends JPanel implements ActionListener{
    JLabel userL = new JLabel("Choose a Username: ");
    JTextField userTF = new JTextField();
    JLabel passL = new JLabel("Password: ");
    JPasswordField passTF = new JPasswordField();
    JLabel passLC = new JLabel("Confirm Password: ");
    JPasswordField passC = new JPasswordField();
    JButton register = new JButton("Register");
    JButton back = new JButton("Back");
    
    public Register(){
        JPanel loginP = new JPanel();
        loginP.setLayout(new GridLayout(4,2));
        loginP.add(userL);
        loginP.add(userTF);
        loginP.add(passL);
        loginP.add(passTF);
        loginP.add(passLC);
        loginP.add(passC);
        loginP.add(register);
        loginP.add(back);
        register.addActionListener(this);
        back.addActionListener(this);
        add(loginP);
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource() == register && passTF.getPassword().length > 0 && userTF.getText().length() > 0){
                String pass = new String(passTF.getPassword());
                String confirm = new String(passC.getPassword());
                if(pass.equals(confirm)){
                    try {
                        BufferedReader input = new BufferedReader(new FileReader("password.txt"));
                        try {
                            String line = input.readLine();
                            while(line != null){
                                StringTokenizer st = new StringTokenizer(line);
                                if(userTF.getText().equals(st.nextToken())){
                                    System.out.println("User already exist");
                                    return;
                                } 
                                line = input.readLine();    
                            }
                            input.close();
                            try {
                                MessageDigest md = MessageDigest.getInstance("SHA-256");
                                md.update(pass.getBytes());
                                byte byteData[] = md.digest();
                                StringBuffer sb = new StringBuffer();
                                for(int i=0; i<byteData.length; i++)
                                    sb.append(Integer.toString((byteData[i] & 0xFF)+0x100,16).substring(1));
                                
                                BufferedWriter output = new BufferedWriter(new FileWriter("password.txt",true));
                                output.write(userTF.getText()+" "+sb.toString()+"\n");
                                output.close();
                                Login login = (Login) getParent();
                                login.c1.show(login,"login");
                                
                            } catch (NoSuchAlgorithmException ex) {
                                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                        } catch (IOException ex) {
                            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                                } catch (FileNotFoundException ex) {
                        Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 
            }
            if(e.getSource()==back){
                Login login = (Login)getParent();
                login.c1.show(login,"login");
                
            }
    }
}
    

