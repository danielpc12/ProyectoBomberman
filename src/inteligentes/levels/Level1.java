package inteligentes.levels;

import static inteligentes.BombermanGame.*;
import static inteligentes.control.Menu.timeNumber;
import static inteligentes.entities.animal.Bomber.swapKill;
import static inteligentes.entities.block.Bomb.isBomb;
import static inteligentes.entities.block.Bomb.powerBomb;
import static inteligentes.entities.item.SpeedItem.speed;

import inteligentes.entities.animal.Animal;
import inteligentes.entities.animal.Ballom;
import inteligentes.entities.animal.Oneal;
import inteligentes.graphics.CreateMap;
import inteligentes.graphics.Sprite;
import javafx.scene.image.Image;

public class Level1 {
    public Level1() {
        enemy.clear();
        block.clear();
        swapKill = 1;
        powerBomb = 0;
        new CreateMap("res/levels/Level1.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        timeNumber = 120;
        isBomb = 0;
        speed = 1;

        player.setImg(Sprite.player_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");

        Animal enemy1 = new Ballom(4, 4, Sprite.ballom_left1.getFxImage());
        Animal enemy2 = new Ballom(9, 9, Sprite.ballom_left1.getFxImage());
        Animal enemy3 = new Ballom(22, 6, Sprite.ballom_left1.getFxImage());
        enemy.add(enemy1);
        enemy.add(enemy2);
        enemy.add(enemy3);

        Animal enemy4 = new Oneal(7, 6, Sprite.oneal_right1.getFxImage());
        Animal enemy5 = new Oneal(13, 8, Sprite.oneal_right1.getFxImage());
        enemy.add(enemy4);
        enemy.add(enemy5);

        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}
