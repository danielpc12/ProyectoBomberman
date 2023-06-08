package inteligentes.entities.block;

import static inteligentes.BombermanGame.block;
import static inteligentes.BombermanGame.listKill;

import inteligentes.entities.Entity;
import inteligentes.graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends Entity {

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    private void checkHidden() {
        for (Entity entity : block) {
            if (entity instanceof Brick)
                if (listKill[entity.getX() / 32][entity.getY() / 32] == 4) {
                    entity.setImg(Sprite.grass.getFxImage());
                }
        }
    }

    @Override
    public void update() {
        checkHidden();
    }
}