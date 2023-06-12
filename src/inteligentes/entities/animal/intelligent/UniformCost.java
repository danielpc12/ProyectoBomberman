package inteligentes.entities.animal.intelligent;

import java.util.*;

public class UniformCost {
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public UniformCost(int rows, int cols, Node initialNode, Node finalNode) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getG));
        setNodes();
        this.closedSet = new HashSet<>();
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
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
        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(0, currentNode);
            currentNode = currentNode.getParent();
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < searchArea.length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lowerRow);
            }
            if (col + 1 < searchArea[0].length) {
                checkNode(currentNode, col + 1, lowerRow);
            }
            checkNode(currentNode, col, lowerRow);
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row);
        }
        if (col + 1 < searchArea[0].length) {
            checkNode(currentNode, col + 1, row);
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow);
            }
            if (col + 1 < searchArea[0].length) {
                checkNode(currentNode, col + 1, upperRow);
            }
            checkNode(currentNode, col, upperRow);
        }
    }

    private void checkNode(Node currentNode, int col, int row) {
        Node adjacentNode = searchArea[row][col];
        if (!adjacentNode.isBlock() && !closedSet.contains(adjacentNode)) {
            int gCost = currentNode.getG() + getCost(currentNode, adjacentNode);
            if (!openList.contains(adjacentNode) || gCost < adjacentNode.getG()) {
                adjacentNode.setParent(currentNode);
                adjacentNode.setG(gCost);
                if (!openList.contains(adjacentNode)) {
                    openList.add(adjacentNode);
                }
            }
        }
    }


    private int getCost(Node currentNode, Node adjacentNode) {
        int colDiff = Math.abs(currentNode.getCol() - adjacentNode.getCol());
        int rowDiff = Math.abs(currentNode.getRow() - adjacentNode.getRow());
        return colDiff + rowDiff;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
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
}
