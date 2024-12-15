package com.unipi.alexandris.game.echotrials.base;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import com.unipi.alexandris.game.echotrials.base.core.GameLevel;
import com.unipi.alexandris.game.echotrials.base.core.Camera;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.gui.*;
import com.unipi.alexandris.game.echotrials.base.gui.Window;
import com.unipi.alexandris.game.echotrials.base.handlers.Handler;
import com.unipi.alexandris.game.echotrials.base.loaders.BufferedImageLoader;
import com.unipi.alexandris.game.echotrials.base.loaders.LevelLoader;
import com.unipi.alexandris.game.echotrials.base.core.UserFiles;
import com.unipi.alexandris.game.echotrials.base.roomobjects.Player;
import com.unipi.alexandris.game.echotrials.base.roomobjects.PortalBlock;
import com.unipi.alexandris.game.echotrials.base.sensors.KeyInput;
import com.unipi.alexandris.game.echotrials.base.sensors.MouseInput;
import com.unipi.alexandris.game.echotrials.base.sensors.MouseMotionSensor;
import javafx.application.Platform;

import javax.swing.*;

/**
 * The main Game class that manages the core game functionality.
 * This class extends Canvas and implements Runnable to handle the game loop and rendering.
 * It manages game states, resources, input handling, and level progression.
 *
 * Features include:
 * <ul>
 *   <li>Game loop management</li>
 *   <li>Resource loading and management</li>
 *   <li>Level loading and progression</li>
 *   <li>Input handling (keyboard and mouse)</li>
 *   <li>User data persistence</li>
 *   <li>Speedrun mode</li>
 *   <li>High score tracking</li>
 * </ul>
 */
@SuppressWarnings(value = "unused")
public class Game extends Canvas implements Runnable {

    /** Serialization version UID for the Game class. */
    @Serial
    private static final long serialVersionUID = 5839527675529025230L;

    /** User data object containing progress, unlocked levels, and high scores. */
    private final UserFiles user;

    /** Reference to the currently active game instance. */
    private static Game active;

    /** List of levels that have been unlocked by the player. */
    public static final ArrayList<String> unlockedLevels = new ArrayList<>();

    /** List of GUI button elements in the game. */
    private final ArrayList<GUIButton> guiElements = new ArrayList<>();

    /** Utility for loading image resources. */
    private static final BufferedImageLoader imageLoader = new BufferedImageLoader();

    /**
     * Record containing all game images and textures.
     * Includes loading screens, portal animations, GUI elements, and game object textures.
     */
    public record Images(
            BufferedImage loadingScreen,
            BufferedImage[] portalImages,
            BufferedImage backButton,
            BufferedImage highScoreButton,
            BufferedImage heart,
            BufferedImage bubble,
            BufferedImage brickImage,
            BufferedImage iceImage,
            BufferedImage waterImage,
            BufferedImage backgroundImage,
            BufferedImage[] spikeBlockImages,
            BufferedImage[] trapBlockImages,
            BufferedImage[] movingBlockImages,
            BufferedImage[] buttonBlockImages
    ) {}

    /** Static collection of all game images loaded at startup. */
    public static final Images gameImages = new Images(
            imageLoader.loadImage("/textures/loading_screen1080p.png"),
            new BufferedImage[] {
                imageLoader.loadImage("/textures/portal4.png"),
                imageLoader.loadImage("/textures/portal4Locked.png")
            },
            imageLoader.loadImage("/textures/back.png"),
            imageLoader.loadImage("/textures/highscore.png"),
            imageLoader.loadImage("/textures/hearts4.png"),
            imageLoader.loadImage("/textures/bubble.png"),
            imageLoader.loadImage("/textures/brick5.png"),
            imageLoader.loadImage("/textures/ice2.png"),
            imageLoader.loadImage("/textures/water3.png"),
            imageLoader.loadImage("/textures/bgrnd5.png"),
            new BufferedImage[] {
                imageLoader.loadImage("/textures/spikes3.png"),
                imageLoader.loadImage("/textures/ice_spikes1.png")
            },
            new BufferedImage[] {
                imageLoader.loadImage("/textures/brick5.png"),
                imageLoader.loadImage("/textures/brick5.png")
            },
            new BufferedImage[] {
                imageLoader.loadImage("/textures/brick5.png")
            },
            new BufferedImage[] {
                imageLoader.loadImage("/textures/button1.png"),
                imageLoader.loadImage("/textures/button_pressed1.png")
            }
    );

