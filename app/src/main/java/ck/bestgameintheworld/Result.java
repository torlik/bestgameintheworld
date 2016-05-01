package ck.bestgameintheworld;
/**
 * Created by Torlik on 2016.04.18..
 */
public class Result {
    public int getScore() {
        return score;
    }

    int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public Result(String name, int score) {
        this.score = score;
        this.name = name;
    }
}
