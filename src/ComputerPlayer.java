import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;
    private int maxLooks = 5;
    private int max = 2;
    private int min = 1;
    private Node mainNode;
    private ArrayList<Node> children;
    private ArrayList<Node> rootNodes;

    public ComputerPlayer(Othello othello, int player, int d) {
        this.othello = othello;
        this.max = player;
        min = (max == 1) ? 2 : 1;
        maxLooks = d;
    }

    public void move(Board board) {
        //move ai
        if (board.getAllMoves(max).size() == 0) {
            System.out.println("No legal moves");
            return;
        }
        if (Fields.useMinimax()) {
            Move bestMove = lookAhead(board);
            board.makeMove(bestMove, max);
        } else if (Fields.useRandom()) {
            Move move = random(board);
            board.makeMove(move, max);
        } else if (Fields.useCasual()) {
            Move move = casual(board);
            board.makeMove(move, max);
        }
    }

    public Move random(Board board) {
        ArrayList<Move> moves = board.getAllMoves(max);
        if (Fields.useCorners()) {
            for (Move move : moves) {
                int row = move.row;
                int col = move.col;
                if ((col == 0 && (row == 0 || row == board.getBoardHeight() - 1)) || (col == board.getBoardWidth() - 1 && (row == 0 || row == board.getBoardHeight() - 1))) { //corner bias
                    return move;
                }
            }
        }
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }

    public Move casual(Board board) {
        ArrayList<Move> moves = board.getAllMoves(max);
        int bestValue = -1;
        int bestIndex = -1;
        for (Move move : moves) {
            int val = othello.getValue(move, board, max);
            if (val > bestValue) {
                bestValue = val;
                bestIndex = moves.indexOf(move);
                int row = move.row;
                int col = move.col;
                if (Fields.useCorners() && (col == 0 && (row == 0 || row == board.getBoardHeight() - 1)) || (col == board.getBoardWidth() - 1 && (row == 0 || row == board.getBoardHeight() - 1))) { //corner bias
                    break;
                }
            }
        }

        return moves.get(bestIndex);
    }

    public Move lookAhead(Board board) {
        simMoves(board);
        //start from leaves and move upwards
        ArrayList<Node> leafNodes = new ArrayList<Node>();
        for (Node node : children) {
            if (node.getChildren().size() == 0) {
                leafNodes.add(node);
            }
        }

        ArrayList<ArrayList> rootMovesArray[] = new ArrayList[rootNodes.size()];
        for (int i = 0; i < rootMovesArray.length; i++) {
            rootMovesArray[i] = new ArrayList<ArrayList>();
        }

        for (Node leaf : leafNodes) {
            Node currNode = leaf;
            Node parent = currNode.getParent();
            ArrayList<Move> allMoves = new ArrayList<Move>();
            while (true) {
                allMoves.add(currNode.getMove());

                if (parent.getParent() != null) {
                    currNode = currNode.getParent();
                    parent = currNode.getParent();
                } else {
                    break;
                }
            }

            Collections.reverse(allMoves);

            for (int i = 0; i < rootNodes.size(); i++) {
                Node rootNode = rootNodes.get(i);
                if (currNode.equals(rootNode)) {
                    ArrayList<ArrayList> list = rootMovesArray[i];
                    list.add(allMoves);
                }
            }
        }

        float bestEval = Float.MAX_VALUE;
        int bestIndex = -1;
        for (int i = 0; i < rootMovesArray.length; i++) {
            ArrayList<ArrayList> rootMovesList = rootMovesArray[i];
            float loses = 0;
            float totalBoards = 0;
            for (ArrayList<Move> moveOrder : rootMovesList) {
                int player = max;
                Board temp = board.cloneBoard();
                for (Move move : moveOrder) {
                    temp.makeMove(move, player);
                    player = (player == max) ? min : max;
                }
                loses += othello.evaluate(temp, max);
                totalBoards++;
            }
            float result = loses / totalBoards;
//            System.out.println("OOLOOOO - " + loses + ", " + totalBoards);
            if (result < bestEval) {
                bestEval = result;
                bestIndex = i;
            }
        }

//        System.out.println(bestEval);
        return rootNodes.get(bestIndex).getMove();
    }

    //notes. node tree will now generate but the depth variable probably wont work just yet.
    public void simMoves(Board board) {
        mainNode = new Node();
        children = new ArrayList<Node>();
        rootNodes = new ArrayList<Node>();
        ArrayList<Move> allMoves = board.getAllMoves(max);
        for (Move move : allMoves) {
            rootNodes.add(new Node(mainNode, move));
        }
        if (allMoves.size() > 0) {
            simMoves(mainNode, allMoves, board, max, min, maxLooks);
        }
//        System.out.println(children.size());
    }

    public void simMoves(Node parent, ArrayList<Move> allMoves, Board board, int playerA, int playerB, int depth) {
        Collections.shuffle(allMoves);
        //System.out.println(children.size());
        if (depth > 0) {
            for (Move currMove : allMoves) {
                Node currNode = new Node(parent, currMove);
                children.add(currNode);
                parent.addChild(currNode);
                //make move
                Board tempBoard = board.cloneBoard();
                tempBoard.makeMove(currMove, playerA);
                //sim opponent move
                ArrayList<Move> opponentMoves = tempBoard.getAllMoves(playerB);
                if (opponentMoves.size() > 0) {
                    this.simMoves(currNode, opponentMoves, tempBoard, playerB, playerA, depth-1);
                }
            }
        }
    }
}
