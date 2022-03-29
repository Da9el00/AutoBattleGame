package org.autoBattle.battle;

public class Level {

    private int level = 0;

    private static final Level instance = new Level();

    private Level() {
    }

    public static Level getInstance(){
        return instance;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void nextLevel(){
        level++;
    }

    public boolean levelIsABossBattle(){
        return level % 5 == 0 && level > 1;
    }

    public boolean levelIsABossBattlePreFight(){
        return (level + 1) % 5 == 0 && level > 1;
    }
}
