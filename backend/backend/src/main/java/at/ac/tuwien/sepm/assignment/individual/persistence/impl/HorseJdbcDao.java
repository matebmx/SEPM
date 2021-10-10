package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FeedDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.mapper.FeedMapper;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "Horse";
    private static final String FEED_TABLE_NAME = "Feed";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final HorseMapper horseMapper;
    private final FeedMapper feedMapper;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate, FeedMapper feedMapper, HorseMapper horseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.feedMapper = feedMapper;
        this.horseMapper = horseMapper;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);

        if (horses.isEmpty()) throw new NotFoundException("Could not find horse with id " + id);

        return horses.get(0);
    }

    public Feed getOneFeedById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + FEED_TABLE_NAME + " WHERE id=?";
        List<Feed> feeds = jdbcTemplate.query(sql, this::feedMapRow, id);

        if (feeds.isEmpty()) throw new NotFoundException("Could not find feed with id " + id);

        return feeds.get(0);
    }

    @Override
    public Horse addHorse(Horse horse) {
        LOGGER.trace("addHorse({})", horse);
        final String sql = "INSERT INTO "
                + TABLE_NAME
                + "(NAME, DESCRIPTION, DATEBORN, GENDER, FEED, MOTHER, FATHER) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, horse.getName());
            statement.setString(2, horse.getDescription());
            statement.setString(3, horse.getDateBorn());
            statement.setString(4, horse.getGender().toString());
            if (horse.getFeed() != null) {
                statement.setLong(5, horse.getFeed().getId());
            } else {
                statement.setObject(5, null);
            }
            if (horse.getMother() != null) {
                statement.setLong(6, horse.getMother().getId());
            } else {
                statement.setObject(6, null);
            }
            if (horse.getFather() != null) {
                statement.setLong(7, horse.getFather().getId());
            } else {
                statement.setObject(7, null);
            }
            return statement;
        }, keyHolder);

        horse.setId((Long) keyHolder.getKeys().get("id"));

        return horse;
    }

    @Override
    public Long deleteHorse(Long id) {
        LOGGER.trace("deleteHorse({})", id);
        final String sql = "" +
                "UPDATE " + TABLE_NAME +
                " SET FATHER=NULL WHERE FATHER=?; DELETE FROM " + TABLE_NAME +
                " WHERE id=?;";
        jdbcTemplate.update(sql, id, id);

        return id;
    }

    @Override
    public Horse editHorse(Horse horse) {
        LOGGER.trace("editHorse({})", horse);
        final String sql = "UPDATE "
                + TABLE_NAME
                + " SET NAME=?,"
                + " DESCRIPTION=?,"
                + " DATEBORN=?,"
                + " GENDER=?,"
                + " FEED=?,"
                + " MOTHER=?,"
                + " FATHER=?"
                + " WHERE ID=?;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, horse.getName());
            statement.setString(2, horse.getDescription());
            statement.setString(3, horse.getDateBorn());
            statement.setString(4, horse.getGender().toString());
            if (horse.getFeed() == null || horse.getFeed().getId() == 0) {
                statement.setObject(5, null);
            } else {
                statement.setLong(5, horse.getFeed().getId());
            }
            statement.setLong(8, horse.getId());
            if (horse.getMother() == null || horse.getMother().getId() == 0) {
                statement.setObject(6, null);
            } else {
                statement.setLong(6, horse.getMother().getId());
            }
            if (horse.getFather() == null || horse.getFather().getId() == 0) {
                statement.setObject(7, null);
            } else {
                statement.setLong(7, horse.getFather().getId());
            }
            return statement;
        }, keyHolder);

        horse.setId((Long) keyHolder.getKeys().get("id"));

        return horse;
    }

    @Override
    public List<Horse> searchHorse(String name, String description, String dateBorn, String gender, String feed) {
        List<Horse> horses;
        // An empty search returns every horse saved in the database
        if ((name == null || name.equals("null")) &&
                (description == null || description.equals("null")) &&
                (dateBorn == null || dateBorn.equals("null")) &&
                (gender == null || gender.equals("null")) &&
                (feed == null || feed.equals("null"))) {
            final String sql = "SELECT * FROM " + TABLE_NAME + ";";
            horses = jdbcTemplate.query(sql, this::mapRow);
        } else {
            // If not all the parameters are empty, only the empty ones should not be considered
            String checkedName = (name == null ||
                    name.equals("null") ||
                    name.equals("")) ? "%" : "%" + name + "%";
            String checkedDescription = (description == null ||
                    description.equals("null") ||
                    description.equals("")) ? "%" : "%" + description + "%";
            String checkedDateBorn = (dateBorn == null ||
                    dateBorn.equals("null") ||
                    dateBorn.equals("")) ? "9999-12-31" : dateBorn;
            String checkedGender = (gender == null ||
                    gender.equals("null") ||
                    gender.equals("")) ? "%" : "%" + gender + "%";
            String checkedFeed = (feed == null ||
                    feed.equals("null") ||
                    feed.equals("")) ? "%" : "%" + feed + "%";

            // If description is empty also return horses without description
            String emptyDescription = (description == null || description.equals("null")) ? " OR DESCRIPTION IS NULL)" : ")";
            // If the feed is empty also return horses without assigned feeds
            String emptyFeed = (feed == null || feed.equals("null")) ? " OR FEED IS NULL" : "";

            LOGGER.trace("searchHorse({},{},{},{},{})", name, description, dateBorn, gender, feed);

            String sql;

            // If the entered gender is 'male', only the male horses should be returned
            if (gender != null && (gender.equals("male") || gender.equals("Male"))) {
                sql = "SELECT * FROM " + TABLE_NAME +
                        " WHERE LOWER(NAME) LIKE LOWER(?)" +
                        " AND (LOWER(DESCRIPTION) LIKE LOWER(?)" + emptyDescription +
                        " AND DATEBORN < ?" +
                        " AND GENDER='male'" +
                        " AND (FEED IN (SELECT ID FROM FEED s WHERE LOWER(s.NAME) LIKE Lower(?))" + emptyFeed + ")";

                horses = jdbcTemplate.query(sql, this::mapRow,
                        checkedName,
                        checkedDescription,
                        checkedDateBorn,
                        checkedFeed);
            } else {
                sql = "SELECT * FROM " + TABLE_NAME +
                        " WHERE LOWER(NAME) LIKE LOWER(?)" +
                        " AND (LOWER(DESCRIPTION) LIKE LOWER(?)" + emptyDescription +
                        " AND DATEBORN < ?" +
                        " AND LOWER(GENDER) LIKE LOWER(?)" +
                        " AND (FEED IN (SELECT ID FROM FEED s WHERE LOWER(s.NAME) LIKE Lower(?))" + emptyFeed + ")";

                horses = jdbcTemplate.query(sql, this::mapRow,
                        checkedName,
                        checkedDescription,
                        checkedDateBorn,
                        checkedGender,
                        checkedFeed);
            }
            if (horses.isEmpty()) throw new NotFoundException("No horse can be found in the database");
        }
        return horses;
    }

    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        LOGGER.trace("Mapping data in row {} of the ResultSet", i);
        final Horse horse = new Horse();

        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setDescription(resultSet.getString("description"));
        horse.setDateBorn(resultSet.getString("dateBorn"));
        horse.setStringGeender(resultSet.getString("gender"));

        if (resultSet.getLong("feed") == 0) {
            horse.setFeed(null);
        } else {
            final FeedDto feed = feedMapper.entityToDto(this.getOneFeedById(resultSet.getLong("feed")));
            horse.setFeed(feed);

        }

        if (resultSet.getLong("mother") == 0) {
            horse.setMother(null);
        } else {
            final HorseDto mother = horseMapper.entityToDto(this.getOneById(resultSet.getLong("mother")));
            horse.setMother(mother);

        }

        if (resultSet.getLong("father") == 0) {
            horse.setFather(null);
        } else {
            final HorseDto father = horseMapper.entityToDto(this.getOneById(resultSet.getLong("father")));
            horse.setFather(father);

        }

        return horse;
    }

    private Feed feedMapRow(ResultSet resultSet, int i) throws SQLException {
        LOGGER.trace("Mapping data in row {} of the ResultSet", i);
        final Feed feed = new Feed();
        feed.setId(resultSet.getLong("id"));
        feed.setName(resultSet.getString("name"));
        feed.setDescription(resultSet.getString("description"));
        feed.setNutritionalValue(resultSet.getInt("nutritionalValue"));

        return feed;
    }
}