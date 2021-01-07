package com.almasb.fosdem;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

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
    protected void initGame() {
        getGameWorld().addEntityFactory(new BasicFactory());

        player = spawn("player", 0, 450);

        run(() -> {
            spawn("goodStuff", random(0, 500), 0);
            spawn("badStuff", random(0, 500), 0);
        }, Duration.seconds(0.5));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(PLAYER, GOOD_STUFF, (p, stuff) -> {
            stuff.removeFromWorld();
        });

        onCollisionBegin(PLAYER, BAD_STUFF, (p, stuff) -> {
            stuff.removeFromWorld();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
