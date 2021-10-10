package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.dto.FeedDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;

import java.util.Objects;

public class Horse {

    // Each horse has an id, name and description
    private Long id;
    private String name;
    private String description;
    private String dateBorn;
    // Java enum for the gender
    public enum genderType {male, female};
    private genderType gender;
    // FeedDto for the feed
    private FeedDto feed;
    // HorseDto for the parents
    private HorseDto mother;
    private HorseDto father;

    // Empty constructor with no parameters
    public Horse() {
    }

    // Constructor with only the name of the horse
    public Horse(String name) { this.name = name;}

    // Constructor with only the id and the name of the horse
    public Horse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor with id, name and description
    public Horse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor with id, name, description and date of birth
    public Horse(Long id, String name, String description, String dateBorn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
    }

    // Constructor with id, name, description, date of birth and gender
    public Horse(Long id, String name, String description, String dateBorn, genderType gender) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
    }

    // Constructor with id, name, description, date of birth, gender and feed
   public Horse(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.feed = feed;
    }

    // Constructor with id, name, description, date of birth, gender and feed
    public Horse(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed,
                 HorseDto mother) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBorn = dateBorn;
        this.gender = gender;
        this.feed = feed;
        this.mother = mother;
    }

    // Constructor with id, name, description, date of birth, gender and feed
    public Horse(Long id, String name, String description, String dateBorn, genderType gender, FeedDto feed,
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

    // Getter and setter methods for every attribute of the horse table in the database
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDateBorn() { return dateBorn; }

    public void setDateBorn(String dateBorn) { this.dateBorn = dateBorn; }

    public genderType getGender() { return gender; }

    public void setGender(genderType gender) { this.gender = gender; }

    public void setStringGeender(String gender) {
        if (gender.equals("male")) {
            this.gender = genderType.male;
        } else if (gender.equals("female")) {
            this.gender = genderType.female;
        } else {
            this.gender = null;
        }
    }

    public FeedDto getFeed() { return feed; }

    public void setFeed(FeedDto feed) { this.feed = feed; }

    public HorseDto getMother() { return mother; }

    public void setMother(HorseDto mother) { this.mother = mother; }

    public HorseDto getFather() { return father; }

    public void setFather(HorseDto father) { this.father = father; }

    // Override of the equals method each attribute of the horse table in the database
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Objects.equals(id, horse.id) &&
                Objects.equals(name, horse.name) &&
                Objects.equals(description, horse.description) &&
                Objects.equals(dateBorn, horse.dateBorn) &&
                Objects.equals(gender, horse.gender) &&
                Objects.equals(feed, horse.feed) &&
                Objects.equals(mother, horse.mother) &&
                Objects.equals(father, horse.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    protected String fieldsString() {
        return "id=" + id
                + ", name='" + name
                + "', description='" + description
                + "', date of birth='" + dateBorn
                + "', gender='" + gender
                + "', feed='" + feed
                + "', mother='" + mother
                + "', father='" + father
                + '\'';
    }

    @Override
    public String toString() {
        return "Horse{ " + fieldsString() +" }";
    }
}