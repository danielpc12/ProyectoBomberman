package inteligentes.entities.animal;

import inteligentes.entities.animal.intelligent.AStar;
import inteligentes.entities.animal.intelligent.Node;
import inteligentes.control.Move;
import inteligentes.graphics.Sprite;
import javafx.scene.image.Image;
import static inteligentes.BombermanGame.*;

import java.util.List;

public class Kondoria extends Animal {

    private static int swapKill = 1;
    private static int countKill = 0;
    private static boolean dir;

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
    }

    public Kondoria(boolean life) {
        super(life);
    }

    private void killKondoria(Animal animal) {
        if (countKill % 16 == 0) {
            if (swapKill == 1) {
                animal.setImg(Sprite.kondoria_dead.getFxImage());
                swapKill = 2;
            } else if (swapKill == 2) {
                animal.setImg(Sprite.player_dead3.getFxImage());
                swapKill = 3;
            } else {
                animal.setLife(false);
                enemy.remove(animal);
                swapKill = 1;
            }
        }
    }

    private void move() {
        if (this.y % 32 == 0 && this.x % 32 == 0) {
            Node initialNode = new Node(this.y / 32, this.x / 32);
            Node finalNode = new Node(player.getY() / 32, player.getX() / 32);

            int rows = _height;
            int cols = _width;

            AStar aStar = new AStar(rows, cols, initialNode, finalNode);

            int[][] blocksArray = new int[_width * _height][2];
            int countBlock = 0;

            for (int i = 0; i < _height; i++) {
                for (int j = 0; j < _width; j++) {
                    if (idObjects[j][i] != 0) {
                        blocksArray[countBlock][0] = i;
                        blocksArray[countBlock][1] = j;
                        countBlock++;
                    }
                }
            }

            aStar.setBlocks(blocksArray, countBlock);
            List<Node> path = aStar.findPath();
            if (path.size() > 1) {
                int nextY = path.get(1).getRow();
                int nextX = path.get(1).getCol();

                if (this.y / 32 > nextY)
                    Move.up(this);
                if (this.y / 32 < nextY)
                    Move.down(this);
                if (this.x / 32 > nextX)
                    Move.left(this);
                if (this.x / 32 < nextX)
                    Move.right(this);
            }
        }
    }

    @Override
    public void update() {
        countKill++;
        for (Animal animal : enemy) {
            if (animal instanceof Kondoria && !animal.life)
                killKondoria(animal);
        }
        move();
    }
}
