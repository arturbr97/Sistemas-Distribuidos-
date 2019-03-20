package rmicomunica;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class Cliente {

    public static void main(String[] args)throws RemoteException, Exception{

        try {
            
            
            Registry x = LocateRegistry.getRegistry("192.168.43.10", 5000);

            RMIcm z = (RMIcm) x.lookup("Calcular Ordenacao");
            
            String numeros = JOptionPane.showInputDialog("insira os numeros separados por virgula: ");
            
            JOptionPane.showMessageDialog(null, "Antes = "+numeros+" / Depois = "+ z.ordenacao(numeros));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
