package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TagDAO {
    public ArrayList<String> ottieniTags() throws SQLException;
}
