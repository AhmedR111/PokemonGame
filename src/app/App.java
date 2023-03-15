package app;

import data.ArenaData;
import data.ArenaDto;
import game.Arena;

public class App {

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            ArenaData arenaData = new ArenaData("battle"+i+".in");
            ArenaDto arenaDto = arenaData.findOne();
            Arena arena = Arena.getTheInstance();
            arena.setTrainer1(arenaDto.getTrainer1());
            arena.setTrainer2(arenaDto.getTrainer2());
            System.out.println("ARENA "+i+ " : " + arenaDto);
            arena.fight(i);
        }

    }

}
