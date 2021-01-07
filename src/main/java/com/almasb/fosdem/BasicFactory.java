package com.almasb.fosdem;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class BasicFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox(new Rectangle(40, 40, Color.BLUE))
                .collidable()
                .build();
    }

    @Spawns("goodStuff")
    public Entity newGoodStuff(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.GOOD_STUFF)
                .viewWithBBox(new Rectangle(20, 20, Color.GREEN))
                .collidable()
                .with(new ProjectileComponent(new Point2D(0, 1), 400))
                .build();
    }

    @Spawns("badStuff")
    public Entity newBadStuff(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.BAD_STUFF)
                .viewWithBBox(new Rectangle(20, 20, Color.RED))
                .with(new ProjectileComponent(new Point2D(0, 1), 150))
                .collidable()
                .build();
    }
}
