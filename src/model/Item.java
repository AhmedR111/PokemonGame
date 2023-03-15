package model;

import java.util.Objects;

public class Item {

    private Integer hp;
    private Integer attack;
    private Integer specialAttack;
    private Integer defense;
    private Integer specialDefense;

    public Item(){

    }

    public Item(Integer hp, Integer attack, Integer specialAttack, Integer defense, Integer specialDefense) {
        this.hp = hp;
        this.attack = attack;
        this.specialAttack = specialAttack;
        this.defense = defense;
        this.specialDefense = specialDefense;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(Integer specialDefense) {
        this.specialDefense = specialDefense;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(hp, item.hp) && Objects.equals(attack, item.attack) &&
                Objects.equals(specialAttack, item.specialAttack) &&
                Objects.equals(defense, item.defense) &&
                Objects.equals(specialDefense, item.specialDefense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, attack, specialAttack, defense, specialDefense);
    }

    @Override
    public String toString() {
        return "Item{" +
                "hp=" + hp +
                ", attack=" + attack +
                ", specialAttack=" + specialAttack +
                ", defense=" + defense +
                ", specialDefense=" + specialDefense +
                '}';
    }
}
