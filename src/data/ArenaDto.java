package data;

import model.Trainer;

/**
 * Data Transfer Object class dedicated to be used as a "container" when reading data from the input files
 */
public class ArenaDto {

    private Trainer trainer1;
    private Trainer trainer2;

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


    @Override
    public String toString() {
        return "ArenaDto{" +
                "trainer1=" + trainer1 +
                ", trainer2=" + trainer2 +
                '}';
    }
}
