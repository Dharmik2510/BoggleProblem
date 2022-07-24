import java.util.List;

//Class for storing the state of each possible path
public class RouteState
{

    private Coordinates starting_coordinate;
    private Coordinates next_coordinate;

    //Navigation string for this state
    private String navigationString;

    //List of all visited coordinates for this state
    private List<Coordinates> allVisitedCoordinates;

    //Word that is formed for this state
    private String matchingString;

    //Empty Constructor
    RouteState()
    {

    }

    //Parameterized Constructor
    public RouteState(Coordinates starting_coordinate, Coordinates next_coordinate, String navigationString, List<Coordinates> allVisitedCoordinates, String matchingString) {
        this.starting_coordinate = starting_coordinate;
        this.next_coordinate = next_coordinate;
        this.navigationString = navigationString;
        this.allVisitedCoordinates = allVisitedCoordinates;
        this.matchingString = matchingString;
    }


    public Coordinates getStarting_coordinate() {
        return starting_coordinate;
    }

    public void setStarting_coordinate(Coordinates starting_coordinate) {
        this.starting_coordinate = starting_coordinate;
    }

    public Coordinates getNext_coordinate() {
        return next_coordinate;
    }

    public void setNext_coordinate(Coordinates next_coordinate) {
        this.next_coordinate = next_coordinate;
    }

    public String getNavigationString() {
        return navigationString;
    }

    public void setNavigationString(String navigationString) {
        this.navigationString = navigationString;
    }

    public List<Coordinates> getAllVisitedCoordinates() {
        return allVisitedCoordinates;
    }

    public void setAllVisitedCoordinates(List<Coordinates> allVisitedCoordinates) {
        this.allVisitedCoordinates = allVisitedCoordinates;
    }

    public String getMatchingString() {
        return matchingString;
    }

    public void setMatchingString(String matchingString) {
        this.matchingString = matchingString;
    }
}