    /** Current X coordinate of the mouse cursor. */
    public static int cursorX;

    /** Current Y coordinate of the mouse cursor. */
    public static int cursorY;

    /** Collision area for solid blocks. */
    public static Area block = new Area();

    /** Collision area for ice blocks. */
    public static Area ice = new Area();

    /** Collision area for water blocks. */
    public static Area water = new Area();

    /** Game window width in pixels. */
    public static final int WIDTH = 1920;

    /** Game window height in pixels. */
    public static final int HEIGHT = 1080;

    /** The game window frame. */
    public JFrame frame;

    /** The game window wrapper. */
    public Window window;

    /** List of hazard locations in the current level. */
    public static ArrayList<Integer> hazardsMap = new ArrayList<>();

    /** Initial player X coordinate. */
    public static int i_p_x;

    /** Initial player Y coordinate. */
    public static int i_p_y;

    /** Number of bubble power-ups available. */
    public static int bubble_counter = 10;

    /** Player's current health points. */
    public static int health_counter = 3;

    /** Flag indicating if bubble shield is active. */
    public static boolean bubble_flag;

    /** Reference to the player object. */
    public static Player player;

    /** Reference to the level's goal portal. */
    public static PortalBlock goal;

    /** Camera controller for the game view. */
    public static Camera camera;

    /** Size multiplier for game objects. */
    public static final int multiplier = 48;

    /** Flag indicating if the game is currently running. */
    private boolean running = false;

    /** Handler for game objects and events. */
    private static final Handler handler = new Handler();

    /** ID of the current level being played. */
    public static LevelID currentLevel;

    /** Flag indicating if speedrun mode is active. */
    public static boolean SPEEDRUN = false;

    /** Flag for debug mode. */
    public static final boolean DEBUG = false;

    /** Title displayed during speedrun mode. */
    private static String speedrunTitle = "SPEEDRUN";

    /** Seconds counter for speedrun timing. */
    private static int SECONDS = 0;

    /** Milliseconds counter for speedrun timing. */
    private static int TIME = 0;

    /** Current frames per second. */
    private long fps = 0L;

    /**
     * Constructs a new Game instance.
     * Initializes the game with user data, sets up input handlers, and creates the game window.
     *
     * @param user The user data object containing game progress and settings
     */
    public Game(UserFiles user) {
        this.user = user;
        unlockedLevels.clear();
        unlockedLevels.addAll(user.unlockedLevels());
        loadLevel(LevelID.LEVEL_SELECTOR);
        this.addKeyListener(new KeyInput(this, handler));
        this.addMouseListener(new MouseInput(this, handler));
        this.addMouseMotionListener(new MouseMotionSensor(handler));
        window = new Window(WIDTH, HEIGHT, "ECHO TRIALS!", this);
        if(active != null) {
            active.stop();
        }
        active = this;
        guiElements.add(new BackButton(64, 64, ID.Button, gameImages.backButton));
        guiElements.add(new HighScoreButton(160, 64, ID.Button, gameImages.highScoreButton));
    }

    /**
     * Restarts the game to its initial state.
     * Resets health, speedrun mode, and returns to the level selector.
     */
    public static void restart() {
        health_counter = 3;
        SPEEDRUN = false;
        SECONDS = 0;
        TIME = 0;
        loadLevel(LevelID.LEVEL_SELECTOR);
    }

    /**
     * Reloads the current level.
     * Clears all objects and reloads the level from scratch.
     */
    public static void reload() {
        handler.clear();
        player = null;
        LevelLoader level = new LevelLoader(handler);
        GameLevel gameLevel = level.load(currentLevel);
        camera = new Camera(0.0, 0.0, gameLevel.mapWidth(), gameLevel.mapHeight(), 48);
    }

    /**
     * Loads a specific level by its ID.
     * Handles level initialization and camera setup.
     *
     * @param levelID The ID of the level to load
     */
    public static void loadLevel(LevelID levelID) {
        if(levelID == LevelID.LEVEL_SELECTOR) {
            health_counter = 3;
            SPEEDRUN = false;
            SECONDS = 0;
            TIME = 0;
        }
        handler.clear();
        if(!SPEEDRUN) health_counter = 3;
        player = null;
        LevelLoader level = new LevelLoader(handler);
        GameLevel gameLevel = level.load(levelID);
        camera = new Camera(0.0, 0.0, gameLevel.mapWidth(), gameLevel.mapHeight(), 48);
        currentLevel = levelID;
    }

