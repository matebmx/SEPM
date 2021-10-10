package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

public interface FeedService {


    /**
     * Gets the feed with a given ID.
     *
     * @param id of the feed to find.
     * @return the feed with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the Feed could not be found in the system.
     */
    Feed getOneById(Long id);

    /**
     * Get every feed saved in the database.
     *
     * @return every feed saved in the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Feed> getAllFeeds();

    /**
     * Add a new feed to the database.
     *
     * @param Feed entity that needs to be added to the database.
     * @return Feed entity added to the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws ValidationException will be thrown if the given attribute values are incorrect.
     */
    Feed addFeed(Feed Feed);
}
