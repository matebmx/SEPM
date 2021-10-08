package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.FeedDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Feed;
import org.springframework.stereotype.Component;

@Component
public class FeedMapper {

    // Turning an entity object into a DTO object
    public FeedDto entityToDto(Feed Feed) {
        if (Feed == null)
            return null;
        return new FeedDto(Feed.getId(), Feed.getName(), Feed.getDescription(), Feed.getNutritionalValue());
    }

    // Turning a DTO object into an entity object
    public Feed DtoToEntity(FeedDto FeedDto) {
        if(FeedDto == null)
            return null;
        return new Feed(FeedDto.getId(), FeedDto.getName(), FeedDto.getDescription(), FeedDto.getNutritionalValue());
    }
}