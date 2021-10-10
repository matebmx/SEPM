package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.FeedDto;
import at.ac.tuwien.sepm.assignment.individual.mapper.FeedMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.FeedService;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(FeedEndpoint.BASE_URL)
public class FeedEndpoint {

    static final String BASE_URL = "/feeds";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final FeedService FeedService;
    private final FeedMapper FeedMapper;

    @Autowired
    public FeedEndpoint(FeedService FeedService, FeedMapper FeedMapper) {
        this.FeedService = FeedService;
        this.FeedMapper = FeedMapper;
    }

    // Method returning the Feed with the given id
    @GetMapping(value = "/{id}")
    public FeedDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return FeedMapper.entityToDto(FeedService.getOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading Feed", e);
        }
    }

    // Method returning every Feed in the database
    @GetMapping(value = "")
    public List<FeedDto> getAllFeeds() {
        LOGGER.info("GET " + BASE_URL);
        List<Feed> original = FeedService.getAllFeeds();
        List<FeedDto> toBeReturned = new ArrayList<>();
        List<Long> FeedIDs = new ArrayList<>();
        try {
            for (int i = 0; i < original.size(); i++) {
                if (original.get(i) != null) {
                    toBeReturned.add(FeedMapper.entityToDto(original.get(i)));
                    FeedIDs.add(FeedMapper.entityToDto(original.get(i)).getId());
                }
            }
            LOGGER.info("Feeds with the IDs " + FeedIDs + " returned");
            return toBeReturned;
        } catch (NotFoundException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading Feed", e);
        }
    }

    // Method adding a new Feed to the database using the data in the body of the http request
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedDto addFeed(@RequestBody FeedDto FeedDto) {
        LOGGER.info("POST " + BASE_URL);
        try {
            Feed Feed = FeedMapper.DtoToEntity(FeedDto);
            return FeedMapper.entityToDto(FeedService.addFeed(Feed));
        } catch (ValidationException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }
}
