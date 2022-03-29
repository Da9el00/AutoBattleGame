package org.autoBattle.battle;

public class BossUpgrade {

    private boolean everyTurnTrigger;

    public BossUpgrade(boolean everyTurnTrigger) {
        this.everyTurnTrigger = everyTurnTrigger;
    }

    public void triggerEveryTurn(){
    }

    public void triggerWhenPicked(){
    }

    public boolean isEveryTurnTrigger() {
        return everyTurnTrigger;
    }
}
