package game.arena.strategy;

import exceptions.DodgeException;
import exceptions.LoseToNeutrelException;
import game.Battle;
import model.Pokemon;
import model.PokemonType;
import util.PokemonFactory;

import java.util.Random;

/**
 * There are different strategies for implementing a battle between two pokemons,
 * the role of AbstractStrategy is to provide a framework for a fight between
 * two pokemons, checking various stats, decrementing cooldown ticks etc.
 */
public abstract class AbstractStrategy implements IArenaStrategy{

    protected Pokemon pokemon1;
    protected Pokemon pokemon2;
    protected Battle battle;


    public AbstractStrategy(Pokemon pokemon1, Pokemon pokemon2, Battle battle) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.battle = battle;
    }

    /**
     * Each ability has a tick, when reseted the pokemon can use the ability again
     * @param attacker
     * @param abilityUsed
     */
    private void tick(Pokemon attacker, int abilityUsed){
        if(abilityUsed ==2 || abilityUsed == 0) {
            if (attacker.getAbility1() != null && attacker.getAbility1().isInUseCurrently()) {
                attacker.getAbility1().tick();
            }
        }
        if(abilityUsed ==1 || abilityUsed == 0) {
            if (attacker.getAbility2() != null && attacker.getAbility2().isInUseCurrently()) {
                attacker.getAbility2().tick();
            }
        }
    }

    /**
     * Method which simulates a certain action on behalf of the "attacker" pokemon, and call tick if necessary
     * @param attacker
     * @param defender
     * @param action
     * @return
     * @throws DodgeException
     */
    private boolean action(Pokemon attacker, Pokemon defender, int action) throws DodgeException {


        if(action == 0){
            if(attacker.getAbility1() != null) {
                attacker.getAbility1().setUsedLastRound(false);
            }
            if(attacker.getAbility2() != null) {
                attacker.getAbility2().setUsedLastRound(false);
            }
            Random rand = new Random();
            boolean specialDefence = rand.nextBoolean();
            int defPoints = specialDefence ? defender.getSpecialDef() : defender.getDef();

            if(defender.isDodge()){
                defender.setDodge(false);
                throw new DodgeException();
            }
            if(attacker.getAttack() != null){

                int damage = attacker.getAttack() - defPoints;
                if (damage < 0) {
                    damage = -damage;
                }
                defender.setHp(defender.getHp() - damage);


            }else{
                int damage = attacker.getSpecialAttack() - defPoints;
                if (damage < 0) {
                    damage = -damage;
                }
                defender.setHp(defender.getHp() - damage);
            }
//            tick(attacker, 0);
        }else if(action == 1){

            if(attacker.getAbility1().getCooldown() == attacker.getAbility1().getInitialCooldown()) {
                attacker.getAbility1().setUsedLastRound(true);
                attacker.getAbility1().setInUseCurrently(true);

                if(attacker.getAbility1().getDamage() != null){
                    defender.setHp(defender.getHp() - attacker.getAbility1().getDamage());
                }

                if(attacker.getAbility1().isStun()){
                    defender.setStunned(true);
                }
                if(attacker.getAbility1().isDodge()){
                    attacker.setDodge(true);
                }

            }else{
                return false;
            }

        }else if(action == 2){


            if(attacker.getAbility2().getCooldown() == attacker.getAbility2().getInitialCooldown()) {
                attacker.getAbility2().setUsedLastRound(true);
                attacker.getAbility2().setInUseCurrently(true);


                if(attacker.getAbility2().getDamage() != null){
                    defender.setHp(defender.getHp() - attacker.getAbility2().getDamage());
                }

                if(attacker.getAbility2().isStun()){
                    defender.setStunned(true);
                }
                if(attacker.getAbility2().isDodge()){
                    attacker.setDodge(true);
                }

            }else{
                return false;
            }

        }else{
            throw new RuntimeException("Invalid action specified");
        }

        if(action == 1 || action == 2 || action == 0){
            if(attacker.getAbility1() != null && attacker.getAbility1().isInUseCurrently()){
                tick(attacker, 2);
            }
            if(attacker.getAbility2() != null && attacker.getAbility2().isInUseCurrently()){
                tick(attacker, 1);
            }
        }

        return true;
    }

    /**
     * The fight will call action() method repeatedly, in order to generate the interaction between the
     * two fighting pokemons. When the fight is over, the winner pokemon is returned, null if draw
     * @return
     * @throws LoseToNeutrelException
     */
    @Override
    public Pokemon fight() throws LoseToNeutrelException {
        this.battle.setState("----****"+pokemon1.getPokemonType()+" VS "+pokemon2.getPokemonType()+"****----\n");
        pokemon1.reset(PokemonFactory.createPokemon(pokemon1.getPokemonType()));
        pokemon2.reset(PokemonFactory.createPokemon(pokemon2.getPokemonType()));

        pokemon1.equipItems();
        pokemon2.equipItems();
        // (atac / atac special) / ab1 / ab2
        // 0 1 2
        Random random = new Random();

        int counter = 0;  //counter for corner cases (rounds over 100) / battle is looping.

        while(pokemon1.getHp() > 0 && pokemon2.getHp() > 0){
            int action = random.nextInt(3);
            boolean canAction = false;
            boolean pokemon1Dodged = false;
            boolean pokemon2Dodged = false;
            if(!pokemon1.isStunned()) {
                try {
                    canAction = action(pokemon1, pokemon2, action);
                } catch (DodgeException e) {
                    // e.printStackTrace();
                    pokemon2Dodged = true;
                }
            }else{
                // no action
                pokemon1.setStunned(false);
            }
            if(!canAction){
                try {
                    action(pokemon1, pokemon2, 0);
                } catch (DodgeException e) {
                    //e.printStackTrace();
                    pokemon2Dodged = true;
                }
                action = 0;
            }

            String stunnedP1 = "";
            String stunnedP2 = "";
            String cooldownP1 = "";
            String cooldownP2 = "";
            if(action == 0) {
                String attackType = pokemon1.getAttack()  != null ? " atac normal " : " atac special ";
                this.battle.setState(  pokemon1.getPokemonType() + " " + attackType + " / " + pokemon2.getPokemonType() );
            }else if(action == 1){
                this.battle.setState(pokemon1.getPokemonType() + " abilitate 1 " + " / " + pokemon2.getPokemonType());
                if(pokemon1.getAbility1().isStun()){
                    stunnedP1 = " (abilitatea 1 are stun) ";
                    stunnedP2 = "nu va executa nicio acțiune la următoare mișcare din cauza abilității 1 a lui " + pokemon1.getPokemonType() + ")";
                }
            }else if(action == 2){
                this.battle.setState(pokemon1.getPokemonType() + " abilitate 2" + " / " + pokemon2.getPokemonType());
                if(pokemon1.getAbility2().isStun()){
                    stunnedP1 = " (abilitatea 2 are stun) ";
                    stunnedP2 = " (nu va executa nicio acțiune la următoare mișcare din cauza abilității 2 a lui " + pokemon1.getPokemonType() + ")";
                    // cooldownP1C2 = "abilitatea 2 cooldown " + pokemon1.getAbility2().getCooldown();
                }
            }
            cooldownP1 += pokemon1.getAbility1() != null && pokemon1.getAbility1().isInUseCurrently() ? " abilitatea 1 cooldown " + pokemon1.getAbility1().getCooldown() : "";
            cooldownP1 += pokemon1.getAbility2() != null && pokemon1.getAbility2().isInUseCurrently() ?  " abilitatea 2 cooldown " + pokemon1.getAbility2().getCooldown() : "";


            action = random.nextInt(3);
            if(pokemon2.getPokemonType().equals(PokemonType.NEUTREL1) || pokemon2.getPokemonType().equals(PokemonType.NEUTREL2)){
                action = 0;
            }
            if(!pokemon2.isStunned()) {
                try {
                    canAction = action(pokemon2, pokemon1, action);
                } catch (DodgeException e) {
//                    e.printStackTrace();
                    pokemon1Dodged = true;
                }
            }else{
                pokemon2.setStunned(false);
            }
            if(!canAction){
                try {
                    action(pokemon2, pokemon1, 0);
                } catch (DodgeException e) {
//                    e.printStackTrace();
                    pokemon1Dodged = true;
                }
                action = 0;
            }
            if(action == 0) {
                String attackType = pokemon2.getAttack()  != null ? " atac normal " : " atac special ";
                this.battle.setState(attackType );
            }else if(action == 1){
                this.battle.setState(" abilitate 1 " );
                if(pokemon1.getAbility1().isStun()){
                    stunnedP1 = " (abilitatea 1 are stun) ";
                    stunnedP2 = "nu va executa nicio acțiune la următoare mișcare din cauza abilității 1 a lui " + pokemon1.getPokemonType() + ")";
                }
            }else if(action == 2){
                this.battle.setState( " abilitate 2");
                if(pokemon1.getAbility2().isStun()){
                    stunnedP1 = " (abilitatea 2 are stun) ";
                    stunnedP2 = " (nu va executa nicio acțiune la următoare mișcare din cauza abilității 2 a lui " + pokemon1.getPokemonType() + ")";
                }
            }

            cooldownP2 += pokemon2.getAbility1() != null && pokemon2.getAbility1().isInUseCurrently() ? " abilitatea 1 cooldown " + pokemon2.getAbility1().getCooldown()  : "";
            cooldownP2 += pokemon2.getAbility2() != null && pokemon2.getAbility2().isInUseCurrently()?  " abilitatea 2 cooldown " + pokemon2.getAbility2().getCooldown() : "";


            this.battle.setState( " -> Rezultat: \n");


            this.battle.setState("a. " + pokemon1.getPokemonType() + " HP " + pokemon1.getHp() + (pokemon1Dodged ? " (dodge) " : "") + stunnedP1 + " "+cooldownP1 + "\n");
            this.battle.setState("b. " + pokemon2.getPokemonType() + " HP " + pokemon2.getHp() +  (pokemon2Dodged ? " (dodge) " : "") + stunnedP2 +  " " + cooldownP2+ "\n");
            counter++;
            if(counter > 100){
                break;
            }

            // check if either pokemon is dead
        }
        if(pokemon1.getHp() <= 0 && pokemon2.getHp() <= 0){
            this.battle.setState("DRAW\n");
            return null;
        }
        if(pokemon1.getHp() > 0){
            this.battle.setState("POKEMON 1: " + pokemon1.getPokemonType() + " WON\n");
            this.battle.setState("WINNER : " + pokemon1.getTheTrainer().getName() +" WON\n");
            pokemon1.winBattle();
            return pokemon1;
        }else{
            this.battle.setState("POKEMON 2: " + pokemon2.getPokemonType() + " WON\n");
            this.battle.setState("WINNER : " + pokemon2.getTheTrainer().getName()+" WON\n");
            pokemon2.winBattle();
            if(pokemon2.getPokemonType().equals(PokemonType.NEUTREL1) || pokemon2.getPokemonType().equals(PokemonType.NEUTREL2)){
                throw new LoseToNeutrelException();
            }
            return pokemon2;
        }
    }
}
