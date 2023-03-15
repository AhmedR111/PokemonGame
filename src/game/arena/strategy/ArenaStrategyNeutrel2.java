package game.arena.strategy;

import game.Battle;
import model.Pokemon;
import model.PokemonType;
import util.PokemonFactory;

public class ArenaStrategyNeutrel2   extends AbstractStrategy{

    public ArenaStrategyNeutrel2(Pokemon pokemon1, Battle battle) {
        super(pokemon1, PokemonFactory.createPokemon(PokemonType.NEUTREL2), battle);
    }


}
