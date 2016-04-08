package be.uantwerpen.sc.tools;

/**
 * Created by Arthur on 3/04/2016.
 */
public enum Tile {
    POINT,
    VERTICAL,
    HORIZONTAL,
    //bend
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST,
    //3way-intersect
    NORTH_EAST_WEST,
    EAST_SOUTH_NORTH,
    SOUTH_WEST_EAST,
    WEST_NORTH_SOUTH,
    //4way-intersect
    INTERSECT
}
