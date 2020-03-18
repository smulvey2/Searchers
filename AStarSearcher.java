import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

    /**
     * Calls the parent class constructor.
     * 
     * @see Searcher
     * @param maze initial maze.
     */
    public AStarSearcher(Maze maze) {
        super(maze);
    }

    /**
     * Main a-star search algorithm.
     * 
     * @return true if the search finds a solution, false otherwise.
     */
    public boolean search() {
    	//establishes the starting point
        State first = new State(maze.getPlayerSquare(), null, 0, 0);
        //creates the formula for finding distance to goal node
        double euclidianD = Math.sqrt(Math.pow((maze.getPlayerSquare().X - maze.getGoalSquare().X), 2) 
        + Math.pow((maze.getPlayerSquare().Y - maze.getGoalSquare().Y), 2));
        
        StateFValuePair root = new StateFValuePair(first, euclidianD + first.getGValue());

        // explored list is a Boolean array that indicates if a state associated with a given
        // position in the maze has already been explored.
        boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
        explored[first.getX()][first.getY()] = true;
        
        //initializes priority queue
        PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
        frontier.add(root);
        
        //initializes states for traversing graph
        StateFValuePair state1;
        StateFValuePair state2 = null;
        State previous;
        
        ArrayList<State> path = new ArrayList<State>();
        State adjacent;
        noOfNodesExpanded+=1;
        
        //loops until frontier is empty, printing "." at each traversed node in the chosen path
        while (!frontier.isEmpty()) {
            state1 = frontier.poll();
            previous = state1.getState();
            
            //handles finding goal
            if (state1.getState().isGoal(maze) == true) {
               while (previous.getParent() != null) {
                   if (maze.getSquareValue(previous.getX(), previous.getY()) == ' ') {
                       maze.setOneSquare(previous.getSquare(), '.');
                    }
                    
                    cost+=1;
                    maxDepthSearched++;
                    previous = previous.getParent();
                    if (previous.getParent().getParent() == null) {
                        cost+=1;
                        maxDepthSearched+=1;
                        break;
                    }
                }
                return true;
            } 
            path = state1.getState().getSuccessors(explored, maze, previous);
            noOfNodesExpanded+=1;
            
            while(!path.isEmpty()) {
                adjacent = path.remove(0);
                euclidianD = Math.sqrt(Math.pow((adjacent.getX() - maze.getGoalSquare().X), 2) 
                + Math.pow((adjacent.getY() - maze.getGoalSquare().Y), 2));
                StateFValuePair state3 = new StateFValuePair(adjacent, adjacent.getGValue() + euclidianD);
                StateFValuePair state4;
                Iterator<StateFValuePair> searcher = frontier.iterator();
                
                while (searcher.hasNext()) {
                   state4 = searcher.next();
                    if (state4.getState().equals(adjacent)) {
                       state2 = state4;
                        if (adjacent.getGValue() < state2.getState().getGValue()) {
                           frontier.add(state3);
                           frontier.remove(state2);
                        }
                    }
                } 
                
                if (state2 == null && !explored[adjacent.getX()][adjacent.getY()]) {
                    frontier.add(state3);
                }
                explored[adjacent.getX()][adjacent.getY()] = true;
            }
            if (maxSizeOfFrontier < frontier.size()) {
                maxSizeOfFrontier = frontier.size();
            }
        }
        return false;
    }
}
