package DAO;

import DBEntities.Riferimento;

import java.util.ArrayList;

public interface CitazioneDAO {

    int associaRiferimenti(Riferimento riferimento, ArrayList<Riferimento> menzioni);
}
