package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TagDAO {
    public ArrayList<String> ottieniTags() throws SQLException;
    public void creaTag(String tag) throws SQLException;
    public void legaRiferimento(String riferimento_id, String tag) throws SQLException;
}
