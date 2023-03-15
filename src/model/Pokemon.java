package model;

import java.util.HashSet;
import java.util.Set;

public class Pokemon {

    private Integer hp;
    private Integer attack;
    private Integer specialAttack;
    private Integer def;
    private Integer specialDef;
    private boolean stunned;
    private boolean dodge;
    private final int initialHP;
    private int battlesWon = 0;

    private Trainer theTrainer;

    private PokemonType pokemonType;

    private Ability ability1;
    private Ability ability2;

    public Pokemon(int initialHP) {
        this.initialHP = initialHP;
    }

    /**
     * After each fight the pokemon stats should be "reset" to the default values
     * in order for the battles not to "leak" into each other. Also, the reset checks
     * the number of fights won by each pokemon in order to increase the stats accordingly
     * @param pokemon
     */
    public void reset(Pokemon pokemon){
        this.def = pokemon.def;
        this.ability1 = pokemon.getAbility1() != null? new Ability(pokemon.getAbility1()) : null;
        this.ability2 = pokemon.getAbility2() != null? new Ability(pokemon.getAbility2()) : null;
        this.attack = pokemon.attack;
        this.specialAttack = pokemon.specialAttack;
        this.specialDef = pokemon.specialDef;
        this.hp = initialHP;
        this.pokemonType = pokemon.pokemonType;

        for(int i=0; i<this.battlesWon; i++){
            this.hp++;
            if(this.attack != null){
                this.attack++;
            }
            if(this.specialAttack!= null){
                this.specialAttack++;
            }
            if(this.def != null){
                this.def++;
            }
            if(this.specialDef != null){
                this.specialDef++;
            }

        }

    }

    /**
     * Method used in the arena before the final fight in order to determine the "best" pokemon
     * @return
     */
    public int getSumStats(){
        int result = 0;
        // hp + atac normal + atac special + defense + defense special
        result += this.hp + (this.attack != null ? this.attack : 0)
                + (this.specialAttack != null ? this.specialAttack : 0)+
                (this.def != null ? this.def : 0)+
                (this.specialDef != null ? this.specialDef : 0);
        return result;
    }

    private Set<Item> equipedItems = new HashSet<>();

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

    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getSpecialDef() {
        return specialDef;
    }

    public void setSpecialDef(Integer specialDef) {
        this.specialDef = specialDef;
    }

    public Ability getAbility1() {
        return ability1;
    }

    public void setAbility1(Ability ability1) {
        this.ability1 = ability1;
    }

    public Ability getAbility2() {
        return ability2;
    }

    public void setAbility2(Ability ability2) {
        this.ability2 = ability2;
    }

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
    }


    public Set<Item> getEquipedItems() {
        return equipedItems;
    }

    /**
     * Make sure that the items are added to each pokemon instance
     */
    public void equipItems(){
        for(Item item : equipedItems){
            if(item.getAttack() != null && this.attack != null){
                this.attack += item.getAttack();
            }
            if(item.getHp() != null){
                this.hp += item.getHp();
            }
            if(item.getSpecialAttack() != null && this.specialAttack != null){
                this.specialAttack += item.getSpecialAttack();
            }
            if(item.getDefense() != null && this.def != null){
                this.def += item.getDefense();
            }
            if(item.getSpecialDefense() != null && this.specialAttack != null) {
                this.specialDef += item.getSpecialDefense();
            }
        }
    }


    public Trainer getTheTrainer() {
        return theTrainer;
    }

    public void setTheTrainer(Trainer theTrainer) {
        this.theTrainer = theTrainer;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public boolean isDodge() {
        return dodge;
    }

    public void setDodge(boolean dodge) {
        this.dodge = dodge;
    }

    public void winBattle(){
        this.battlesWon++;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "hp=" + hp +
                ", attack=" + attack +
                ", specialAttack=" + specialAttack +
                ", def=" + def +
                ", specialDef=" + specialDef +
                ", pokemonType=" + pokemonType +
                ", ability1=" + ability1 +
                ", ability2=" + ability2 +
                '}';
    }
}
