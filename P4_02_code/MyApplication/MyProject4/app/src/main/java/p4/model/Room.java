package p4.model;

import java.util.Objects;

/**
 * Model object representing a Room
 */
public class Room {
    /**
     * name Room
     */
    private String name;
    /**
     * color Room
     */
    private int color;

    /**
     * Constructor
     * name
     * color
     */
    public Room(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getName(), room.getName());
    }
}

