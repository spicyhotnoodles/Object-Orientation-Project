package DAO;

import DBEntities.Categoria;
import DBEntities.Riferimento;

public interface CatalogoDAO {
    int catalogaRiferimento(Riferimento riferimento, Categoria categoria);
}
