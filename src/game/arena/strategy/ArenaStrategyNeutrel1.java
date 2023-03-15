package game.arena.strategy;

import game.Battle;
import model.Pokemon;
import model.PokemonType;
import util.PokemonFactory;

public class ArenaStrategyNeutrel1 extends AbstractStrategy{

    public ArenaStrategyNeutrel1(Pokemon pokemon1, Battle battle) {
        super(pokemon1, PokemonFactory.createPokemon(PokemonType.NEUTREL1), battle);
    }


}
