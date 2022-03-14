package DAOImplementations;

import DAO.TagDAO;

import java.sql.*;
import java.util.ArrayList;

public class TagDAOPostgre implements TagDAO {
    private Connection connection;
    Statement ottieniTags;
    PreparedStatement eliminaTag;
    PreparedStatement creaTag;


    public TagDAOPostgre(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<String> ottieniTags() throws SQLException {
        ArrayList<String> tags = new ArrayList<>();
        ottieniTags = connection.createStatement();
        ResultSet rs = ottieniTags.executeQuery("select parola from tag");
        while (rs.next()) {
            tags.add(rs.getString("parola"));
        }
        return tags;
    }

    @Override
    public void creaTag(String tag) throws SQLException {
        creaTag = connection.prepareStatement("insert into tag values (default, ?, null)");
        creaTag.setString(1, tag);
        creaTag.executeUpdate();
    }

    @Override
    public void legaRiferimento(String riferimento_id, String tag) throws SQLException {

    }

    public void eliminaTag(String tag) throws SQLException {
        eliminaTag = connection.prepareStatement("delete from tag where parola = ?");
        eliminaTag.setString(1, tag);
        eliminaTag.executeUpdate();
    }
}
