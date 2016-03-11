package ForestFun;

import ForestFun.Ninja.item;

import org.newdawn.slick.state.*;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.logging.Level;

import java.util.logging.Logger;

import org.newdawn.slick.Animation;

import org.newdawn.slick.Color;

import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.BasicGame;

import org.newdawn.slick.Font;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;

import org.newdawn.slick.Input;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.SpriteSheet;

import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.geom.Rectangle;

import org.newdawn.slick.geom.Shape;

import org.newdawn.slick.state.BasicGameState;

import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.state.transition.FadeInTransition;

import org.newdawn.slick.state.transition.FadeOutTransition;

import org.newdawn.slick.tiled.TiledMap;

public class ForestFun extends BasicGameState {

    public static Player player;
    public static GemWin antidote;

    public item healthpotion, healthpotion1;
    public ItemGem speedpotion, speedpotion1;

    public Weapon magic8ball, weapon1;
    public Weapon bob;
    public Enemy monster;

    public ArrayList<Enemy> enemiez = new ArrayList();
    public ArrayList<item> stuff = new ArrayList();
    public ArrayList<ItemGem> stuff1 = new ArrayList();
    public ArrayList<GemWin> stuffwin = new ArrayList();
    public ArrayList<Weapon> weaponlist = new ArrayList();

    private boolean[][] hostiles;

    private static TiledMap grassMap;
    private static AppGameContainer app;
    private static Camera camera;

    public static int counter = 0;

	// player stuff
	//private Animation sprite, up, down, left, right, wait;
    /**
     *
     * The collision map indicating which tiles block movement - generated based
     *
     * on tile properties
     */
	// changed to match size of sprites & map
    private static final int SIZE = 64;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;

    public ForestFun(int xSize, int ySize) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        gc.setTargetFrameRate(60);

        gc.setShowFPS(false);

        grassMap = new TiledMap("res/d4.tmx");

		// Ongoing checks are useful
        camera = new Camera(gc, grassMap);
        player = new Player();

		// player stuff --- these things should probably be chunked into methods
        // and classes
		// Obstacles etc.
		// build a collision map based on tile properties in the TileD map
        Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {

                int tileID = grassMap.getTileId(xAxis, yAxis, 1);

                String value = grassMap.getTileProperty(tileID,
                        "blocked", "false");

                if ("true".equals(value)) {

                    Blocked.blocked[xAxis][yAxis] = true;

                }
            }
        }
        // A remarkably similar process for finding hostiles

        hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {
                        ItemGem h = new ItemGem(xAxis * SIZE, yAxis * SIZE);
                        stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }
        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Enemy e = new Enemy(xAxis * SIZE, yAxis * SIZE);
                        enemiez.add(e);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        stuff.add(healthpotion);
        stuff.add(healthpotion1);

        bob = new Weapon(1, 1);
        weapon1 = new Weapon(42, 42);

        monster = new Enemy((int) player.x + 142, (int) player.y + 142);

        speedpotion = new ItemGem(100, 150);
        speedpotion1 = new ItemGem(450, 100);
        stuff1.add(speedpotion);
        stuff1.add(speedpotion1);

        antidote = new GemWin(3004, 92);
        stuffwin.add(antidote);

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        camera.centerOn((int) player.x, (int) player.y);

        camera.drawMap();

        camera.translateGraphics();

        player.sprite.draw(player.x, player.y);

        g.drawString("Health: " + player.health / 1000, camera.cameraX + 10,
                camera.cameraY + 10);

        g.drawString("speed: " + (int) (player.speed * 10), camera.cameraX + 10,
                camera.cameraY + 25);
        
