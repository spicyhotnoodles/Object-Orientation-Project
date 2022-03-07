package DAO;

import java.util.ArrayList;
import DBEntities.Categoria;
import DBEntities.Riferimento;

public interface RiferimentoDAO {
    //ArrayList<Riferimento> ottieniRiferimentoPerParoleChiave(ArrayList<String> tags);
    void inserisciRiferimento(Riferimento riferimento);
    void modificaRiferimento(Riferimento riferimento);
    ArrayList<Riferimento> ottieniRiferimenti();
    //ArrayList<Riferimento> ottieniRiferimentiPerCategoria(Categoria categoria);
}
