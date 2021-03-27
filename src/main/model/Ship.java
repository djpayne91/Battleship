package main.model;

/**
 * Class representing a player's ship. It has an ID, a length, and a number of hit points determined by the length.
 */
public class Ship {

    private ShipType id;
    private int length;
    private int HP;


    /**
     * Public constructor for Ship class. Requires an ID and a ship length. Length of the ship will determine its HP.
     * @param id int ID of this Ship.
     * @param length length of Ship.
     */
    public Ship(ShipType id, int length) {
        this.id = id;
        this.length = length;
        this.HP = length;
    }

    /**
     * Public getter for this ship's ID.
     * @return id
     */
    public ShipType getId() {
        return id;
    }
    /**
     * Public getter for this ship's length.
     * @return length
     */
    public int getLength() {
        return length;
    }
    /**
     * Public getter for this ship's remaining hit points(HP).
     * @return HP
     */
    public int getHP() {
        return HP;
    }

    public void hit(){
        HP--;
    }

    public boolean isSunk(){
        return HP <= 0;
    }
}
