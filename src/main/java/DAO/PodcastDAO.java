package DAO;

import DBEntities.Podcast;

import java.sql.SQLException;

public interface PodcastDAO {

    public String inserisciPodcast(Podcast p) throws SQLException;
    public void modificaPodcast(Podcast p) throws SQLException;

}
