package org.autoBattle.fighters;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.autoBattle.battle.Level;

import java.util.Random;

public class CreateEnemies {


    private AttackAnimation attackAnimation;
    private final Random random = new Random();
    private AnchorPane scene;
    private int startHealth;
    private int startDamage;
    private Player player;
    private Level level = Level.getInstance();
    private Text levelText;

    public CreateEnemies(AttackAnimation attackAnimation, AnchorPane scene, int startHealth, int startDamage, Player player, Text levelText) {
        this.attackAnimation = attackAnimation;
        this.scene = scene;
        this.startHealth = startHealth;
        this.startDamage = startDamage;
        this.player = player;
        this.levelText = levelText;
    }

    public AttackAnimation createSquareEnemy(Boolean startFight){
        int attackTime = 400;
        int maxHealth = healthGrowth(startHealth);
        int damage = damageGrowth(startDamage);

        if((level.levelIsABossBattlePreFight())){
            Rectangle rectangle1 = new Rectangle(100, 100);
            rectangle1.toBack();
            Fighter newEnemy = new SquareEnemy(rectangle1, 425,
                    50 + random.nextInt(150), "#1e90ff", scene,maxHealth * 2, (int) (damage * 1.25));
            attackAnimation = new AttackAnimation(player, newEnemy, attackTime, 4, 2);
        } else {
            int rectangleSize = 40 + random.nextInt(20);
            Rectangle rectangle1 = new Rectangle(rectangleSize, rectangleSize);
            Fighter newEnemy = new SquareEnemy(rectangle1, 400 + random.nextInt(100),
                    50 + random.nextInt(150), "#1e90ff", scene, maxHealth, damage);
            attackAnimation = new AttackAnimation(player, newEnemy, attackTime, 4, 2);
        }
        if(startFight){
            attackAnimation.startFight();
            level.nextLevel();
            levelText.setText("Level " +level.getLevel());
        } else {
            attackAnimation.setPlayerFighting(false);
        }
        return attackAnimation;
    }

    public int damageGrowth(double startDamage){
        return (int) (2 * level.getLevel() + startDamage * Math.pow(1.02, level.getLevel()));
    }

    public int healthGrowth(double startHealth){
        return (int) (startHealth + 10 * level.getLevel()  * Math.pow(1.05, level.getLevel()));
    }

}
