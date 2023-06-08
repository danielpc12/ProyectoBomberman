package inteligentes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import inteligentes.control.Menu;
import inteligentes.control.Move;
import inteligentes.entities.Entity;
import inteligentes.entities.animal.Animal;
import inteligentes.entities.animal.Bomber;
import inteligentes.entities.block.Bomb;
import inteligentes.entities.block.Portal;
import inteligentes.graphics.Sprite;
import inteligentes.levels.Level1;
import inteligentes.levels.NextLevel;

public class BombermanGame extends Application {

    /**
     * The default size of the window
     * H: 480px W: 800px
     */
    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    public static int _width = 0;
    public static int _height = 0;
    public static int _level = 1;

    public static final List<Entity> block = new ArrayList<>(); //Contains fixed entities
    public static List<Animal> enemy = new ArrayList<>();       //Contains enemy entities
    public static int[][] idObjects;    //Two-dimensional array is used to test paths
    public static int[][] listKill;     //Array containing dead positions
    public static Animal player;
    public static boolean running;

    private GraphicsContext gc;
    private Canvas canvas;

    private int frame = 1;
    private long lastTime;

    public static Stage mainStage = null;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas.setTranslateY(32);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        ChoiceBox<String> searchAlgorithms = new ChoiceBox<>();
        searchAlgorithms.getItems().addAll("A*", "BFS", "DFS", "Uniform Cost");
        searchAlgorithms.setValue("A*");

        searchAlgorithms.setOnAction(event -> {
            String selectedAlgorithm = searchAlgorithms.getValue();
            switch (selectedAlgorithm) {
                case "A*":
                    
                    break;
                case "BFS":
                    
                    break;
                case "DFS":
                    
                    break;
                case "Uniform Cost":
                    
                    break;
                default:
                    break;
            }
        });

        root.getChildren().add(searchAlgorithms);

        scene.setOnKeyPressed(event -> {
            if (player.isLife())
                switch (event.getCode()) {
                    case UP:
                        Move.up(player);
                        break;
                    case DOWN:
                        Move.down(player);
                        break;
                    case LEFT:
                        Move.left(player);
                        break;
                    case RIGHT:
                        Move.right(player);
                        break;
                    case SPACE:
                        Bomb.putBomb();
                        break;
                }
        });

        stage.setScene(scene);
        mainStage = stage;
        mainStage.show();

        lastTime = System.currentTimeMillis();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    render();
                    update();
                }
            }
        };
        timer.start();

        player = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
        player.setLife(false);

        new Level1();

        running = true;
    }

    public void update() {
        block.forEach(Entity::update);
        enemy.forEach(Entity::update);
        player.update();

        player.setCountToRun(player.getCountToRun() + 1);
        if (player.getCountToRun() == 4) {
            Move.checkRun(player);
            player.setCountToRun(0);
        }

        for (Animal a : enemy) {
            a.setCountToRun(a.getCountToRun() + 1);
            if (a.getCountToRun() == 8) {
                Move.checkRun(a);
                a.setCountToRun(0);
            }
        }

        if (enemy.size() == 0 && !Portal.isPortal && !NextLevel.wait) {
            Entity portal = new Portal(_width - 2, _height - 2, Sprite.portal.getFxImage());
            block.add(portal);
            if (player.getX() / 32 == portal.getX() / 32 && player.getY() / 32 == portal.getY() / 32) {
                NextLevel.wait = true;
                NextLevel.waitingTime = System.currentTimeMillis();
            }
        }
        NextLevel.waitToLevelUp();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        block.forEach(g -> g.render(gc));
        enemy.forEach(g -> g.render(gc));
        player.render(gc);
    }
}
