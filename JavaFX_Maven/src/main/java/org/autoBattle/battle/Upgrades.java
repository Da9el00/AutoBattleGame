package org.autoBattle.battle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.autoBattle.fighters.Player;

import java.util.ArrayList;
import java.util.Random;

public class Upgrades{

    private final Level level = Level.getInstance();
    private final BossUpgrades bossUpgrades;
    private final Player player;
    private final AnchorPane scene;
    ArrayList<Button> upgradeButtons = new ArrayList<>();
    Random random = new Random();

    public Upgrades(Player player, AnchorPane scene) {
        this.player = player;
        this.scene = scene;
        bossUpgrades = new BossUpgrades(scene,player);
    }

    public void showUpgrades(){

        if(level.levelIsABossBattle()){
            bossUpgrades.showBossUpgrades(scene);
            System.out.println("Boss");
            return;
        }

        HBox upgrades = new HBox();

        upgrades.setPrefHeight(300);
        upgrades.setPrefWidth(400);

        upgrades.setLayoutX(100);
        upgrades.setLayoutY(75);

        scene.getChildren().add(upgrades);

        int healthUpgrade = 25 + random.nextInt(30);

        //Health upgrade
        createUpgrade("Health upgrade \n+" + healthUpgrade,
                e->{
                    player.upgradeMaxHealth(healthUpgrade);
                    player.updateHealth();
                    scene.getChildren().remove(upgrades);
                });


        int damageUpgrade = 10 + random.nextInt(20);

        //Damage upgrade
        createUpgrade("Damage upgrade \n+" + damageUpgrade,
            e->{
            player.upgradeDamage(damageUpgrade);
            scene.getChildren().remove(upgrades);
        });

        //Heal upgrade
        createUpgrade("Heal to full",
                e->{
                    player.resetHealth();
                    scene.getChildren().remove(upgrades);
                });

        //+1 Gold
        createUpgrade("+3 gold",
                e->{
                    player.changeGoldAmount(3);
                    scene.getChildren().remove(upgrades);
                });


        //Crit damage upgrade
        int criticalHitDamageUpgrade = random.nextInt(15) + 5;
        createUpgrade("Critical Hit Damage \n+" + criticalHitDamageUpgrade + "%",
                e->{
                    player.upgradeCritDamage(criticalHitDamageUpgrade);
                    scene.getChildren().remove(upgrades);
                });


        //Crit chance upgrade
        int criticalHitChanceUpgrade = random.nextInt(5) + 2;
        createUpgrade("Critical Hit Chance \n+" + criticalHitChanceUpgrade + "%",
                e->{
                    player.upgradeCritChance(criticalHitChanceUpgrade);
                    scene.getChildren().remove(upgrades);
                });

        BossUpgrades.showUpgrades(random, upgradeButtons, upgrades);
    }

    public void createUpgrade(String upgradeText, EventHandler<ActionEvent> e){
        Button button = new Button(upgradeText);
        button.setPrefHeight(300);
        button.setPrefWidth(200);

        button.setOnAction(e);
        upgradeButtons.add(button);
    }
}
