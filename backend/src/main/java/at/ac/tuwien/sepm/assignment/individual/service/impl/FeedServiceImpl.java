package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.persistence.FeedDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.FeedService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final FeedDao dao;
    private final HorseService horseService;
    private final Validator validator;

    @Autowired
    public FeedServiceImpl(FeedDao FeedDao, Validator validator, HorseService horseService) {
        this.dao = FeedDao;
        this.validator = validator;
        this.horseService = horseService;
    }

    @Override
    public Feed getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        return dao.getOneById(id);
    }

    @Override
    public List<Feed> getAllFeeds() {
        LOGGER.trace("getAllFeeds()");
        return dao.getAllFeeds();
    }

    @Override
    public Feed addFeed(Feed Feed) {
        LOGGER.trace("addFeed({})", Feed);
        validator.validateNewFeed(Feed);
        return dao.addFeed(Feed);
    }
}