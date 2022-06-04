package DAOImplementations;

import DAO.CitazioneDAO;
import DBEntities.Riferimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CitazioneDAOPostgre implements CitazioneDAO {

    private Connection connection;
    //Qui vanno definiti gli Statement per le istruzioni SQL che i vari metodi devono eseguire
    PreparedStatement associaRiferimento;
    PreparedStatement disassociaRiferimento;

    public CitazioneDAOPostgre(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void associaRiferimento(String riferimento_id, String menzionato_id) throws SQLException {
        associaRiferimento = connection.prepareStatement("insert into citazione values (default, cast(? as int), cast(? as int))");
        associaRiferimento.setString(1, menzionato_id);
        associaRiferimento.setString(2, riferimento_id);
        associaRiferimento.executeUpdate();
    }

    @Override
    public void disassociaRiferimento(String riferimento_id, String menzionato_id) throws SQLException {
        disassociaRiferimento = connection.prepareStatement("delete from citazione where riferimento_id = cast(? as int) and menzionato_id = cast(? as int)");
        disassociaRiferimento.setString(1, riferimento_id);
        disassociaRiferimento.setString(2, menzionato_id);
        disassociaRiferimento.executeUpdate();
    }
}
