package me.wiviw.custominventories;

import org.bukkit.entity.EntityType;

import java.util.Arrays;

public class EntityInfo implements Cloneable{
    
	private EntityType type;
    private String name;
    private double[] location;
    private int direction;
	
	public EntityInfo(EntityType iType, String iName , double[] iLocation, int iDirection) {
		type = iType;
		name = iName;
		location = iLocation;
		direction = iDirection;
    }
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType iType) {
		type = iType;
	}

	public String getName() {
		return name;
	}
	public void setName(String iName) {
		name = iName;
	}

	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] iLocation) {
		location = iLocation;
	}

	public double getLocationX() {
		return location[0];
	}
	public void setLocationX(double xLoc) {
		location[0] = xLoc;
	}

	public double getLocationY() {
		return location[1];
	}
	public void setLocationY(double yLoc) {
		location[1] = yLoc;
	}

	public double getLocationZ() {
		return location[2];
	}
	public void setLocationZ(double zLoc) {
		location[2] = zLoc;
	}

	public int getDirection() {
		return direction;
	}
	public void setDirection(int iDirection) {
		direction = iDirection;
	}
	public String toString() {
		StringBuilder toString = (new StringBuilder("EntityInfo{")).append(type).append(",").append(name).append(",").append(Arrays.toString(location)).append(",").append(direction);
		return toString.append('}').toString();
	}
}
