package util;

import model.Ability;
import model.Pokemon;
import model.PokemonType;

public class PokemonFactory {

    /***
     * Factory method to create Pokemon instances according to a specified type
     * @param pokemonType
     * @return
     */
    public static Pokemon createPokemon(PokemonType pokemonType){
        Pokemon result = null;

        Ability ability1 = null;
        Ability ability2 = null;
        switch(pokemonType){

            case NEUTREL1:
                result = new Pokemon(10);
                result.setHp(10);
                result.setAttack(3);
                result.setDef(1);
                result.setSpecialDef(1);
                break;

            case NEUTREL2:
                result = new Pokemon(20);
                result.setHp(20);
                result.setAttack(4);
                result.setDef(1);
                result.setSpecialDef(1);
                break;

            case PIKACHU:
                result = new Pokemon(35);
                result.setHp(35);
                result.setSpecialAttack(4);
                result.setDef(2);
                result.setSpecialDef(3);
                ability1 = new Ability(6, false, false, 4);
                ability2 = new Ability(4, true, true, 5);
                break;

            case BULBASAUR:
                result = new Pokemon(42);
                result.setHp(42);
                result.setSpecialAttack(5);
                result.setDef(3);
                result.setSpecialDef(1);
                ability1 = new Ability(6, false, false, 4);
                ability2 = new Ability(5, true, true, 3);
                break;

            case CHARMANDER:
                result = new Pokemon(50);
                result.setHp(50);
                result.setAttack(4);
                result.setDef(3);
                result.setSpecialDef(2);
                ability1 = new Ability(4, true, false, 4);
                ability2 = new Ability(7, false, false, 6);
                break;

            case SQUIRTLE:
                result = new Pokemon(60);
                result.setHp(60);
                result.setSpecialAttack(3);
                result.setDef(5);
                result.setSpecialDef(5);
                ability1 = new Ability(4, false, false, 3);
                ability2 = new Ability(2, true, false, 2);
                break;

            case SNORLAX:
                result = new Pokemon(62);
                result.setHp(62);
                result.setAttack(3);
                result.setDef(6);
                result.setSpecialDef(4);
                ability1 = new Ability(4, true, false, 5);
                ability2 = new Ability(0, false, true, 5);
                break;

            case VULPIX:
                result = new Pokemon(36);
                result.setHp(36);
                result.setAttack(5);
                result.setDef(2);
                result.setSpecialDef(4);
                ability1 = new Ability(8, true, false, 6);
                ability2 = new Ability(2, false, true, 7);
                break;

            case EEVEE:
                result = new Pokemon(39);
                result.setHp(39);
                result.setSpecialAttack(4);
                result.setDef(3);
                result.setSpecialDef(3);
                ability1 = new Ability(5, false, false, 3);
                ability2 = new Ability(3, true, false, 3);
                break;

            case JIGGLYPUFF:
                result = new Pokemon(34);
                result.setHp(34);
                result.setAttack(4);
                result.setDef(2);
                result.setSpecialDef(3);
                ability1 = new Ability(4, true, false, 4);
                ability2 = new Ability(3, true, false, 4);
                break;

            case MEOWTH:
                result = new Pokemon(41);
                result.setHp(41);
                result.setAttack(3);
                result.setDef(4);
                result.setSpecialDef(2);
                ability1 = new Ability(5, false, true, 4);
                ability2 = new Ability(1, false, true, 3);
                break;

            case PSYDUCK:
                result = new Pokemon(43);
                result.setHp(43);
                result.setAttack(3);
                result.setDef(3);
                result.setSpecialDef(3);
                ability1 = new Ability(2, false, true, 4);
                ability2 = new Ability(2, true, false, 5);
                break;

            default:
                throw new UnsupportedOperationException("NO POKEMON FOUND");
        }
        result.setPokemonType(pokemonType);

        result.setAbility1(ability1);
        result.setAbility2(ability2);

        return result;
    }



}
