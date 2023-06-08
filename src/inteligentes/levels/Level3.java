package inteligentes.levels;

import static inteligentes.BombermanGame.*;
import static inteligentes.control.Menu.timeNumber;
import static inteligentes.entities.animal.Bomber.swapKill;
import static inteligentes.entities.block.Bomb.isBomb;
import static inteligentes.entities.item.SpeedItem.speed;

import inteligentes.entities.animal.Animal;
import inteligentes.entities.animal.Ballom;
import inteligentes.entities.animal.Doll;
import inteligentes.graphics.CreateMap;
import inteligentes.graphics.Sprite;
import javafx.scene.image.Image;

public class Level3 {
    public Level3() {
        enemy.clear();
        block.clear();
        swapKill = 1;
        new CreateMap("res/levels/Level3.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        speed = 1;
        timeNumber = 120;
        isBomb = 0;

        player.setImg(Sprite.player_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");

        Animal enemy1 = new Ballom(5, 5, Sprite.ballom_left1.getFxImage());
        Animal enemy2 = new Ballom(11, 9, Sprite.ballom_left1.getFxImage());
        enemy.add(enemy1);
        enemy.add(enemy2);

        Animal enemy3 = new Doll(7, 5, Sprite.doll_left1.getFxImage());
        enemy.add(enemy3);

        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}
