package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Validating the Feed before adding it to the database.
     *
     * @param Feed entity to validate.
     */
    public void validateNewFeed(Feed Feed) {
        LOGGER.info("Validation of the Feed {}", Feed);

        // Validating the name and the description of a new Feed
        if (Feed.getName() == null || Feed.getName().isBlank() || Feed.getName().isEmpty()) {
            throw new ValidationException("Please enter a valid name for the Feed!");
        } else if (Feed.getDescription() == null || Feed.getDescription().isBlank() || Feed.getDescription().isEmpty()) {
            throw new ValidationException("Please enter a valid description for the Feed!");
        } else if (Feed.getName().length() > 255) {
            throw new ValidationException("The name you have entered is too long!");
        } else if (Feed.getDescription().length() > 255) {
            throw new ValidationException("The description you have entered is too long!");
        }
    }
}