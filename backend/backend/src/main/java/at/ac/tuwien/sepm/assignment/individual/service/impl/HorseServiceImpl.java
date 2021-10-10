package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao dao;
    private final Validator validator;

    @Autowired
    public HorseServiceImpl(HorseDao horseDao, Validator validator) {
        this.dao = horseDao;
        this.validator = validator;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        return dao.getOneById(id);
    }

    @Override
    public Horse addHorse(Horse horse) {
        LOGGER.trace("addHorse({})", horse);
        // Avoiding null pointers by checking if the given parents are null
        Horse mother;
        Horse father;
        if (horse.getMother() == null || horse.getMother().getId() == null) {
            mother = null;
        } else {
            mother = this.getOneById(horse.getMother().getId());
        }
        if (horse.getFather() == null || horse.getFather().getId() == null) {
            father = null;
        } else {
            father = this.getOneById(horse.getFather().getId());
        }
        this.validator.validateNewHorse(horse, mother, father);
        return dao.addHorse(horse);
    }

    @Override
    public Long deleteHorse(Long id) {
        LOGGER.trace("deleteHorse({})", id);
        this.validator.validateHorseOnDelete(id, this.searchHorse(null, null, null, null, null));
        return dao.deleteHorse(id);
    }

    @Override
    public Horse editHorse(Horse horse) {
        LOGGER.trace("editHorse({})", horse);
        // Avoiding null pointers by checking if the given parents are null
        Horse mother;
        Horse father;
        List<Horse> horses = searchHorse(null, null, null, null, null);
        if (horse.getMother() == null || horse.getMother().getId() <= 0 || horse.getMother().toString().isBlank()) {
            mother = null;
        } else {
            mother = this.getOneById(horse.getMother().getId());
        }
        if (horse.getFather() == null || horse.getFather().getId() <= 0 || horse.getFather().toString().isBlank()) {
            father = null;
        } else {
            father = this.getOneById(horse.getFather().getId());
        }
        this.validator.validateChangedHorse(horse, mother, father, horses);
        return dao.editHorse(horse);
    }

    @Override
    public List<Horse> searchHorse(String name, String description, String dateBorn, String gender, String feed) {
        LOGGER.trace("searchHorse({}, {}, {}, {}, {})", name, description, dateBorn, gender, feed);
        return dao.searchHorse(name, description, dateBorn, gender, feed);
    }
}