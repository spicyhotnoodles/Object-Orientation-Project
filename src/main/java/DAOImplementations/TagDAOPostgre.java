package DAOImplementations;

import DAO.TagDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TagDAOPostgre implements TagDAO {
    private Connection connection;
    Statement ottieniTags;


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
}
