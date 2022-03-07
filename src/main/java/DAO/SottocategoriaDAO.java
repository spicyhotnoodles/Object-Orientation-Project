package DAO;

import DBEntities.Categoria;

public interface SottocategoriaDAO {

    String creaSottocategoria(Categoria sottocategoria, Categoria supercategoria);
    //int definisciSupercategoria(Categoria categoria);
}
