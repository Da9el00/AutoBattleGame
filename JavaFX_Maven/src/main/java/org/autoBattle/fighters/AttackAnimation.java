package org.autoBattle.fighters;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.autoBattle.battle.Upgrades;

import java.io.File;

public class AttackAnimation {

    private final Player player;
    private final Fighter enemy;

    private final TranslateTransition transitionPlayer;
    private final TranslateTransition transitionEnemy;
    private final int attackTime;
    private final int attackTimeBetween;
    private final int timeBetweenFights;
    private Timeline timeline;
    private boolean playerFighting;
    private boolean enemyIsAlive;
    private MediaPlayer mediaPlayer;
    PauseTransition playerCanAttack;
    PauseTransition playerTakeDamageTransition;
    PauseTransition enemyTakeDamageTransition;
    Upgrades upgrades;


    public AttackAnimation(Player player, Fighter enemy, int attackTime, int positionPlayer, int positionEnemy) {
        this.player = player;
        this.enemy = enemy;
        this.attackTime = attackTime;
        playerFighting = false;
        enemyIsAlive = true;

        attackTimeBetween = (int) (1.5 * attackTime);
        timeBetweenFights = 3 * attackTime;

        transitionPlayer = animation(player.getRectangle(), enemy.getRectangle(),positionPlayer);
        transitionEnemy = animation(enemy.getRectangle(), player.getRectangle(),positionEnemy);

        upgrades = new Upgrades(player, player.getScene());

        playerTakeDamageTransition = new PauseTransition(Duration.millis(2.5 * attackTime));
        playerTakeDamageTransition.setOnFinished(e -> {
            takeDamagePlayer();
            //playHitSound("sound.mp3");
            if(player.isDead()) {
                System.out.println("Game lost!!!!");
                playerFighting = false;
                timeline.stop();
                player.removeSquareEnemy();
            }
        });

        playerCanAttack = new PauseTransition(Duration.millis(attackTime));
        playerCanAttack.setOnFinished(e ->{
            enemyIsAlive = false;
            playerFighting = false;
            upgrades.showUpgrades();
        });

        enemyTakeDamageTransition = new PauseTransition(Duration.millis(attackTime));
        enemyTakeDamageTransition.setOnFinished(e -> {
            takeDamageEnemy();
            //playHitSound("sound.mp3");
            enemy.updateHealth();
            checkIfEnemyIsDead();
        });
    }

    private int getPositionPlacement(int position){
        if(position == 4){
            return (int) (-1 * player.getRectangle().getWidth());
        } else if (position == 2){
            return (int) player.getRectangle().getWidth();
        }
        return 0;
    }

    private int takeDamagePlayer(){
        return player.takeDamage(enemy.getDamage(), enemy.getDamageObject());
    }

    private int takeDamageEnemy(){
        return enemy.takeDamage(player.getPlayerDamage(),player.getDamageObject());
    }

    private void attack(){
        Platform.runLater(() -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(attackTimeBetween));
            pauseTransition.setOnFinished(e -> transitionEnemy.play());
            transitionPlayer.play();
            pauseTransition.play();
        });
    }

    private TranslateTransition animation(Rectangle player, Rectangle enemy, int enemyPosition){
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(player);
        transition.setToX(enemy.getLayoutX()-player.getLayoutX() + (getPositionPlacement(enemyPosition)));
        transition.setToY(enemy.getLayoutY()-player.getLayoutY() + (0.5 * (enemy.getHeight() - player.getHeight())));
        transition.setDuration(Duration.millis(attackTime));
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        return transition;
    }

    public void startFight(){
        playerFighting = true;

        player.triggerAtStartOfFight();

        //First attack
        enemyTakeDamageTransition.play();
        playerTakeDamageTransition.play();
        attack();

        //Attack until someone is dead
        timeline = new Timeline(new KeyFrame(Duration.millis(timeBetweenFights), e->{
            playerTakeDamageTransition.play();
            enemyTakeDamageTransition.play();
            attack();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stopFight(){
        timeline.stop();
    }

    public void setPlayerFighting(boolean playerFighting) {
        this.playerFighting = playerFighting;
    }

    public boolean isPlayerFighting(){
        return playerFighting;
    }

    public boolean isEnemyIsAlive() {
        return enemyIsAlive;
    }


    private void playHitSound(String fileName){
        String path = getClass().getResource("sound/" + fileName).getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public Fighter getEnemy() {
        return enemy;
    }

    public void checkIfEnemyIsDead(){
        if(enemy.isDead()){
            deleteEnemy();
            player.triggerAfterKillingEnemy();
        }
    }

    public void deleteEnemy(){
        if(playerFighting){
            playerTakeDamageTransition.stop();
            timeline.stop();
            playerCanAttack.play();
        }
        enemy.removeSquareEnemy();
    }
}
