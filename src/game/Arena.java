package game;

import game.arena.strategy.*;
import model.Pokemon;
import model.Trainer;
import util.logger.AbstractSubject;
import util.logger.ObserverFile;
import util.logger.ObserverSout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Singleton class which instantiates the required battles between the pokemons (PVN, PVN2, PVP and final battle PVP)
 */
public class Arena extends AbstractSubject {

    private Trainer trainer1;
    private Trainer trainer2;


    private static Arena theInstance;

    private Arena(){

    }

    public static Arena getTheInstance(){
        if(theInstance == null){
            theInstance = new Arena();
        }
        return theInstance;
    }

    public Trainer getTrainer1() {
        return trainer1;
    }

    public void setTrainer1(Trainer trainer1) {
        this.trainer1 = trainer1;
    }

    public Trainer getTrainer2() {
        return trainer2;
    }

    public void setTrainer2(Trainer trainer2) {
        this.trainer2 = trainer2;
    }

    /**
     * Instantiating the battles and loggers accordingly
     * @param arenaNumber
     */
    public void fight(int arenaNumber){


        Battle battle1 = new Battle(this.trainer1.getPokemons().get(0), this.trainer2.getPokemons().get(0), false);
        Battle battle2 = new Battle(this.trainer1.getPokemons().get(1), this.trainer2.getPokemons().get(1), false);
        Battle battle3 = new Battle(this.trainer1.getPokemons().get(2), this.trainer2.getPokemons().get(2), false);
        ObserverSout observerSout = new ObserverSout(battle1);
        ObserverSout observerSout2 = new ObserverSout(battle2);
        ObserverSout observerSout3 = new ObserverSout(battle3);
        ObserverFile observerFile = new ObserverFile(battle1, "output" + arenaNumber + ".out");
        ObserverFile observerFile2 = new ObserverFile(battle2, "output" + arenaNumber + ".out");
        ObserverFile observerFile3 = new ObserverFile(battle3, "output" + arenaNumber + ".out");
        List<Battle> battles = Stream.of(battle1, battle2, battle3).collect(Collectors.toList());       //Collecting all stream elements into a list instance
        ObserverFile observerFileArena = new ObserverFile(this, "output" + arenaNumber + ".out");

        ExecutorService pool=Executors.newFixedThreadPool(1); // battles.size()

        ExecutorCompletionService<Pokemon> completionService =
                new ExecutorCompletionService<>(pool);

        for(int i = 0; i < battles.size(); i++) {
            completionService.submit(battles.get(i));
        }

        int received = 0;
        boolean errors = false;

        Pokemon strongerP1 = null;
        Pokemon strongerP2 = null;

        while(received < battles.size() && !errors) {
            Future<Pokemon> resultFuture = null; //blocks if none available
            try {
                resultFuture = completionService.take();
                Pokemon result = resultFuture.get();
                System.out.println("IS DONE: " + resultFuture.isDone());
                received ++;
            } catch (Exception e) {
                e.printStackTrace();
                errors = true;
            }
        }

        strongerP1 = this.trainer1.getPokemons().get(0);
        strongerP2 = this.trainer2.getPokemons().get(0);
        // hp + atac normal + atac special + defense + defense special
        for(Pokemon p : this.trainer1.getPokemons()){
            if(strongerP1.getSumStats() == p.getSumStats()){
                if(strongerP1.getPokemonType().toString().compareTo(p.getPokemonType().toString()) > 0){
                    strongerP1 = p;
                }
            }else {
                if (strongerP1.getSumStats() < p.getSumStats()) {
                    strongerP1 = p;
                }
            }
        }
        for(Pokemon p : this.trainer2.getPokemons()){
            if(strongerP2.getSumStats() == p.getSumStats()){
                if(strongerP2.getPokemonType().toString().compareTo(p.getPokemonType().toString()) > 0){
                    strongerP2 = p;
                }
            }else {
                if (strongerP2.getSumStats() < p.getSumStats()) {
                    strongerP2 = p;
                }
            }
        }

        System.out.println("------ALL 3 battles are over------");
        System.out.println("************FINAL BATTLE******************");
        Battle finalBattle = new Battle(strongerP1, strongerP2, true);
        ObserverSout observerSoutFinalBattle = new ObserverSout(finalBattle);

        completionService.submit(finalBattle);
        try {
            Future<Pokemon> resultFuture = completionService.take();
            Pokemon result = resultFuture.get();
            if(result == null){
                System.out.println("DRAW");
//                this.setState("DRAW\n");
            }else{
                System.out.println("WINNER: " + result.getTheTrainer().getName());
//                this.setState("WINNER: " + result.getTheTrainer().getName() + "\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        pool.shutdown();

    }
}