    /**
     * Loads the next level in sequence.
     * Handles level progression, unlocking new levels, and speedrun mode transitions.
     */
    public static void loadNext() {
        List<String> groups = List.of(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"});
        List<String> latinNum = List.of(new String[]{"I", "II", "III", "IV", "V"});
        if(SPEEDRUN && currentLevel.part.equals("V")) {
            HashMap<LevelID, Double> highScore = active.user.highScore();
            LevelID level = currentLevel;
            Double score = Double.parseDouble(SECONDS+"."+TIME);
            if(highScore.containsKey(level) && highScore.get(level) > score) highScore.replace(level, score);
            else if(!highScore.containsKey(level)) highScore.put(level, score);
            active.save();
        }
        if(currentLevel.part.equals("V")) {
            if(currentLevel.group.equals("E")) {
                if(!SPEEDRUN) {
                    active.user.unlockedLevels().add("SPEEDRUN");
                    unlockedLevels.add("SPEEDRUN");
                    active.save();
                }
            }
            else {
                int currentGroup = groups.indexOf(currentLevel.group);
                if(SPEEDRUN) {
                    String levelName = "LEVEL_" + groups.get(++currentGroup) + "_I";
                    loadLevel(LevelID.getByName(levelName));
                    return;
                }
                active.user.unlockedLevels().add(groups.get(++currentGroup));
                unlockedLevels.add(groups.get(currentGroup));
                active.save();
            }
            loadLevel(LevelID.LEVEL_SELECTOR);
            return;
        }
        handler.clear();
        if(!SPEEDRUN) health_counter = 3;
        player = null;
        LevelLoader level = new LevelLoader(handler);
        int currentNum = latinNum.indexOf(currentLevel.part);
        String levelName = "LEVEL_" + currentLevel.group + "_" + latinNum.get(++currentNum);
        GameLevel gameLevel = level.load(LevelID.getByName(levelName));
        camera = new Camera(0.0, 0.0, gameLevel.mapWidth(), gameLevel.mapHeight(), 48);
        currentLevel = gameLevel.levelID();
    }

    /**
     * Retrieves the high scores for all levels.
     *
     * @return A HashMap containing level names and their corresponding high scores in MM:SS:MS format
     */
    public static HashMap<String, String> getHighScores() {
        HashMap<String, String> scores = new HashMap<>();
        for(LevelID levelID : LevelID.values()) {
            if(active.user.highScore().containsKey(levelID)) {
                int minutes;
                int seconds = active.user.highScore().get(levelID).intValue();
                int millis = Integer.parseInt((round(active.user.highScore().get(levelID) - seconds) + "").substring(2));

                minutes = seconds / 60;
                seconds = seconds % 60;

                scores.put("LEVEL_"+levelID.group, minutes+":"+seconds+":"+millis);

                System.out.println(levelID.name() + " : " + active.user.highScore().get(levelID));
            }
        }
        return scores;
    }

    /**
     * Rounds a double value to two decimal places.
     *
     * @param number The number to round
     * @return The rounded value
     */
    private static double round(double number) {
        // Creating an object of DecimalFormat class
        DecimalFormat df_obj = new DecimalFormat("#.##");

        return Double.parseDouble(df_obj.format(number));
    }

    /**
     * Starts the game thread.
     */
    public synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
        this.running = true;
    }

    /**
     * Exits the game safely.
     */
    public static void exit() {
        assert active != null;
        active.stop();
    }

    /**
     * Stops the game thread and saves user data before exiting.
     */
    public synchronized void stop() {
        if(save()) {
            this.running = false;
            Game.active = null;
            System.exit(0);
        }
        else {
            System.out.println("Canceled game termination due to an error during user data saving.");
        }
    }

    /**
     * Saves the current user data to file.
     *
     * @return true if save was successful, false otherwise
     */
    private boolean save() {
        String filename = "saves/"+user.uuid()+".data";
        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(user);

            out.close();
            file.close();

            System.out.println(user.username() + "' save data was updated successfully!");
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("[SEVERE]: User's save data update has failed!");
            return false;
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 100.0;
        double ns = 1.0E9 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        long frames = 0L;

        while(this.running) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / ns;

            for(lastTime = now; delta >= 1.0; --delta) {
                this.tick();
            }

            if (this.running) {
                this.render();
            }

            ++frames;
            if (System.currentTimeMillis() - timer > 1000L) {
                timer += 1000L;
                this.fps = frames;
                frames = 0L;
            }
        }

