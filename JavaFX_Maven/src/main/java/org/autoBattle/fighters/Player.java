package org.autoBattle.fighters;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.autoBattle.battle.BossUpgrade;

import java.util.ArrayList;

public class Player extends SquareEnemy {

    private final Text text = new Text();
    private int gold;
    private int goldAmount = 1;
    private int criticalHitChance = 1;
    private int criticalHitDamagePercent = 50;
    private ArrayList<BossUpgrade> bossUpgrades= new ArrayList<>();

    public Player(Rectangle rectangle, double xPos, double yPos, String color, AnchorPane scene, int maxHealth, int damage) {
        super(rectangle, xPos, yPos, color, scene, maxHealth, damage);
        gold = 0;
        text.setLayoutX(25);
        text.setLayoutY(40);
        scene.getChildren().add(text);
    }

    public int getGold() {
        return gold;
    }

    public void changeGoldAmount(){
        gold += goldAmount;
        updateGoldAmount();
    }

    public void changeGoldAmount(int amount){
        gold += amount;
        updateGoldAmount();
    }

    @Override
    public int takeDamage(int damage, Damage damageObject) {
        lowerCurrentHealth(damage);
        updateHealth();
        Text damageNumber = new Text();
        int textSize = 14;
        damageNumber.setStyle("-fx-fill: #550a12; -fx-font-size: " + textSize + "pt;");
        damageNumber.setText(Integer.toString(damage));
        damageNumber.setLayoutX(rectangle.getLayoutX() + (0.4 * rectangle.getWidth()) + random.nextInt((int) (0.3 * rectangle.getWidth())));
        damageNumber.setLayoutY(rectangle.getLayoutY() + (0.3 * rectangle.getHeight()) + random.nextInt((int) (0.3 * rectangle.getWidth())));
        getScene().getChildren().add(damageNumber);

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(150));
        pauseTransition.setOnFinished(e->{
            getScene().getChildren().remove(damageNumber);
        });

        pauseTransition.play();
        return damage;
    }

    public void heal(int healAmount){
        int realHealAmount = healAmount;
        if(getCurrentHealth() + healAmount < getMaxHealth()){
            changeCurrentHealth(healAmount);
            updateHealth();
        } else if (getCurrentHealth() + healAmount >= getMaxHealth()) {
            resetHealth();
        }
    }

    public void updateGoldAmount(){
        text.setText("Gold: " + gold);
    }

    public String getStats(){
        return "Damage: " + getDamage()
                + "\nHealth: " + getCurrentHealth() + "/" + getMaxHealth()
                + "\nCritical Hit chance: " + criticalHitChance + "%"
                + "\nCritical Hit damage: " + criticalHitDamagePercent + "%";
    }

    public void deletePLayer(){
        getScene().getChildren().removeAll(rectangle,getHealthBar(),getHealthText(),text);
    }

    public int getPlayerDamage(){
        int damage = getDamage();

        int critChance = random.nextInt(100) + 1;
        if(critChance < criticalHitChance){
            damage = (int) (damage * (1 + criticalHitDamagePercent/100.));
            getDamageObject().nexHitIsCrit();
        }
        return damage;
    }

    public void upgradeCritDamage(int upgrade){
        criticalHitDamagePercent += upgrade;
    }

    public void upgradeCritChance(int upgrade){
        criticalHitChance += upgrade;
    }

    public EventHandler<ActionEvent> addBossUpgrade(BossUpgrade bossUpgrade){
        return e -> {
            bossUpgrade.triggerWhenPicked();
            bossUpgrades.add(bossUpgrade);
        };
    }

    public void triggerAtStartOfFight(){
        System.out.println(bossUpgrades.size());
    }

    public ArrayList<BossUpgrade> getBossUpgrades() {
        return bossUpgrades;
    }

    public void triggerAfterKillingEnemy(){
        changeGoldAmount();
        getScene().lookup("#goCamp").setDisable(false);

        for (BossUpgrade bossUpgrade: bossUpgrades) {
            if(bossUpgrade.isEveryTurnTrigger()){
                bossUpgrade.triggerEveryTurn();
            }
        }
    }
}
