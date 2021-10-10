package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FeedDao;

import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FeedJdbcDao implements FeedDao {

    private static final String TABLE_NAME = "Feed";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FeedJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Feed getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Feed> Feeds = jdbcTemplate.query(sql, this::mapRow, id);

        if (Feeds.isEmpty()) throw new NotFoundException("Could not find Feed with id " + id);

        return Feeds.get(0);
    }

    @Override
    public List<Feed> getAllFeeds() {
        LOGGER.trace("getAllFeeds()");
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Feed> Feeds = jdbcTemplate.query(sql, this::mapRow);

        if (Feeds.isEmpty()) throw new NotFoundException("No Feeds can be found in the database");

        return Feeds;
    }

    @Override
    public Feed addFeed(Feed Feed) {
        LOGGER.trace("addFeed({})", Feed);
        final String sql = "INSERT INTO "
                + TABLE_NAME
                + "(NAME, DESCRIPTION, NUTRITIONALVALUE) "
                + "VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, Feed.getName());
            statement.setString(2, Feed.getDescription());
            statement.setInt(3, Feed.getNutritionalValue());
            return statement;
        }, keyHolder);

        Feed.setId((Long) keyHolder.getKeys().get("id"));

        return Feed;
    }

    private Feed mapRow(ResultSet resultSet, int rownum) throws SQLException {
        LOGGER.trace("Mapping data in row {} of the ResultSet", rownum);
        final Feed Feed = new Feed();
        Feed.setId(resultSet.getLong("id"));
        Feed.setName(resultSet.getString("name"));
        Feed.setDescription(resultSet.getString("description"));
        Feed.setNutritionalValue(resultSet.getInt("nutritionalvalue"));

        return Feed;
    }

}
