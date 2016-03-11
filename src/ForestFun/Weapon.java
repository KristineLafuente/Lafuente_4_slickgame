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
public class Weapon {
 private int positionX;
 private int positionY;
 int x;
 int y;
 private boolean isVisible;
 public  Image weaponpic;
 Shape hitbox; 
 private int timeExists;
    private int direction;

 
 public Weapon(int a, int b) throws SlickException{
     this.x = a;
     this.y = b;
     this.isVisible = false;
     this.weaponpic = new Image ("res/Lafuente_Weapon.png");
     this.hitbox = new Rectangle (a,b, 16, 16);
     this.timeExists = 0;
     
 }
 
 
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Image getWeaponpic() {
        return weaponpic;
    }

    public void setWeaponpic(Image orbpic) {
        this.weaponpic = orbpic;
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public void setHitbox(Shape hitbox) {
        this.hitbox = hitbox;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTimeExists(int timeExists) {
        this.timeExists = timeExists;
    }

    

    public int gettimeExists() {
       return this.timeExists;
    }

    public void countdown() {
       this.timeExists--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTimeExists() {
        return timeExists;
    }

    public void settimeExists(int i) {
        this.timeExists = i;
           }

    public void setDirection(int direction) {
        this.direction = direction;
           }

    public int getDirection(){
        return this.direction;
    }
    }
   

 /**
  * Getters and setters are a common concept in Java.
  * A design guideline in Java, and object oriented programming in general, is to encapsulate/isolate values as musch as possible
  * Getters - are methods used to query the value of instance variables.
  * this.getlocationX();
  * Setters - methods that set values for instance variable. 
  * orb1.setLocation(Player.x, Player.y);
  */     
     
 
 
 
    
 
