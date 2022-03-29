package org.autoBattle.fighters;

import javafx.animation.PauseTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SquareEnemy extends Fighter {


    Rectangle rectangle;
    private final AnchorPane scene;

    public SquareEnemy(Rectangle rectangle, double xPos, double yPos, String color, AnchorPane scene, int maxHealth, int damage) {
        super(rectangle,maxHealth,damage);
        this.scene = scene;
        this.rectangle = rectangle;
        rectangle.setLayoutX(xPos);
        rectangle.setLayoutY(yPos);
        rectangle.setFill(Paint.valueOf(color));

        getHealthBar().setPrefWidth(rectangle.getWidth() * 2);
        getHealthBar().setPrefHeight(20);
        getHealthBar().setLayoutX(rectangle.getLayoutX() - (0.5 * rectangle.getWidth()));
        getHealthBar().setLayoutY(rectangle.getLayoutY() - 30);

        updateHealth();

        getHealthText().setLayoutX(rectangle.getLayoutX() + (0.5 * rectangle.getWidth()) - (0.5 * getHealthText().getLayoutBounds().getWidth()));
        getHealthText().setLayoutY(rectangle.getLayoutY() - 35);

        scene.getChildren().add(6,getHealthText());
        scene.getChildren().add(5,rectangle);
        scene.getChildren().add(6,getHealthBar());

    }

    public AnchorPane getScene() {
        return scene;
    }

    @Override
    public int takeDamage(int damage, Damage damageObject) {
        lowerCurrentHealth(damage);
        Text damageNumber = new Text();
        int textSize = 14;
        if(damageObject.isNewHitIsCrit()){
            textSize = 30;
            damageObject.critDamageDone();
        }
        damageNumber.setStyle("-fx-fill: #550a12; -fx-font-size: " + textSize + "pt;");
        damageNumber.setText(Integer.toString(damage));
        damageNumber.setLayoutX(rectangle.getLayoutX() - (0.20 * rectangle.getWidth()) + random.nextInt((int) (0.3 * rectangle.getWidth())));
        damageNumber.setLayoutY(rectangle.getLayoutY() + (0.3 * rectangle.getHeight()) + random.nextInt((int) (0.3 * rectangle.getWidth())));
        scene.getChildren().add(damageNumber);

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(150));
        pauseTransition.setOnFinished(e->{
            scene.getChildren().remove(damageNumber);
        });

        pauseTransition.play();
        return damage;
    }

    public void removeSquareEnemy(){
        scene.getChildren().removeAll(rectangle,getHealthBar(),getHealthText());
    }

    @Override
    public void updateHealth(){
        getHealthBar().setProgress((double) getCurrentHealth()/getMaxHealth());
        getHealthText().setText(getCurrentHealth() + " / " + getMaxHealth());
    }

}
