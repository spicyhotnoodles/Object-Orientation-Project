package DAOImplementations;

import DAO.RiferimentoDAO;
import DBEntities.Categoria;
import DBEntities.Riferimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class RiferimentoDAOPostgre implements RiferimentoDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire

    public RiferimentoDAOPostgre(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public void inserisciRiferimento(Riferimento riferimento) {
    }

    @Override
    public void modificaRiferimento(Riferimento riferimento) {
    }

    @Override
    public ArrayList<Riferimento> ottieniRiferimenti() {
        ArrayList<Riferimento> riferimenti = new ArrayList<Riferimento>();

        return riferimenti;
    }

    public ArrayList<String> estraiAutori(String str) {
        Stack<Integer> dels = new Stack<Integer>();
        ArrayList<String> autori = new ArrayList<String>();
        for(int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '[')
                dels.add(i);
            else if (str.charAt(i) == ']' &&
                    !dels.isEmpty())
            {
                int pos = dels.peek();
                dels.pop();
                int len = i - 1 - pos;
                String ans = str.substring(
                        pos + 1, pos + 1 + len);
                autori.add(ans);
            }
        }
        return autori;
    }

}
