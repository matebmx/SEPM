package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {

    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    // Method returning the horse with the given id
    @GetMapping(value = "/{id}")
    public HorseDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return horseMapper.entityToDto(horseService.getOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    // Method searches for horses with the given query parameters transferred as request parameters
    @GetMapping(value = "")
    public List<HorseDto> searchHorse(@RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "description", required = false) String description,
                                      @RequestParam(name = "dateBorn", required = false) String dateBorn,
                                      @RequestParam(name = "gender", required = false) String gender,
                                      @RequestParam(name = "feed", required = false) String feed) {
        LOGGER.info("SEARCH " + BASE_URL +
                        "/?name={}?description={}?dateBorn={}?gender={}?feed={}",
                name, description, dateBorn, gender, feed);
        try {
            List<Horse> original = horseService.searchHorse(name, description, dateBorn, gender, feed);
            List<HorseDto> toBeReturned = new ArrayList<>();
            for (int i = 0; i < original.size(); i++) {
                if (original.get(i) != null) {
                    toBeReturned.add(horseMapper.entityToDto(original.get(i)));
                }
            }
            return toBeReturned;
        } catch (NotFoundException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No horse found with the given parameters", e);
        }
    }

    // Method adds a new horse to the database using the information in the body of the http request
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto addHorse(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST " + BASE_URL);
        try {
            Horse horse = horseMapper.DtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.addHorse(horse));
        } catch (ValidationException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);

        }
    }

    // Method deletes a horse from the database with the given id
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Long deleteHorse(@PathVariable("id") Long id) {
        LOGGER.info("DELETE " + BASE_URL + "/" + id);
        try {
            return horseService.deleteHorse(id);
        } catch (ValidationException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    // Method changes attributes of an existing horse using the input values
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HorseDto editHorse(@RequestBody HorseDto horseDto,
                              @PathVariable Long id) {
        LOGGER.info("EDIT " + BASE_URL + "/" + id);
        try {
            Horse horse = horseMapper.DtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.editHorse(horse));
        } catch (ValidationException e) {
            LOGGER.error("ERROR " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }
}