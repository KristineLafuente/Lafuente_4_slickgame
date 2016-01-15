/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickexample;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Kristine
 */
public class Ninja {
    Object currentImage;

    Ninja(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public class item {
    
	public int x;
	public int y;
	public boolean isvisible = true;
	Image currentImage;
	Shape hitbox;
	Image ninjaImage = new Image(
			"res/d22_ninja.png");

	item(int a, int b) throws SlickException {
		this.x = a;
		this.y = b;
		
		this.currentImage = ninjaImage;

	}
    
}
}
