package at.ac.tuwien.sepm.assignment.individual.dto;

import java.util.Objects;

public class FeedDto {
    private Long id;
    private String name;
    private String description;
    private int nutritionalValue;

    public FeedDto() {
    }

    public FeedDto(String name) {
        this.name = name;
    }

    public FeedDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FeedDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public FeedDto(Long id, String name, String description, int nutritionalValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nutritionalValue = nutritionalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNutritionalValue() { return nutritionalValue; }

    public void setNutritionalValue(int nutritionalValue) { this.nutritionalValue = nutritionalValue; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedDto FeedDto = (FeedDto) o;
        return Objects.equals(id, FeedDto.id) &&
                Objects.equals(name, FeedDto.name) &&
                Objects.equals(description, FeedDto.description) &&
                Objects.equals(nutritionalValue, FeedDto.nutritionalValue)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    private String fieldsString() {
        return "id=" + id + ", name='" + name + "', description='" + description + "'"
                +", nutritionalValue=" + nutritionalValue + '\'';
    }

    @Override
    public String toString() {
        return "FeedDto{ " + fieldsString() + " }";
    }
}
