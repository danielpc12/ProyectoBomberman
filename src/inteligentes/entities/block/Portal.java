package inteligentes.entities.block;

import inteligentes.entities.Entity;
import javafx.scene.image.Image;

public class Portal extends Entity {
    public static boolean isPortal = false;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}