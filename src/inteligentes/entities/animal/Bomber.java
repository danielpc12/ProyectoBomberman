package inteligentes.entities.animal;

import static inteligentes.BombermanGame.*;

import java.util.List;

import inteligentes.control.Move;
import inteligentes.entities.animal.intelligent.AStar;
import inteligentes.entities.animal.intelligent.BFS;
import inteligentes.entities.animal.intelligent.DFS;
import inteligentes.entities.animal.intelligent.Node;
import inteligentes.graphics.Sprite;
import javafx.scene.image.Image;

public class Bomber extends Animal {
    public static int swapKill = 1;
    private static int countKill = 0;
    private String algorithm;

    public Bomber(int isMove, int swap, String direction, int count, int countToRun, String algorithm) {
        super(8, 1, direction, 0, 0);
        this.algorithm = algorithm;
    }

    public Bomber() {
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }


    private void killBomber(Animal animal) {
        if (countKill % 16 == 0) {
            if (swapKill == 1) {
                animal.setImg(Sprite.player_dead1.getFxImage());
                swapKill = 2;
            } else if (swapKill == 2) {
                animal.setImg(Sprite.player_dead2.getFxImage());
                swapKill = 3;
            } else if (swapKill == 3) {
                animal.setImg(Sprite.player_dead3.getFxImage());
                swapKill = 4;
            } else {
                animal.setImg(Sprite.transparent.getFxImage());
                running = false;
            }
        }
    }

    private void checkBombs() {
        if (listKill[player.getX() / 32][player.getY() / 32] == 4)
            player.setLife(false);
    }

    private void checkEnemy() {
        int ax = player.getX() / 32;
        int ay = player.getY() / 32;
        for (Animal animal : enemy) {
            int bx = animal.getX() / 32;
            int by = animal.getY() / 32;
            if (ax == bx && ay == by
                    || ax == bx && ay == by + 1 || ax == bx && ay == by - 1
                    || ay == by && ax == bx + 1 || ay == by && ax == bx - 1) {
                player.life = false;
                break;
            }
        }
    }

    private void checkEnemy2() {    //easy level
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy)
            if (ax == animal.getX() && ay == animal.getY()
                    || ax == animal.getX() && ay == animal.getY() - 32
                    || ax == animal.getX() && ay == animal.getY() + 32
                    || ay == animal.getY() && ax == animal.getX() - 32
                    || ay == animal.getY() && ax == animal.getX() + 32) {
                player.life = false;
                break;
            }
    }

    private void checkEnemy3() {
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy) {
            int bx = animal.getX();
            int by = animal.getY();
            if (ax == bx && by - 32 <= ay && by + 32 >= ay
                    || ay == by && bx - 32 <= ax && bx + 32 >= ax) {
                player.life = false;
                break;
            }
        }
    }

    private void move() {
        switch (selectedAlgorithm) {
            case "A*":
                moveAStar();
                break;
            case "DFS":
                moveDFS();
                break;
             case "BFS":
                moveBFS();
                break;
            // case "Uniform Cost":
            //     moveUniformCost();
            //     break;
            // case "Hill Climbing":
            //     moveHillClimbing();
            //     break;
            // case "Beam Search":
            //     moveBeamSearch();
            //     break;
            // default:
            //     moveAStar();
            //     break;
        }
        
    }

    public void moveAStar(){
        if (this.y % 32 == 0 && this.x % 32 == 0) {
            Node initialNode = new Node(player.getY() / 32, player.getX() / 32);
            Node finalNode = new Node(FinalRow, FinalColumn);

            int rows = _height;
            int cols = _width;
            System.out.println(selectedAlgorithm);

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

    public void moveDFS() {
        if (this.y % 32 == 0 && this.x % 32 == 0) {
            Node initialNode = new Node(player.getY() / 32, player.getX() / 32);
            Node finalNode = new Node(FinalRow, FinalColumn);

            int rows = _height;
            int cols = _width;
            System.out.println(selectedAlgorithm);

            DFS dfs = new DFS(rows, cols, initialNode, finalNode);

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

            dfs.setBlocks(blocksArray, countBlock);
            List<Node> path = dfs.findPath();
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

public void moveBFS() {
    if (this.y % 32 == 0 && this.x % 32 == 0) {
        Node initialNode = new Node(player.getY() / 32, player.getX() / 32);
        Node finalNode = new Node(FinalRow, FinalColumn);

        int rows = _height;
        int cols = _width;

        BFS bfs = new BFS(rows, cols, initialNode, finalNode);

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

        bfs.setBlocks(blocksArray, countBlock);
        List<Node> path = bfs.findPath();
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
        checkBombs();
        checkEnemy3();
        countKill++;
        if (!player.life)
            killBomber(player);
        move();
    }

}
