import java.util.*;

public class BoggleSolver{

    //List that will store the all coordinates
    private LinkedList<Coordinates> coordinationObjectLinkedList;

    //Final solutionString
    private  String solutionString;

    //List that will contain all the final solutionString
    private List<String> solutionList = new ArrayList<>();


    /**
     *This method will be used to solve the boggle problem
     * this is the method from where algorithm execution will start
     * @return
     * List<String> - This method will return the list of solutionString
     */

    public List<String> solve() {
            for (int i = 0; i < DriverClass.dictionary.length; i++) {

                //Find current word from the dictionary
                String word = DriverClass.dictionary[i];

                //List for maintaining the multiple first letters if present in the puzzlegrid to explore each path of the first letter
                List<Coordinates> multipleFirstLetters = new ArrayList<>();

                char findingC = word.charAt(0);

                int iPos = findingC - 'A';
                coordinationObjectLinkedList = DriverClass.charToCoordinates.get(iPos);

                //Treemap to sort the multiple paths if founded to get the min path
                //If both multiple path found then comapare X postion and return min of both X
                //If X postion is same for both then compare Y position
                TreeMap<Coordinates, String> finalMap = new TreeMap<>(new Comparator<Coordinates>() {
                    @Override
                    public int compare(Coordinates o1, Coordinates o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        } else if (o1.getX_pos() > o2.getX_pos()) {
                            return 1;

                        } else if(o1.getX_pos()==o2.getY_pos())
                        {
                            if(o1.getY_pos()>o2.getY_pos())
                            {
                                return 1;
                            }
                            else
                            {
                                return -1;
                            }
                        } else
                            {
                            return -1;
                        }
                    }
                });


                if (coordinationObjectLinkedList.size() == 0) {
                    continue;
                }
                multipleFirstLetters.addAll(coordinationObjectLinkedList);


                for (Coordinates coordinationObject : multipleFirstLetters)
                {
                    //Creating new stack and passing as an argument
                    Stack<RouteState> stateStack = new Stack();

                    //calling explore method for first character and storing returned result state
                    RouteState foundsState = explore(coordinationObject,word, stateStack);

                    if(!foundsState.getNavigationString().equals(""))
                    {
                        finalMap.put(foundsState.getStarting_coordinate(), foundsState.getNavigationString());
                    }

                }

                for (Map.Entry<Coordinates, String> entry : finalMap.entrySet()) {

                    solutionString = word + "      " + (entry.getKey().getY_pos() + 1) + "        " + (DriverClass.puzzleGrid.length -entry.getKey().getX_pos())  + "       " + entry.getValue();
                    solutionList.add(solutionString);
                    break;

                }


            }
        return solutionList;

        }

    /**
     * This method will explore the starting letter of each word for finding the final solution
     * by maintaining the state of each possible path.
     * @param
     * coordinationObject - starting letter of each word of the dictionary
     * @param
     * word - current word of the dictionary
     * @param
     * maintainState - stack to maintain the each possible RouteState
     *
     * @return
     * RouteState - This method will return the final state that is the state which found the final solution if reached
     */

    private RouteState explore(Coordinates coordinationObject, String word, Stack<RouteState> maintainState)
    {
        //Creating the new stack for every first character
        maintainState = new Stack<>();

        //Setting up the initial Starting state
        RouteState startingRouteState = new RouteState();
        startingRouteState.setStarting_coordinate(coordinationObject);
        startingRouteState.setNext_coordinate(coordinationObject) ;
        startingRouteState.setMatchingString(word.charAt(0)+"");
        startingRouteState.setNavigationString("");

        List<Coordinates> coordinationObjectList = new ArrayList<>();
        coordinationObjectList.add(coordinationObject);
        startingRouteState.setAllVisitedCoordinates(coordinationObjectList);
        maintainState.push(startingRouteState);


        //Iterate over stack till stack becomes empty
        while (!maintainState.isEmpty())
        {
            RouteState prevState = maintainState.pop();
            char findingCharacter =word.charAt(prevState.getAllVisitedCoordinates().size());
            int indexPos = findingCharacter - 'A';
            coordinationObjectLinkedList = DriverClass.charToCoordinates.get(indexPos);

            for(Coordinates nextCoordinate : coordinationObjectLinkedList)
            {
                List<Coordinates> visitedCoordinates = prevState.getAllVisitedCoordinates();
                if(visitedCoordinates.contains(nextCoordinate))
                {
                    continue;
                }
                if(!adjacent(prevState.getNext_coordinate(),nextCoordinate))
                {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                String updatedWord= prevState.getMatchingString()+findingCharacter;
                String s = prevState.getNavigationString() + setNavigation(prevState.getNext_coordinate(),nextCoordinate,sb);
                List<Coordinates> copyVisited =new ArrayList<>(prevState.getAllVisitedCoordinates());
                copyVisited.add(nextCoordinate);
                RouteState added_State = new RouteState(prevState.getStarting_coordinate(),nextCoordinate,s,copyVisited,updatedWord);

                if(word.equals(added_State.getMatchingString()))
                {
                    //If found the final solution state then return that state for the given first character
                    return added_State;
                }

                //Pushing into stack the state which is unexplored
                maintainState.push(added_State);

            }

        }
        return startingRouteState;
    }


    /**
     * This method will be used to check if the previousCoordinate is the adjacent of the currentCoordinate or not
     * @param
     * previousCoordinate - previousCoordinate of the previousState  to which this method will compare the currentCoordinate
     * @param
     * currentCoordinate - currentCoordinate of the currentState
     * @return
     * boolean - This method will return true if adjacent otherwise false
     */

    private boolean adjacent(Coordinates previousCoordinate, Coordinates currentCoordinate) {


        int previous_x = previousCoordinate.getX_pos();
        int previous_y = previousCoordinate.getY_pos();
        int current_x = currentCoordinate.getX_pos();
        int current_y = currentCoordinate.getY_pos();
        int value = Math.max(Math.abs(previous_x - current_x), Math.abs(previous_y - current_y));
        return value <= 1;

    }

    /**
     * This method will be used to find the navigation string of the current dictionary word
     *
     * @param
     * previousCoordinate - Coordinate from which nextCoordinate is found
     * @param
     * coordinationObject - Current next Coordinate
     * @param
     * navigationString - String that will contain the navigation till where we have explored
     *
     * @return
     * StringBuilder -  This method will return the StringBuilder object of the current explored state
     */
    private StringBuilder setNavigation(Coordinates previousCoordinate, Coordinates coordinationObject, StringBuilder navigationString) {

        int previous_x = previousCoordinate.getX_pos();
        int previous_y = previousCoordinate.getY_pos();
        int current_x = coordinationObject.getX_pos();
        int current_y = coordinationObject.getY_pos();
        int final_x_value = (previous_x - current_x);
        int final_y_value = (previous_y - current_y);

        if (final_x_value == 0 && final_y_value == -1) {
            navigationString.append(NavigationLetters.goRight);
        } else if (final_x_value == -1 && final_y_value == 0) {
            navigationString.append(NavigationLetters.goDown);
        } else if (final_x_value == 1 && final_y_value == 0) {
            navigationString.append(NavigationLetters.goUp);
        } else if (final_x_value == 0 && final_y_value == 1) {
            navigationString.append(NavigationLetters.goLeft);
        } else if (final_x_value == -1 && final_y_value == -1) {
            navigationString.append(NavigationLetters.goDiagonallyDownAndToTheRight);
        } else if (final_x_value == -1 && final_y_value == 1) {
            navigationString.append(NavigationLetters.goDiagonallyDownAndToTheLeft);
        } else if (final_x_value == 1 && final_y_value == -1) {
            navigationString.append(NavigationLetters.goDiagonallyUpAndToTheRight);
        } else if (final_x_value == 1 && final_y_value == 1) {
            navigationString.append(NavigationLetters.goDiagonallyUpAndToTheLeft);
        }
        return navigationString;
    }
}
