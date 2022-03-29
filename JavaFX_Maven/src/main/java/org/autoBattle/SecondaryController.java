package org.autoBattle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.autoBattle.battle.Level;
import org.autoBattle.fighters.AttackAnimation;
import org.autoBattle.fighters.CreateEnemies;
import org.autoBattle.fighters.Player;


public class SecondaryController implements Initializable {

    private AttackAnimation attackAnimation = null;
    private CreateEnemies createEnemies;

    private Player player;
    private final Level level = Level.getInstance();
    private int startEnemyHealth = 400;
    private int startEnemyDamage = 100;


    //Scene
    @FXML
    private AnchorPane scene;
    @FXML
    private Text levelText;

    //Buttons
    @FXML
    private Button stop;
    @FXML
    private Button resetHealth;
    @FXML
    private Button fightNewEnemy;
    @FXML
    private Button goCamp;
    @FXML
    private Button restart;

    //Button activations

    @FXML
    public void fight(ActionEvent event){
        if(event.getSource() == stop){
            attackAnimation.stopFight();
            attackAnimation.setPlayerFighting(false);
        } else if(event.getSource() == fightNewEnemy){
            if(!attackAnimation.isPlayerFighting() && !attackAnimation.isEnemyIsAlive()) {
                attackAnimation = createEnemies.createSquareEnemy(true);
            } else if(!attackAnimation.isPlayerFighting() && attackAnimation.isEnemyIsAlive()) {
                attackAnimation.startFight();
            }
            camp.setVisible(false);
            goCamp.setDisable(true);
        } else if (event.getSource() == goCamp){
            if(camp.isVisible()){
                camp.setVisible(false);
            } else{
                statText.setText(player.getStats());
                camp.setVisible(true);
                camp.toFront();
            }
        } else if(event.getSource() == restart){
            player.deletePLayer();
            attackAnimation.deleteEnemy();
            startGame();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startGame();

        stop.setFocusTraversable(false);
        resetHealth.setFocusTraversable(false);
        fightNewEnemy.setFocusTraversable(false);
        goCamp.setFocusTraversable(false);
        restart.setFocusTraversable(false);
    }

    //Camp
    @FXML
    private AnchorPane camp;
    @FXML
    private Text statText;

    @FXML
    void campActions(ActionEvent event) {
        if(event.getSource() == resetHealth && player.getGold() >= 5) {
            player.changeGoldAmount(-5);
            player.resetHealth();
        }
    }

    public void startGame(){
        level.setLevel(0);
        Rectangle playerRectangle = new Rectangle(50, 50);
        this.player = new Player(playerRectangle, 50, 150
                , "#1e90ff", scene,1000,200);
        this.player.updateGoldAmount();
        levelText.setText("Level " + level.getLevel());
        createEnemies = new CreateEnemies(attackAnimation,scene,startEnemyHealth,startEnemyDamage,player,levelText);
        attackAnimation = createEnemies.createSquareEnemy(false);
    }
}