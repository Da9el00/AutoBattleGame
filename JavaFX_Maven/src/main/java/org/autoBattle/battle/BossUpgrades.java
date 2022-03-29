package org.autoBattle.battle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.autoBattle.fighters.Player;

import java.util.ArrayList;
import java.util.Random;

public class BossUpgrades {

    private HBox bossUpgrades;
    private AnchorPane scene;
    private Player player;
    Random random = new Random();
    ArrayList<Button> bossUpgradeButtons = new ArrayList<>();

    public BossUpgrades(AnchorPane scene, Player player) {
        this.scene = scene;
        this.player = player;

    }

    public void showBossUpgrades(AnchorPane scene){

        bossUpgrades = new HBox();

        bossUpgrades.setPrefHeight(300);
        bossUpgrades.setPrefWidth(400);

        bossUpgrades.setLayoutX(100);
        bossUpgrades.setLayoutY(75);

        scene.getChildren().add(bossUpgrades);

        BossUpgrade bossUpgrade = new BossUpgrade(true){
            @Override
            public void triggerEveryTurn(){
                player.heal((int) (player.getMaxHealth() * 0.1));
                System.out.println("Healed 10% of HP");
            }

            @Override
            public void triggerWhenPicked(){
                scene.getChildren().remove(bossUpgrades);
            }
        };
        createUpgrade("Heal 10% of max health \n at end of every round",bossUpgrade);


        BossUpgrade bossUpgrade1 = new BossUpgrade(true){
            @Override
            public void triggerEveryTurn(){
                player.upgradeDamage(10);
                System.out.println("Damage Upgrade + 10");
            }

            @Override
            public void triggerWhenPicked(){
                scene.getChildren().remove(bossUpgrades);
            }
        };
        createUpgrade("Get +10 damage \n at the end of every round",bossUpgrade1);

        BossUpgrade bossUpgrade2 = new BossUpgrade(true){
            @Override
            public void triggerEveryTurn(){
                player.upgradeMaxHealth(25);
                player.updateHealth();
                System.out.println("Health  Upgrade + 25");
            }

            @Override
            public void triggerWhenPicked(){
                scene.getChildren().remove(bossUpgrades);
            }
        };
        createUpgrade("Get +25 max Health \n at the end of every round",bossUpgrade2);


        showUpgrades(random, bossUpgradeButtons, bossUpgrades);


    }

    static void showUpgrades(Random random, ArrayList<Button> bossUpgradeButtons, HBox bossUpgrades) {
        int number = 0;
        int number1 = 0;

        while (number == number1){
            number = random.nextInt(bossUpgradeButtons.size());
            number1 = random.nextInt(bossUpgradeButtons.size());

        }
        bossUpgradeButtons.get(number).setFocusTraversable(false);
        bossUpgradeButtons.get(number1).setFocusTraversable(false);
        bossUpgrades.getChildren().add(bossUpgradeButtons.get(number));
        bossUpgrades.getChildren().add(bossUpgradeButtons.get(number1));
    }

    public void createUpgrade(String upgradeText, BossUpgrade bossUpgrade){
        Button button = new Button(upgradeText);
        button.setPrefHeight(300);
        button.setPrefWidth(200);

        button.setOnAction(player.addBossUpgrade(bossUpgrade));
        bossUpgradeButtons.add(button);
    }
}
