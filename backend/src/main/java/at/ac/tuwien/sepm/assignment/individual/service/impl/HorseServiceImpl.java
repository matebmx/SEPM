package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao dao;

    public HorseServiceImpl(HorseDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Horse> allHorses() {
        LOGGER.trace("getAllFeeds()");
        return dao.getAll();
    }
}
