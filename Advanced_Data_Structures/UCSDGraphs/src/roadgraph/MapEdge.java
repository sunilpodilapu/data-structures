package roadgraph;

import geography.GeographicPoint;


/**
 * MapEdge graph to be used by MapNodes and MapGraph
 * Arguments must be valid prior to edge creation
 *
 * @author Mark
 */
public class MapEdge {
    private GeographicPoint from;
    private GeographicPoint to;
    private String roadname;
    private String roadType;
    private double length;

    public MapEdge(GeographicPoint from, GeographicPoint to,
                   String roadname, String roadType, double length) {

        // check to make sure arguments are valid prior to creation
        if (from != null || to != null ||
                roadname != null || roadType != null || length < 0) {
            this.from = from;
            this.to = to;
            this.roadname = roadname;
            this.roadType = roadType;
            this.length = length;
        } else {
            throw new IllegalArgumentException("Invalid edge param");
        }
    }

    /**
     * Getter for starting location point
     * @return GeogrphicPoint
     */
    public GeographicPoint getFrom() {
        return from;
    }

    /**
     * Getter for ending location point
     * @return GeogrphicPoint
     */
    public GeographicPoint getTo() {
        return to;
    }

    /**
     * Getter for name of edge
     * @return String roadname
     */
    public String getRoadname() {
        return roadname;
    }

    /**
     * Getter for type of edge
     * @return String roadType
     */
    public String getRoadType() {
        return roadType;
    }

    /**
     * Getter for length of edge
     * @return String length
     */
    public double getLength() {
        return length;
    }
}
