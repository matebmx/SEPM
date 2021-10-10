package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface FeedDao {

    /**
     * Get the Feed with given ID.
     *
     * @param id of the feed to find.
     * @return the feed entity with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the Feed could not be found in the database.
     */
    Feed getOneById(Long id);

    /**
     * Get every feed saved in the database.
     *
     * @return every feed saved in the database will be returned.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Feed> getAllFeeds();

    /**
     * Add a new feed to the database.
     *
     * @param Feed entity that needs to be added.
     * @return Feed entity added to the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    Feed addFeed(Feed Feed);
}
