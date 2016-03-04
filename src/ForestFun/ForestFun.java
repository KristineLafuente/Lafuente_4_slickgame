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
	public item healthpotion, healthpotion1;
	public item1 speedpotion, speedpotion1;
	public itemwin antidote;
        public Orb magic8ball, orb1;
        public Orb bob;
        public Enemy monster;
        
        public ArrayList<Enemy> enemiez = new ArrayList();
	public ArrayList<item> stuff = new ArrayList();
        public ArrayList<item1> stuff1 = new ArrayList();
	public ArrayList<itemwin> stuffwin = new ArrayList();
        public ArrayList<Orb> orblist = new ArrayList(); 
        
	private boolean[][] hostiles;

	private static TiledMap grassMap;
        private static AppGameContainer app;
        private static Camera camera;
	
	public static int counter = 0;

	// player stuff

	//private Animation sprite, up, down, left, right, wait;

	/**
	 
	 * The collision map indicating which tiles block movement - generated based

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

		//System.out.println("Tile map is this wide: " + grassMap.getWidth());

		camera = new Camera(gc, grassMap);
                player = new Player();

		// player stuff --- these things should probably be chunked into methods
		// and classes

		// Obstacles etc.

		// build a collision map based on tile properties in the TileD map

		Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];

		// There can be more than 1 layer. You'll check whatever layer has the
		// obstacles.

		// You could also use this for planning traps, etc.

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {

                // int tileID = grassMap.getTileId(xAxis, yAxis, 0);

                // Why was this changed?

                // It's a Different Layer.

                // You should read the TMX file. It's xml, i.e.,human-readable


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
					if (xBlock % 9 == 0	&& yBlock % 25 == 0) {
						item1 h = new item1(xAxis * SIZE, yAxis * SIZE);
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

		//healthpotion = new item(100, 100);
		//healthpotion1 = new item(450, 400);
		stuff.add(healthpotion);
		stuff.add(healthpotion1);

                bob = new Orb(1,1);
                orb1 = new Orb(42, 42);
               
		monster = new Enemy((int) player.x + 142, (int) player.y + 142);
                
		speedpotion = new item1(100,150);
		speedpotion1 = new item1(450,100);	
		stuff1.add(speedpotion);
		stuff1.add(speedpotion1);
		
		antidote = new itemwin(3004,92);
		stuffwin.add(antidote);
                
                    //orb1 = new Orb (42,42);
                
	}


	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)

	throws SlickException {

		camera.centerOn((int) player.x, (int) player.y);

		camera.drawMap();

		camera.translateGraphics();

                player.sprite.draw(player.x, player.y);
                
		g.drawString("Health: " + player.health/1000, camera.cameraX + 10,
				camera.cameraY + 10);
		
		g.drawString("speed: " + (int)(player.speed *10), camera.cameraX + 10,
				camera.cameraY + 25);

		//g.draw(player.rect);

		g.drawString("time passed: " +counter/1000, camera.cameraX +600,camera.cameraY );
		// moveenemies();
                
                        for (item1 h : stuff1) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
            }
        }
                
            if(orb1.isIsVisible()){
                    orb1.orbpic.draw(orb1.getX(),orb1.getY());
            }
                    for (Enemy e : enemiez) {
            if (e.isVisible) {
                e.currentanime.draw(e.Bx, e.By);
            }
        }
            
        }
        

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 

	throws SlickException {
               counter += delta;
        Input input = gc.getInput();
        float fdelta = delta * player.speed;
        player.setpdelta(fdelta);
        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
        // System.out.println("Right limit: " + rightlimit);
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

        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.setDirection(2);
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
            orb1.setDirection(player.getDirection());
            orb1.settimeExists(100);
            orb1.setX((int) player.x);
            orb1.setY((int) player.y);
            orb1.hitbox.setX(orb1.getX());
            orb1.hitbox.setY(orb1.getY());
            orb1.setIsVisible(true);
            //magic8ball.setIsVisible(true);
        }
                        for (item1 h : stuff1) {
            if (player.rect.intersects(h.hitbox)) {
                if (h.isvisible) {
                    player.speed += .1f;
                    h.isvisible = false;
                }
            }
        }


               for (Enemy e : enemiez) {
                   if (orb1.hitbox.intersects(e.rect)){
                        e.isVisible = false;
                    }   
               }
        if (orb1.isIsVisible()) {
            if (orb1.gettimeExists() > 0) {
                if (orb1.getDirection() == 0) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() - 5);
                } else if (orb1.getDirection() == 2) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() + 5);
                } else if (orb1.getDirection() == 3) {
                    orb1.setX(orb1.getX() - 5);
                    orb1.setY(orb1.getY());
                } else if (orb1.getDirection() == 1) {
                    orb1.setX(orb1.getX() + 5);
                    orb1.setY(orb1.getY());
                }
                orb1.hitbox.setX(orb1.getX());
                orb1.hitbox.setY(orb1.getY());
                orb1.countdown();
            } else {
                orb1.setIsVisible(false);
            }
        }
                player.health -= counter/1000;
		if(player.health <= 0){
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

    public void makevisible(){
        stuff1.stream().forEach((h) -> {
                h.isvisible = true;
            });
		
        stuff.stream().forEach((i) -> {
                i.isvisible = true;
            });
        }
	
    private boolean isBlocked(float tx, float ty) {

            int xBlock = (int) tx / SIZE;
            int yBlock = (int) ty / SIZE;

            return Blocked.blocked[xBlock][yBlock];

	}

}