        this.stop();
    }

    private void tick() {
        for(int i = 0; i < handler.getObject().size(); ++i) {
            if ((handler.getObject().get(i)).getId() == ID.Player) {
                camera.tick(handler.getObject().get(i));
            }
        }
        if(SPEEDRUN)
            if(++TIME >= 100) {
                TIME = 0;
                SECONDS++;
            }
            else if(TIME == 20 || TIME == 40 || TIME == 60 || TIME == 80) {
                if(Objects.equals(speedrunTitle, "")) speedrunTitle = "SPEEDRUN";
                else speedrunTitle = "";
            }
        handler.tick();

        for (GUIButton guiElement : new ArrayList<>(guiElements)) {
            guiElement.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
        } else {
            Graphics g = bs.getDrawGraphics(); // GUI Graphics
            Graphics2D g2d = (Graphics2D) g.create(); // Game Graphics
            Rectangle r = frame.getBounds();
            if(frame.isAlwaysOnTop()) r = frame.getMaximizedBounds();
            int screenWidth = r.width;
            int screenHeight = r.height;

            double width = Game.WIDTH;
            double height = Game.HEIGHT;

            double zoomX = screenWidth / width;
            double zoomY = screenHeight / height;

            double zoomWidth = width * zoomX;
            double zoomHeight = height * zoomY;

            double anchorx = 0;
            double anchory = 0;

            AffineTransform at = new AffineTransform();
            at.translate(anchorx, anchory);
            at.scale(zoomX, zoomY);
            g2d.setTransform(at);

            if (handler.getObject().isEmpty()) {
                g2d.drawImage(gameImages.loadingScreen, 0, 0, null);
            }

            g2d.translate(-camera.getX(), -camera.getY());
            handler.render(g2d);
            g2d.translate(camera.getX(), camera.getY());
            g.setColor(Color.white);
            if(DEBUG) {
                g.drawString("FPS: " + this.fps, 10, 10);
                if (player != null) {
                    g.drawString("X: " + player.getX(), 10, 20);
                }

                if (player != null) {
                    g.drawString("Y: " + player.getY(), 10, 30);
                }
            }
            if(SPEEDRUN) {
                g.setFont(new Font("VERDANA", Font.BOLD, 45));
                FontMetrics fm = g.getFontMetrics();
                java.awt.geom.Rectangle2D rect = fm.getStringBounds(SECONDS + ":" + TIME, g);
                int textWidth = (int) (rect.getWidth());

                // Center text horizontally and vertically
                int centeredX = (screenWidth - textWidth) / 2;

                g.drawString(SECONDS + ":" + TIME, centeredX, screenHeight - 200);  // Draw the string.

                g.setColor(Color.red);
                g.setFont(new Font("VERDANA", Font.BOLD, 25));

                g.drawString(speedrunTitle, 10, 30);  // Draw the string.


            }

            int i;
            if (bubble_flag) {
                for(i = 0; i < bubble_counter; ++i) {
                    g.drawImage(gameImages.bubble, screenWidth / 2 - i * 38 - 16, screenHeight - 100, null);
                    g.drawImage(gameImages.bubble, screenWidth / 2 + i * 38 - 16, screenHeight - 100, null);
                }
            }

            if (!handler.getObject().isEmpty()) {
                for(i = 0; i < health_counter; ++i) {
                    g.drawImage(gameImages.heart, screenWidth / 2 - i * 38 - 16, screenHeight - 150, null);
                    g.drawImage(gameImages.heart, screenWidth / 2 + i * 38 - 16, screenHeight - 150, null);
                }
            }

            for (GUIButton guiElement : new ArrayList<>(guiElements)) {
                guiElement.render(g);
            }

            g.dispose();
            g2d.dispose();
            bs.show();
        }
    }

    public List<GUIButton> getGUIElements() {
        return new ArrayList<>(guiElements);
    }

    public static double clamp(double var, double min, double max) {
        if (var >= max) {
            return max;
        } else {
            return Math.max(var, min);
        }
    }

    public static void main(String[] args) {
        Platform.startup(() -> {});

        String PATH = "saves/";

        File directory = new File(PATH);
        if (! directory.exists()){
            boolean a = directory.mkdir();
        }

        try
        {
            //create instance of the CreateLoginForm
            LoginForm form = new LoginForm();
            form.setSize(WIDTH/2,HEIGHT/2);  //set size of the frame
            form.setVisible(true);  //make form visible to the user
        }
        catch(Exception e)
        {
            //handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
