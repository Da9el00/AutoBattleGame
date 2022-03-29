package org.autoBattle.fighters;

public class Damage {
    private int damage;
    private boolean newHitIsCrit = false;

    public Damage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void increaseDamage(int increase){
        damage += increase;
    }

    public void nexHitIsCrit(){
        newHitIsCrit = true;
    }

    public boolean isNewHitIsCrit() {
        return newHitIsCrit;
    }

    public void critDamageDone(){
        newHitIsCrit = false;
    }
}
