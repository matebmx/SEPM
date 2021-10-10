package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface HorseDao {

    /**
     * Get the horse with given ID.
     *
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    Horse getOneById(Long id);

    /**
     * Add new horse to the database.
     *
     * @param horse entity that needs to be added.
     * @return horse entity added to the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    Horse addHorse(Horse horse);

    /**
     * Delete an existing horse from the database.
     *
     * @param id of the horse that needs to be deleted.
     * @return id of the horse that needs to be deleted.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    Long deleteHorse(Long id);

    /**
     * Edit attributes of an existing horse.
     *
     * @param horse entity containing the new values.
     * @return horse entity with the updated attributes.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the horse could not be found in the system.
     * */
    Horse editHorse(Horse horse);

    /**
     * Search for a horse in the database with the given query parameters.
     * An empty search returns the list of all horses
     *
     * @param name to be searched for.
     * @param description to be searched for.
     * @param dateBorn represents the earliest birthdate of horses to be searched for.
     * @param gender to be searched for.
     * @param feed represents the feed of horses to be searched for.
     * @return list of horses with the given criteria
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the horse could not be found in the system.
     */
    List<Horse> searchHorse(String name, String description, String dateBorn, String gender, String feed);
}