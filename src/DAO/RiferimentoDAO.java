package DAO;

import java.util.ArrayList;
import DBEntities.Categoria;
import DBEntities.Riferimento;

public interface RiferimentoDAO {
    ArrayList<Riferimento> ottieniRiferimentoPerParoleChiave(ArrayList<String> tags);
    int inserisciNuovoRiferimento(Riferimento riferimento);
    int modificaRiferimento(Riferimento riferimento);
    ArrayList<Riferimento> ottieniRiferimentiPerCategoria(Categoria categoria);
}
