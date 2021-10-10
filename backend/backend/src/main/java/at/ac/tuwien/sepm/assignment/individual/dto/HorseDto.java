package at.ac.tuwien.sepm.assignment.individual.dto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse.genderType;
import java.util.Objects;

public class HorseDto {
    private Long id;
    private String name;
    private String description;
    private String dateBorn;
    // Java enum for the gender
    private genderType gender;
    // FeedDto for the feed
    private FeedDto feed;
    // HorseDto for the parents
    private HorseDto mother;
    private HorseDto father;

    public HorseDto() {
    }

    public HorseDto(String name) {
        this.name = name;
    }

    public HorseDto(Long id, String name) {
        this.id = id;
        this.name= name;
    }

    public HorseDto(Long id, String name, String description) {
        this.id = id;
        this.name  = name;
        this.description = description;
    }

    // Constructor with id, name, description and date of birth
    public HorseDto(Long id, String name, String description, String dateBorn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
    }

    // Constructor with id, name, description, date of birth and gender
    public HorseDto(Long id, String name, String description, String dateBorn, genderType gender) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
    }

    // Constructor with id, name, description, date of birth, gender and feed
    public HorseDto(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.feed = feed;
    }

    public HorseDto(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed,
                    HorseDto mother) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.feed = feed;
        this.mother = mother;
    }

    public HorseDto(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed,
                    HorseDto mother, HorseDto father) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.feed = feed;
        this.mother = mother;
        this.father = father;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description;}

    public  void setDescription(String description) { this.description = description;}

    public String getDateBorn() { return dateBorn; }

    public void setDateBorn(String dateBorn) { this.dateBorn = dateBorn; }

    public genderType getGender() { return gender; }

    public void setGender(genderType gender) { this.gender = gender; }

    public FeedDto getFeed() { return feed; }

    public void setFeed(FeedDto feed) { this.feed = feed; }

    public HorseDto getMother() { return mother; }

    public void setMother(HorseDto mother) { this.mother = mother; }

    public HorseDto getFather() { return father; }

    public void setFather(HorseDto father) { this.father = father; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorseDto horseDto = (HorseDto) o;
        return Objects.equals(id, horseDto.id) &&
                Objects.equals(name, horseDto.name) &&
                Objects.equals(description, horseDto.description) &&
                Objects.equals(dateBorn, horseDto.dateBorn) &&
                Objects.equals(gender, horseDto.gender) &&
                Objects.equals(feed, horseDto.feed) &&
                Objects.equals(mother, horseDto.mother) &&
                Objects.equals(father, horseDto.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    private String fieldsString() {
        return "id=" + id
                + ", name='" + name
                + "', description='" + description
                + "', date of birth='" + dateBorn
                + "', gender='" + gender
                + "', feeed='" + feed
                + "', mother='" + mother
                + "', father='" + father
                + '\'';
    }

    @Override
    public String toString() {
        return "HorseDto{ " + fieldsString() + " }";
    }
}
