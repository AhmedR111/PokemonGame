package game;

import exceptions.LoseToNeutrelException;
import game.arena.strategy.ArenaStrategyNeutrel1;
import game.arena.strategy.ArenaStrategyNeutrel2;
import game.arena.strategy.ArenaStrategyPVP;
import game.arena.strategy.IArenaStrategy;
import model.Ability;
import model.Item;
import model.Pokemon;
import model.Trainer;
import util.PokemonFactory;
import util.logger.AbstractSubject;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class Battle extends AbstractSubject implements Callable<Pokemon>{

    private IArenaStrategy strategy;

    private Ability ability1P1;
    private Ability ability2P1;

    private Ability ability1P2;
    private Ability ability2P2;

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    private boolean finalFight;
    public Battle(Pokemon pokemon1, Pokemon pokemon2, boolean finalFight) {
        this.ability1P1 = new Ability(pokemon1.getAbility1());
        this.ability2P1 = new Ability(pokemon1.getAbility2());
        this.ability1P2 = new Ability(pokemon2.getAbility1());
        this.ability2P2 = new Ability(pokemon2.getAbility2());

        this.finalFight = finalFight;
        this.pokemon1 = pokemon1;
        if(this.pokemon1.getAbility1() != null){
            this.pokemon1.setAbility1(new Ability(this.pokemon1.getAbility1()));
        }
        if(this.pokemon1.getAbility2() != null){
            this.pokemon1.setAbility2(new Ability(this.pokemon1.getAbility2()));
        }
        this.pokemon2 = pokemon2;
        if(this.pokemon2.getAbility1() != null){
            this.pokemon2.setAbility1(new Ability(this.pokemon2.getAbility1()));
        }
        if(this.pokemon2.getAbility2() != null){
            this.pokemon2.setAbility2(new Ability(this.pokemon2.getAbility2()));
        }
    }


    private void reset(){
        System.out.println("RESETTING POKEMON++++++++++++++++++++++++++++");
        if(this.pokemon1.getAbility1() != null){
            this.pokemon1.setAbility1(new Ability(ability1P1));
        }
        if(this.pokemon1.getAbility2() != null){
            this.pokemon1.setAbility2(new Ability(ability2P1));
        }
        if(this.pokemon2.getAbility1() != null){
            this.pokemon2.setAbility1(new Ability(ability1P2));
        }
        if(this.pokemon2.getAbility2() != null){
            this.pokemon2.setAbility1(new Ability(ability2P2));
        }
    }

    private Pokemon commence(){

            this.reset();
            Random random = new Random();
            IArenaStrategy strategyP1 = null;
            IArenaStrategy strategyP2 = null;

            pickItems(pokemon1);
            pickItems(pokemon2);

            System.out.println("ITEMS p1 (" + pokemon1.getPokemonType() + ") : " + pokemon1.getEquipedItems());
            System.out.println("ITEMS p2 (" + pokemon2.getPokemonType() + ") : " + pokemon2.getEquipedItems());

            boolean p1LostToNeutrel = false;
            boolean p2LostToNeutrel = false;

        Pokemon winner = null;

        if(!finalFight) {

            int result = random.nextInt(3);
            while (result != 2) {

                if (result == 0) {

                    strategyP1 = new ArenaStrategyNeutrel1(pokemon1, this);
                    strategyP2 = new ArenaStrategyNeutrel1(pokemon2, this);
                } else if (result == 1) {
                    strategyP1 = new ArenaStrategyNeutrel2(pokemon1, this);
                    strategyP2 = new ArenaStrategyNeutrel2(pokemon2, this);
                }
                try {
                    strategyP1.fight();
                } catch (LoseToNeutrelException e) {
                    p1LostToNeutrel = true;
                }
                try {
                    strategyP2.fight();
                } catch (LoseToNeutrelException e) {
                    p2LostToNeutrel = true;
                }
                result = random.nextInt(3);
            }
            if (p1LostToNeutrel) {
                this.setState(pokemon1.getPokemonType() + " DIED");
            }
            if (p2LostToNeutrel) {
                this.setState(pokemon2.getPokemonType() + " DIED");
            }
            IArenaStrategy strategyPVP = new ArenaStrategyPVP(pokemon1, pokemon2, this);
            try {
                strategyPVP.fight();
            } catch (LoseToNeutrelException e) {

            }
        }else{

            IArenaStrategy strategyPVP = new ArenaStrategyPVP(pokemon1, pokemon2, this);
            try {
                winner = strategyPVP.fight();

            } catch (LoseToNeutrelException e) {

            }
        }

            pokemon1.reset(PokemonFactory.createPokemon(pokemon1.getPokemonType()));
            pokemon2.reset(PokemonFactory.createPokemon(pokemon2.getPokemonType()));
            this.reset();

            System.out.println("FINAL BATTLE WINNER: " + winner);
            return winner;
    }

    private void pickItems(Pokemon pokemon){
        Trainer trainer = pokemon.getTheTrainer();
        if(trainer !=null) {
            List<Item> trainerItems = trainer.getItems();
            if (trainerItems.size() <= 3) {
                pokemon.getEquipedItems().addAll(trainerItems);
            } else {
                Random random = new Random();
                while (pokemon.getEquipedItems().size() < 3) {
                    pokemon.getEquipedItems().add(trainerItems.get(random.nextInt(trainerItems.size())));
                }
            }
        }
    }

    @Override
    public Pokemon call() throws Exception {
        System.out.println("COMMENCING BATTLE BETWEEN " + pokemon1.getPokemonType() + " & "+ pokemon2.getPokemonType());
        Pokemon winner = this.commence();

//        lag();

        System.out.println("BATTLE ENDED");
        return winner;
    }
}
