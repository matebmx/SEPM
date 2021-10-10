package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    // Turning an entity object into a DTO object
    public HorseDto entityToDto(Horse horse) {
        if (horse == null)
            return null;
        return new HorseDto(
                horse.getId(),
                horse.getName(),
                horse.getDescription(),
                horse.getDateBorn(),
                horse.getGender(),
                horse.getFeed(),
                horse.getMother(),
                horse.getFather()
        );
    }

    // Turning a DTO object into an entity object
    public Horse DtoToEntity(HorseDto horseDto) {
        if(horseDto == null)
            return  null;
        return new Horse(
                horseDto.getId(),
                horseDto.getName(),
                horseDto.getDescription(),
                horseDto.getDateBorn(),
                horseDto.getGender(),
                horseDto.getFeed(),
                horseDto.getMother(),
                horseDto.getFather()
        );
    }
}
