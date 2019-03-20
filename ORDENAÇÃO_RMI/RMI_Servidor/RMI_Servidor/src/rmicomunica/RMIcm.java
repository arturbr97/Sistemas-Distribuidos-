package rmicomunica;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIcm extends Remote {

    public String ordenacao(String numeros)throws Exception;

}
