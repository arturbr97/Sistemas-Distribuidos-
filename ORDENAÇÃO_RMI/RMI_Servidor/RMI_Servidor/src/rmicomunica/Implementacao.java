package rmicomunica;


import java.awt.Color;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rmicomunica.DAO;
import rmicomunica.RMIcm;

public class Implementacao extends UnicastRemoteObject implements RMIcm {
    
    
    DAO c = new DAO();
    
    
    public Implementacao()throws Exception {
    
        super();

    }
    
    
   
    public String ordenacao(String numeros) throws Exception {
     
        String antes = "";
        String depois = "";
                      
        String[] parts = numeros.split(",");
        
        ArrayList ord = new ArrayList();
        
        
        for(int i = 0; i < parts.length; i++){
            
            
            if((i + 1) == parts.length){
                antes = antes + parts[i].trim();
            }else{
                antes = antes + parts[i].trim()+ " -> ";
            }           
            
        }
        
        
        for(int i = 0; i < parts.length; i++){
            
            ord.add(Integer.parseInt(parts[i].trim()));
            
        }
        
        
        Collections.sort(ord);
               
        
        for(int i = 0; i < ord.size(); i++){
            
            if((i + 1) == ord.size()){
                depois = depois + ord.get(i).toString();
            }else{
                depois = depois + ord.get(i).toString()+ " -> ";
            }           
            
        }
        
        System.out.println(depois);
        
      //Registra(antes, depois);
        
        
                  
        return  depois;
    
    }
    
    
    
    public void Registra(String antes, String depois) throws SQLException{
        
        c.conexao();

  
            String sql = "insert into dados (Dados_Desordenados, Dados_Ordenados) values (?, ?);";
                
            PreparedStatement stmt = c.conn.prepareStatement(sql);
                    
            stmt.setString(1, antes);
            stmt.setString(2, depois);            
                    
            stmt.execute();
                
            stmt.close();
               
     }

    
    

    

    

    

    
                
            
          
            
    
    
    
}
