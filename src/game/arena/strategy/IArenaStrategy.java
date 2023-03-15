package game.arena.strategy;

import exceptions.LoseToNeutrelException;
import model.Pokemon;

public interface IArenaStrategy {

    public Pokemon fight() throws LoseToNeutrelException;
}
