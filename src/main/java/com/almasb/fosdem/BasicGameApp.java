package com.almasb.fosdem;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fosdem.EntityType.*;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class BasicGameApp extends GameApplication {

    protected void initSettings(GameSettings settings) {

    }

    private Entity player;

    @Override
    protected void initInput() {
        onKey(KeyCode.A, () -> player.translateX(-5));
        onKey(KeyCode.D, () -> player.translateX(5));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BasicFactory());

        player = spawn("player", 0, 450);

        run(() -> {
            spawnFadeIn(
                    "goodStuff",
                    new SpawnData(random(0, 500), 0),
                    Duration.seconds(1)
            );

            spawnWithScale(
                    "badStuff",
                    new SpawnData(random(0, 500), 0),
                    Duration.seconds(3),
                    Interpolators.ELASTIC.EASE_OUT()
            );

        }, Duration.seconds(0.5));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(PLAYER, GOOD_STUFF, (p, stuff) -> {
            inc("score", +1);
            stuff.removeFromWorld();
        });

        onCollisionBegin(PLAYER, BAD_STUFF, (p, stuff) -> {
            inc("score", -2);
            stuff.removeFromWorld();
        });
    }

    @Override
    protected void initUI() {
        var text = getUIFactoryService().newText("", Color.BLUE, 24.0);
        text.setTranslateX(50);
        text.setTranslateY(50);
        text.textProperty().bind(getip("score").asString());

        addUINode(text);

        getip("score").addListener((o, old, newScore) -> {
            animationBuilder()
                    .interpolator(Interpolators.BOUNCE.EASE_OUT())
                    .duration(Duration.seconds(0.4))
                    .repeat(2)
                    .autoReverse(true)
                    .scale(text)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(2, 2))
                    .buildAndPlay();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
