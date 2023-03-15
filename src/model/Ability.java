package model;

public class Ability {
    private Integer damage;
    private boolean stun;
    private boolean dodge;
    private Integer cooldown;
    private final int initialCooldown;
    private boolean usedLastRound = false;
    private boolean inUseCurrently = false;


    public Ability(Ability ability){

        this.initialCooldown = ability.initialCooldown;
        this.damage = ability.damage;
        this.cooldown = ability.initialCooldown;
        this.inUseCurrently = ability.inUseCurrently;
        this.usedLastRound = ability.usedLastRound;
        this.dodge = ability.dodge;
//        System.out.println("SETTING COOLDOWN TO: " + this.cooldown);
    }

    public Ability(Integer damage, boolean stun, boolean dodge, Integer cooldown) {
        this.damage = damage;
        this.stun = stun;
        this.dodge = dodge;
        this.cooldown = cooldown;
        this.initialCooldown = cooldown;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }


    public boolean isStun() {
        return stun;
    }

    public void setStun(boolean stun) {
        this.stun = stun;
    }

    public boolean isDodge() {
        return dodge;
    }

    public void setDodge(boolean dodge) {
        this.dodge = dodge;
    }

    public Integer getCooldown() {
        return cooldown;
    }


    public boolean isUsedLastRound() {
        return usedLastRound;
    }

    public void setUsedLastRound(boolean usedLastRound) {
        this.usedLastRound = usedLastRound;
    }


    public boolean isInUseCurrently() {
        return inUseCurrently;
    }

    public void setInUseCurrently(boolean inUseCurrently) {
        this.inUseCurrently = inUseCurrently;
    }

    public void tick(){
//        System.out.println("TICK FOR ABILITY----------------------------");
        if(!this.inUseCurrently){
            return;
        }
        this.cooldown--;
        if(this.cooldown == 0){
            this.cooldown = this.initialCooldown;
            this.inUseCurrently = false;
        }
    }

    public int getInitialCooldown() {
        return initialCooldown;
    }

    public void reset(){
        this.inUseCurrently = false;
        this.cooldown = this.initialCooldown;
        this.usedLastRound = false;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "damage=" + damage +
                ", stun=" + stun +
                ", dodge=" + dodge +
                ", cooldown=" + cooldown +
                '}';
    }
}
