package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = HorseEndpoint.BASE_URL)
public class HorseEndpoint {
    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService Horseservice;
    private final HorseMapper Horsemapper;

    public HorseEndpoint(HorseService Horseservice, HorseMapper Horssemapper) {
        this.Horseservice = Horseservice;
        this.Horsemapper = Horssemapper;
    }

    @GetMapping
    public Stream<HorseDto> allHorses() {
        LOGGER.info("GET " + BASE_URL);
        return Horseservice.allHorses().stream()
                .map(Horsemapper::entityToDto);
    }
}
