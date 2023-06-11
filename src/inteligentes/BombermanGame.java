package inteligentes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    public static int _width = 0;
    public static int _height = 0;
    public static int _level = 1;

    public static final List<Entity> block = new ArrayList<>();
    public static List<Animal> enemy = new ArrayList<>();
    public static int[][] idObjects;
    public static int[][] listKill;
    public static Animal player;
    public static boolean running;

    private GraphicsContext gc;
    private Canvas canvas;

    public static Stage mainStage = null;
    public static int InitialRow = 0;
    public static int InitialColumn = 0;

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

        requestInitialAndFinalPosition(); // Solicitar posición inicial y final
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

        if (!player.isLife()) {
            restartLevel();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        block.forEach(g -> g.render(gc));
        enemy.forEach(g -> g.render(gc));
        player.render(gc);
    }

    private void restartLevel() {
        block.clear();
        enemy.clear();
        player = null;

        player = new Bomber(InitialRow, InitialColumn, Sprite.player_right_2.getFxImage());
        player.setLife(false);
        new Level1();

        running = true;
    }

    private void requestInitialAndFinalPosition() {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(mainStage);
        dialogStage.setTitle("Posiciones Iniciales y Finales");
        dialogStage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label initialRowLabel = new Label("Fila Inicial:");
        TextField initialRowField = new TextField();
        Label initialColumnLabel = new Label("Columna Inicial:");
        TextField initialColumnField = new TextField();
        Label finalRowLabel = new Label("Fila Final:");
        TextField finalRowField = new TextField();
        Label finalColumnLabel = new Label("Columna Final:");
        TextField finalColumnField = new TextField();
        Button submitButton = new Button("Guardar");

        submitButton.setOnAction(e -> {
            try {
                int initialRowPosition = Integer.parseInt(initialRowField.getText());
                int initialColumnPosition = Integer.parseInt(initialColumnField.getText());
                int finalRowPosition = Integer.parseInt(finalRowField.getText());
                int finalColumnPosition = Integer.parseInt(finalColumnField.getText());
                _width = initialRowPosition;
                _height = initialColumnPosition;
                InitialRow = initialRowPosition;
                InitialColumn = initialColumnPosition;
                player = new Bomber(InitialColumn, InitialRow, Sprite.player_right_2.getFxImage());
                player.setLife(false);
                dialogStage.close();
                System.out.println(player.getX() + " " + player.getY());
                System.out.println(InitialRow + " " + InitialColumn);
                new Level1();

                running = true;
                
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Las posiciones deben ser números enteros.");
                alert.showAndWait();
            }
        });

        grid.add(initialRowLabel, 0, 0);
        grid.add(initialRowField, 1, 0);
        grid.add(initialColumnLabel, 0, 1);
        grid.add(initialColumnField, 1, 1);
        grid.add(finalRowLabel, 0, 2);
        grid.add(finalRowField, 1, 2);
        grid.add(finalColumnLabel, 0, 3);
        grid.add(finalColumnField, 1, 3);
        grid.add(submitButton, 0, 4, 2, 1);

        Scene dialogScene = new Scene(grid, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }
}
