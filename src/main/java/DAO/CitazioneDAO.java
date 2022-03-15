package DAO;

import java.sql.SQLException;

public interface CitazioneDAO {

    void associaRiferimento(String riferimento_id, String categoria_id) throws SQLException;
    void disassociaRiferimento(String riferimento_id, String categoria_id) throws SQLException;
}
