package at.ac.tuwien.sepm.assignment.individual.entity;

import java.util.Objects;

public class Feed {

    // Each Feed has a unique ID, a name and a description
    private Long id;
    private String name;
    private String description;
    private int nutritionalValue;

    // Empty constructor with no parameters
    public Feed() {
    }

    // Constructor with only the name of the Feed
    public Feed(String name) {
        this.name = name;
    }

    // Constructor with only the id and the name of the Feed
    public Feed(Long id, String name) {
        this(name);
        this.id = id;
    }

    // Constructor with only id, name and description
    public Feed(Long id, String name, String description){
        this.name = name;
        this.id = id;
        this.description = description;
    }

    // Constructor with id, name, description and nutritionalValue
    public Feed(Long id, String name, String description, int nutritionalValue){
        this.name = name;
        this.id = id;
        this.description = description;
        this.nutritionalValue = nutritionalValue;
    }

    // Getter and setter methods for for the id, name and description of the Feed
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;}

    public int getNutritionalValue() { return nutritionalValue; }

    public void setNutritionalValue (int nutritionalValue) {this.nutritionalValue = nutritionalValue;}

    // Override of the equals method checking id, name, description and nutritional value, too
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feed Feed = (Feed) o;
        return Objects.equals(id, Feed.id) &&
                Objects.equals(name, Feed.name) &&
                Objects.equals(description, Feed.description) &&
                Objects.equals(nutritionalValue, Feed.nutritionalValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    protected String fieldsString() {
        return "id=" + id + ", name='" + name + "', description='" + description + "'"
                + ", nutritional value=" + nutritionalValue + '\'';
    }

    @Override
    public String toString() {
        return "Feed{ " + fieldsString() +" }";
    }
}
