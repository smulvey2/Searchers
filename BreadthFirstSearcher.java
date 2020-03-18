import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

    /**
     * Calls the parent class constructor.
     * 
     * @see Searcher
     * @param maze initial maze.
     */
    public BreadthFirstSearcher(Maze maze) {
        super(maze);
    }

    /**
     * Main breadth first search algorithm.
     * 
     * @return true if the search finds a solution, false otherwise.
     */
    public boolean search() {
        State first = new State(maze.getPlayerSquare(), null, 0, 0);

        // explored list is a 2D Boolean array that indicates if a state associated with a given
        // position in the maze has already been explored.
        boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
        explored[first.getX()][first.getY()] = true;

        // Queue implementing the Frontier list
        LinkedList<State> lList = new LinkedList<State>();
        lList.add(first);
        maxSizeOfFrontier += 1;
        int frontier = 0;
        
        //as long as the frontier has nodes, continue exploring nodes in the frontier
        while (!lList.isEmpty()) {
            State state1 = lList.pop();
            maxSizeOfFrontier--;
            ArrayList<State> list = new ArrayList<State>();
            list = state1.getSuccessors(explored, maze, state1);
            
            for (int i = 0; i < list.size(); i++) {
                explored[list.get(i).getX()][list.get(i).getY()] = true;
                lList.add(list.get(i));
                noOfNodesExpanded+=1;
                maxSizeOfFrontier+=1;
                if (maxSizeOfFrontier > frontier) {
                    frontier = maxSizeOfFrontier;
                }
                //handles finding goal
                if (list.get(i).isGoal(maze) == true) {
                    State path = list.get(i);
                    
                    //prints out the path with "." by traversing the correct path backwards
                    while (path.getParent() != null) {
                        if (maze.getSquareValue(path.getX(), path.getY()) == ' ') {
                            maze.setOneSquare(path.getSquare(), '.');
                        }
                        cost+=1;
                        maxDepthSearched+=1;
                        path = path.getParent();
                    }
                    list.get(i).getParent();
                    maxSizeOfFrontier = frontier;
                    noOfNodesExpanded += 1;
                    return true;
                }
            }
        }

        return false;
    }
}
