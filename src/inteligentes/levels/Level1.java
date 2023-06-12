package inteligentes.levels;

import static inteligentes.BombermanGame.*;
import static inteligentes.entities.animal.Bomber.swapKill;
import static inteligentes.entities.block.Bomb.isBomb;
import static inteligentes.entities.block.Bomb.powerBomb;
import static inteligentes.entities.item.SpeedItem.speed;

import inteligentes.entities.animal.Animal;
import inteligentes.entities.animal.Ballom;
import inteligentes.entities.animal.Oneal;
import inteligentes.graphics.CreateMap;
import inteligentes.graphics.Sprite;

public class Level1 {
    public Level1() {
        enemy.clear();
        block.clear();
        swapKill = 1;
        powerBomb = 0;
        new CreateMap("res/levels/Level1.txt");
        player.setLife(true);
        isBomb = 0;
        speed = 1;

        player.setImg(Sprite.player_right_2.getFxImage());



        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}
