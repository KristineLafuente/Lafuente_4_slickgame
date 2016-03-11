/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ForestFun;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Kristine
 */
public class GemWin {
    
	public int x;
	public int y;
	public boolean isvisible = true;
	public Image currentImage;
	Shape hitbox;
	Image antidote = new Image("res/WinGem.png");

	GemWin (int a, int b) throws SlickException {
		this.x = a;
		this.y = b;
		this.hitbox = new Rectangle(a, b, 32, 32);
		this.currentImage = antidote;

	}
    
}
