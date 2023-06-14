package inteligentes.entities.animal.intelligent;

import java.util.*;

public class AStar {
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public AStar(int rows, int cols, Node initialNode, Node finalNode) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        setNodes();
        this.closedSet = new HashSet<>();
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public void setBlocks(int[][] blocksArray, int count) {
        for (int i = 0; i < count; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    public List<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            System.out.println("openList: " + openList);
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            assert currentNode != null;
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentLeftNode(currentNode);
        addAdjacentUpperNode(currentNode);
        addAdjacentRightNode(currentNode);
        addAdjacentLowerNode(currentNode);
    }

    private void addAdjacentLeftNode(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row);
        }
    }

    private void addAdjacentUpperNode(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (row - 1 >= 0) {
            checkNode(currentNode, col, row - 1);
        }
    }

    private void addAdjacentRightNode(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (col + 1 < searchArea[0].length) {
            checkNode(currentNode, col + 1, row);
        }
    }

    private void addAdjacentLowerNode(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (row + 1 < searchArea.length) {
            checkNode(currentNode, col, row + 1);
        }
    }

    private void checkNode(Node currentNode, int col, int row) {
        Node adjacentNode = searchArea[row][col];
        if (!adjacentNode.isBlock() && !closedSet.contains(adjacentNode)) {
            if (!openList.contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode);
                openList.add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode);
                if (changed) {
                    openList.remove(adjacentNode);
                    openList.add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }
}