//        g.draw(player.rect);

        g.drawString("time passed: " + counter / 1000, camera.cameraX + 600, camera.cameraY);

        for (ItemGem h : stuff1) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
            }
        }

        if (weapon1.isIsVisible()) {
            weapon1.weaponpic.draw(weapon1.getX(), weapon1.getY());
        }
        for (Enemy e : enemiez) {
            if (e.isVisible) {
                e.currentanime.draw(e.Bx, e.By);
            }
        }
        for (GemWin w : stuffwin) {
            if (w.isvisible) {
                w.currentImage.draw(w.x, w.y);
            }

        }
        antidote.currentImage.draw(player.x, player.y);

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        
        player.rect.setLocation(player.getplayershitboxX(), player.getplayershitboxY());
        
        counter += delta;
        Input input = gc.getInput();
        float fdelta = delta * player.speed;
        player.setpdelta(fdelta);
        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);

        float projectedright = player.x + fdelta + SIZE;
        boolean cangoright = projectedright < rightlimit;
        // there are two types of fixes. A kludge and a hack. This is a kludge.
        if (input.isKeyDown(Input.KEY_UP)) {
            player.setDirection(0);
            player.sprite = player.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(player.x, player.y - fdelta) || isBlocked(
                    (float) (player.x + SIZE + 1.5), player.y - fdelta))) {
                player.sprite.update(delta);
                // The lower the delta the slower the sprite will animate.
                player.y -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_DOWN)) {            player.setDirection(2);
            player.sprite = player.down;
            if (!isBlocked(player.x, player.y + SIZE + fdelta)
                    || !isBlocked(player.x + SIZE - 1, player.y + SIZE + fdelta)) {
                player.sprite.update(delta);
                player.y += fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            player.setDirection(3);
            player.sprite = player.Left;
            if (!(isBlocked(player.x - fdelta, player.y) || isBlocked(player.x
                    - fdelta, player.y + SIZE - 1))) {
                player.sprite.update(delta);
                player.x -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.setDirection(1);
            player.sprite = player.right;
            // the boolean-kludge-implementation
            if (cangoright
                    && (!(isBlocked(player.x + SIZE + fdelta,
                            player.y) || isBlocked(player.x + SIZE + fdelta, player.y
                            + SIZE - 1)))) {
                player.sprite.update(delta);
                player.x += fdelta;
            }
        } else if (input.isKeyPressed(Input.KEY_SPACE)) {
            //orb1.setLocation(player.x, player.y);
            weapon1.setDirection(player.getDirection());
            weapon1.settimeExists(100);
            weapon1.setX((int) player.x);
            weapon1.setY((int) player.y);
            weapon1.hitbox.setX(weapon1.getX());
            weapon1.hitbox.setY(weapon1.getY());
            weapon1.setIsVisible(true);
            //magic8ball.setIsVisible(true);
        }

        for (ItemGem h : stuff1) {
            if (player.rect.intersects(h.hitbox)) {
                if (h.isvisible) {
                    player.speed += .1f;
                    h.isvisible = false;
                }
            }
        }

        for (Enemy e : enemiez) {
            if (weapon1.hitbox.intersects(e.rect)) {
                e.isVisible = false;
            }
            
            if(player.rect.intersects(e.rect)) {
               
                if(e.isVisible) {
                    player.health -= 500;
                }
                
            }
            
        }
        for (GemWin w : stuffwin) {

            if (player.rect.intersects(w.hitbox)) {
                if (w.isvisible) {
                    w.isvisible = false;
                    makevisible();
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }

            }
        }
        if (weapon1.isIsVisible()) {
            if (weapon1.gettimeExists() > 0) {
                if (weapon1.getDirection() == 0) {
                    weapon1.setX((int) player.x);
                    weapon1.setY(weapon1.getY() - 5);
                } else if (weapon1.getDirection() == 2) {
                    weapon1.setX((int) player.x);
                    weapon1.setY(weapon1.getY() + 5);
                } else if (weapon1.getDirection() == 3) {
                    weapon1.setX(weapon1.getX() - 5);
                    weapon1.setY(weapon1.getY());
                } else if (weapon1.getDirection() == 1) {
                    weapon1.setX(weapon1.getX() + 5);
                    weapon1.setY(weapon1.getY());
                }
                weapon1.hitbox.setX(weapon1.getX());
                weapon1.hitbox.setY(weapon1.getY());
                weapon1.countdown();
            } else {
                weapon1.setIsVisible(false);
            }
        }
//        player.health -= 10;
        if (player.health <= 0) {
            makevisible();

        }
        for (Enemy e : enemiez) {
            if (e.isVisible) {
                e.move();
            }
        }
    }

    public int getID() {

        return 1;
    }

    public void makevisible() {
        for (ItemGem h : stuff1) {
            h.isvisible = true;
        
        }

//        for (item i : stuff) {
//            i.isvisible = true;
//        }
    }

    private boolean isBlocked(float tx, float ty) {

        int xBlock = (int) tx / SIZE;
        int yBlock = (int) ty / SIZE;

        return Blocked.blocked[xBlock][yBlock];

    }

}
