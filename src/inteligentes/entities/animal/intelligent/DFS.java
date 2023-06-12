package inteligentes.entities.animal.intelligent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {
    private Node[][] searchArea;
    private Node initialNode;
    private Node finalNode;
    private boolean[][] visited;

    public DFS(int rows, int cols, Node initialNode, Node finalNode) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        setNodes();
        this.visited = new boolean[rows][cols];
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
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();

            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            }

            if (!isVisited(currentNode)) {
                setVisited(currentNode);

                List<Node> adjacentNodes = getAdjacentNodes(currentNode);
                for (Node adjacentNode : adjacentNodes) {
                    if (!isVisited(adjacentNode)) {
                        adjacentNode.setParent(currentNode);
                        stack.push(adjacentNode);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private List<Node> getAdjacentNodes(Node currentNode) {
        List<Node> adjacentNodes = new ArrayList<>();
        int row = currentNode.getRow();
        int col = currentNode.getCol();

        if (row - 1 >= 0) {
            Node upperNode = searchArea[row - 1][col];
            if (!upperNode.isBlock()) {
                adjacentNodes.add(upperNode);
            }
        }

        if (row + 1 < searchArea.length) {
            Node lowerNode = searchArea[row + 1][col];
            if (!lowerNode.isBlock()) {
                adjacentNodes.add(lowerNode);
            }
        }

        if (col - 1 >= 0) {
            Node leftNode = searchArea[row][col - 1];
            if (!leftNode.isBlock()) {
                adjacentNodes.add(leftNode);
            }
        }

        if (col + 1 < searchArea[0].length) {
            Node rightNode = searchArea[row][col + 1];
            if (!rightNode.isBlock()) {
                adjacentNodes.add(rightNode);
            }
        }

        return adjacentNodes;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isVisited(Node node) {
        return visited[node.getRow()][node.getCol()];
    }

    private void setVisited(Node node) {
        visited[node.getRow()][node.getCol()] = true;
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

    public boolean[][] getVisited() {
        return visited;
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }
}
