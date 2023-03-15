package game.arena.strategy;

import game.Battle;
import model.Pokemon;
import model.Trainer;

public class ArenaStrategyPVP extends AbstractStrategy{

    public ArenaStrategyPVP(Pokemon pokemon1, Pokemon pokemon2, Battle battle) {
        super(pokemon1, pokemon2, battle);
    }

}
