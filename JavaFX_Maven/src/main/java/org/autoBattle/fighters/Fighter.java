package org.autoBattle.fighters;

import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public abstract class Fighter {
     private final Rectangle rectangle;
     private int maxHealth;
     private Damage damage;
     private int currentHealth;
     private ProgressBar healthBar;
     private final Text healthText;
     protected Random random = new Random();

    public Fighter(Rectangle rectangle, int maxHealth, int damage) {
        this.rectangle = rectangle;
        this.maxHealth = maxHealth;
        this.damage = new Damage(damage);
        healthBar = new ProgressBar();
        currentHealth = this.maxHealth;
        healthText = new Text();
    }

    public Text getHealthText() {
        return healthText;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void resetHealth(){
        currentHealth = maxHealth;
        updateHealth();
    }

    public int getDamage() {
        return damage.getDamage();
    }

    public Damage getDamageObject(){
        return damage;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    protected void lowerCurrentHealth(int damage){
        currentHealth -= damage;
    }

    public void changeCurrentHealth (int healAmount){
        currentHealth += healAmount;
    }

    public ProgressBar getHealthBar() {
        return healthBar;
    }

    public abstract int takeDamage(int damage, Damage damageObject);

    public abstract void removeSquareEnemy();

    public abstract void updateHealth();

    public boolean isDead(){
        return currentHealth <= 0;
    }

    public void upgradeMaxHealth(int upgradeAmount){
        maxHealth += upgradeAmount;
    }

    public void upgradeDamage(int upgradeAmount){
        damage.increaseDamage(upgradeAmount);
    }

}
