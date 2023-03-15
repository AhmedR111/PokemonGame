package model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {


    private String name;
    private Integer age;
    private List<Item> items = new ArrayList<>();
    private List<Pokemon> pokemons = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", items=" + items +
                ", pokemons=" + pokemons +
                '}';
    }
}
