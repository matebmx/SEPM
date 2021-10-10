package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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

        // The name can not be empty
        if (Feed.getName() == null || Feed.getName().isBlank() || Feed.getName().isEmpty()) {
            throw new ValidationException("Please enter a valid name for the Feed!");
            // The description can be empty not consisting of only spaces
        } else if (Feed.getDescription() != null && (Feed.getDescription().isBlank() || Feed.getDescription().isEmpty())) {
            throw new ValidationException("Please enter a valid description for the Feed!");
            // Validating too long names
        } else if (Feed.getName() != null && Feed.getName().length() > 255) {
            throw new ValidationException("The name you have entered is too long!");
            // Validating too long descriptions
        } else if (Feed.getDescription() != null && Feed.getDescription().length() > 255) {
            throw new ValidationException("The description you have entered is too long!");
        }
    }

    /**
     * Validates the horse before adding it to the database.
     *
     * @param horse entity to validate.
     */
    public void validateNewHorse(Horse horse, Horse mother, Horse father) {
        LOGGER.info("Validation of the horse {}", horse);
        // Current date
        LocalDateTime now = LocalDateTime.now();

        // The name can not be empty
        if (horse.getName() == null || horse.getName().isEmpty() || horse.getName().isBlank() || horse.getName().length() <= 0) {
            throw new ValidationException("Please enter a valid name for the horse!");
            // The description can be empty but not consisting of only spaces
        } else if (horse.getDescription() != null && (horse.getDescription().isEmpty() || horse.getDescription().isBlank())) {
            throw new ValidationException("Please enter a valid description for the horse!");
            // Validating too long names
        } else if (horse.getName() != null && horse.getName().length() > 255) {
            throw new ValidationException("The name you have entered is too long!");
            // Validating too long descriptions
        } else if (horse.getDescription() != null && horse.getDescription().length() > 255) {
            throw new ValidationException("The description you have entered is too long!");
            // The birth date can not be empty
        } else if (horse.getDateBorn() == null) {
            throw new ValidationException("Please enter a valid birth date for the horse!");
            // The birth date has to be entered using the given format
        } else if (horse.getDateBorn().charAt(4) != '-' || horse.getDateBorn().charAt(7) != '-') {
            throw new ValidationException("Please enter the birth dates using the format 'YYYY-MM-DD'!");
            // The birth date can not be in the future
        } else if (now.isBefore(LocalDate.parse(horse.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay())) {
            throw new ValidationException("The birth date of the horse can not be in the future!");
            // The gender can not be empty
        } else if (horse.getGender() == null) {
            throw new ValidationException("Please enter a valid gender for the horse!");
            // The gender has to be either male of female
        } else if (!horse.getGender().toString().equals("Male") && !horse.getGender().toString().equals("male")
                && !horse.getGender().toString().equals("Female") && !horse.getGender().toString().equals("female")
                && !horse.getGender().equals(Horse.genderType.male) && !horse.getGender().equals(Horse.genderType.female)) {
            throw new ValidationException("The gender of the horse must be either female or male!");
            // Feed is optional
        } else if (horse.getFeed() != null && horse.getFeed().getId() != null) {
            if (horse.getFeed().equals("") || horse.getFeed().getId() <= 0) {
                throw new ValidationException("Please choose a valid feed!");
            }
            // The mother (if given) should be an existing horse
        } else if (horse.getMother() != null && (horse.getMother().getId() <= 0 || horse.getMother().equals(""))) {
            throw new ValidationException("Please choose a valid mother horse!");
            // The father (if given) should be an existing horse
        } else if (horse.getFather() != null && (horse.getFather().getId() <= 0 || horse.getFather().equals(""))) {
            throw new ValidationException("Please choose a valid father horse!");
        } else {
            LocalDate motherDate;
            LocalDate fatherDate;
            LocalDateTime motherBirthDate;
            LocalDateTime fatherBirthDate;
            LocalDate date = LocalDate.parse(horse.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDateTime birthDate = date.atStartOfDay();

            if (mother != null && 0 < mother.getId()) {
                motherDate = LocalDate.parse(mother.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
                motherBirthDate = motherDate.atStartOfDay();
                // The mother must be female
                if (!mother.getGender().toString().equals("female") && !mother.getGender().toString().equals("Female")
                        && !mother.getGender().equals(Horse.genderType.female)) {
                    throw new ValidationException("The mother horse must have the gender female!");
                }
                // The mother must be born before the child
                if (birthDate.isBefore(motherBirthDate)) {
                    throw new ValidationException("The mother horse can not be younger than her child!");
                }
            }

            if (father != null && 0 < father.getId()) {
                fatherDate = LocalDate.parse(father.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
                fatherBirthDate = fatherDate.atStartOfDay();
                // The father must be male
                if (!father.getGender().toString().equals("male") && !father.getGender().toString().equals("Male")
                        && !horse.getGender().equals(Horse.genderType.male)) {
                    throw new ValidationException("The father horse must have the gender male!");
                }
                // The father must be born before the child
                if (birthDate.isBefore(fatherBirthDate)) {
                    throw new ValidationException("The father horse can not be younger than her child!");
                }
            }
            // The mother and the father must be different horses
            if (mother != null && father != null && 0 < mother.getId() && 0 < father.getId()) {
                if (mother.equals(father)) {
                    throw new ValidationException("The mother and the father horses can not be the same!");
                }
            }
            // The horse can not be its own mother
            if (mother != null && 0 < mother.getId()) {
                if (mother.getId() == horse.getId()) {
                    throw new ValidationException("The horse can not be its own mother!");
                }
            }
            // The horse can not be its own father
            if (father != null && 0 < father.getId()) {
                if (father.getId() == horse.getId()) {
                    throw new ValidationException("The horse can not be its own father!");
                }
            }
        }
    }

    /**
     * Validates the horse before changing its attributes.
     *
     * @param horse entity to validate.
     */
    public void validateChangedHorse(Horse horse, Horse mother, Horse father, List<Horse> horses) {
        LOGGER.info("Validation of the changed horse {}", horse);
        // Check if the horse is parent for another horse
        boolean isMother = false;
        boolean isFather = false;
        for (Horse child : horses) {
            if (child.getMother() != null) {
                if (child.getMother().getId() == horse.getId()) {
                    isMother = true;
                }
            }
            if (child.getFather() != null) {
                if (child.getFather().getId() == horse.getId()) {
                    isFather = true;
                }

            }
        }
        // If the horse is already father, the gender must stay male
        if (isFather) {
            if (!horse.getGender().equals("male") && !horse.getGender().equals("Male")
                    && !horse.getGender().toString().equals("male") && !horse.getGender().toString().equals("Male")
                    && !horse.getGender().equals(Horse.genderType.male)) {
                throw new ValidationException("This horse is already father for another one. The gender must be male!");
            }
        }
        // If the horse is already mother, the gender must stay female
        if (isMother) {
            if (!horse.getGender().equals("female") && !horse.getGender().equals("Female")
                    && !horse.getGender().toString().equals("female") && !horse.getGender().toString().equals("Female")
                    && !horse.getGender().equals(Horse.genderType.female)) {
                throw new ValidationException("This horse is already mother for another one. The gender must be female!");
            }
        }
        // Validating the mandatory fields
        LocalDateTime now = LocalDateTime.now();
        if (horse.getName() == null) {
            throw new ValidationException("Please enter a valid name for the horse");
        } else if (horse.getDateBorn() == null) {
            throw new ValidationException("Please enter a valid birth date for the horse");
        } else if (now.isBefore(LocalDate.parse(horse.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay())) {
            throw new ValidationException("The birth date of the horse can not be in the future!");
        } else if (horse.getGender() == null) {
            throw new ValidationException("Please enter a valid gender for the horse");
        } else if (!horse.getGender().toString().equals("Male") && !horse.getGender().toString().equals("male")
                && !horse.getGender().toString().equals("Female") && !horse.getGender().toString().equals("female")
                && !horse.getGender().equals(Horse.genderType.male) && !horse.getGender().equals(Horse.genderType.female)) {
            throw new ValidationException("The gender of the horse must be either female or male!" + "--" + horse.getGender());
            // Feed is optional
        } else if (horse.getFeed() != null) {
            if (horse.getFeed().equals("") || horse.getFeed().getId() <= 0) {
                throw new ValidationException("Please choose a valid feed!");
            }
            // Validation of the parents
        } else if (horse.getMother() == null || horse.getMother().getId() <= 0 || horse.getMother().equals("")
                || horse.getMother().getId() == horse.getId()) {
            throw new ValidationException("Please enter a valid mother horse!");
        } else if (horse.getFather() == null || horse.getFather().getId() <= 0 || horse.getFather().equals("")
                || horse.getFather().getId() == horse.getId()) {
            throw new ValidationException("Please enter a valid father horse!");
        } else {

            LocalDate motherDate;
            LocalDate fatherDate;
            LocalDateTime motherBirthDate;
            LocalDateTime fatherBirthDate;
            LocalDate date = LocalDate.parse(horse.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDateTime birthDate = date.atStartOfDay();

            if (mother != null && 0 < mother.getId()) {
                motherDate = LocalDate.parse(mother.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
                motherBirthDate = motherDate.atStartOfDay();

                if (!mother.getGender().toString().equals("female") && !mother.getGender().toString().equals("Female")) {
                    throw new ValidationException("The mother horse must have the gender female!");
                }

                if (birthDate.isBefore(motherBirthDate)) {
                    throw new ValidationException("The mother horse can not be younger than her child!");
                }
            }

            if (father != null && 0 < father.getId()) {
                fatherDate = LocalDate.parse(father.getDateBorn(), DateTimeFormatter.ISO_LOCAL_DATE);
                fatherBirthDate = fatherDate.atStartOfDay();

                if (!father.getGender().toString().equals("male") && !father.getGender().toString().equals("Male")) {
                    throw new ValidationException("The father horse must have the gender male!");
                }

                if (birthDate.isBefore(fatherBirthDate)) {
                    throw new ValidationException("The father horse can not be younger than her child!");
                }
            }

            if (mother != null && father != null && 0 < mother.getId() && 0 < father.getId()) {
                if (mother.equals(father)) {
                    throw new ValidationException("The mother and the father horses can not be the same!");
                }
            }

            if (mother != null && 0 < mother.getId()) {
                if (mother.getId() == horse.getId()) {
                    throw new ValidationException("The horse can not be its own mother!");
                }
            }

            if (father != null && 0 < father.getId()) {
                if (father.getId() == horse.getId()) {
                    throw new ValidationException("The horse can not be its own father!");
                }
            }
        }
    }

    /**
     * Validates the horse before deleting it from the database.
     *
     * @param id        of the horse entity to validate.
     * @param horseList containing all the existing horses in the database.
     */
    public void validateHorseOnDelete(Long id, List<Horse> horseList) {
        LOGGER.info("Validating the horse with the id {} before deleting", id);
        // Check if horse exists at all
        boolean found = false;
        for (Horse horse : horseList) {
            if (horse.getId() == id) {
                found = true;
            }
        }
        if (!found) {
            throw new ValidationException("The horse with the given ID can not be found in the database");
        } else {
            for (Horse horse : horseList) {
                if (horse.getMother() != null && horse.getMother().getId() > 0) {
                    if (horse.getMother().equals(id) || horse.getMother().getId() == id) {
                        throw new ValidationException(
                                "The horse can not be deleted because it is the mother of one or more horses!");
                    }
                }

                if (horse.getFather() != null && horse.getFather().getId() > 0) {
                    if (horse.getFather().equals(id) || horse.getFather().getId() == id) {
                        throw new ValidationException(
                                "The horse can not be deleted because it is the father of one or more horses!");
                    }
                }
            }
        }
    }

    /*
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
     */
}