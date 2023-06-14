package inteligentes.entities.animal.intelligent;

import java.util.*;

public class BFS {
    private Node[][] searchArea;
    private Queue<Node> queue;
    private Set<Node> visitedSet;
    private Node initialNode;
    private Node finalNode;

    public BFS(int rows, int cols, Node initialNode, Node finalNode) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.queue = new LinkedList<>();
        setNodes();
        this.visitedSet = new HashSet<>();
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
        queue.add(initialNode);
        visitedSet.add(initialNode);

        while (!queue.isEmpty()) {
            System.out.println("Visitados: " + queue);
            Node currentNode = queue.poll();

            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            }

            addAdjacentNodes(currentNode);
        }

        return new ArrayList<>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        Node parent;

        while ((parent = currentNode.getParent()) != null) {
            path.add(0, currentNode);
            currentNode = parent;
        }

        path.add(0, initialNode);
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();

        // Check left neighbor
        if (col - 1 >= 0) {
            Node leftNode = searchArea[row][col - 1];
            if (!leftNode.isBlock() && !visitedSet.contains(leftNode)) {
                queue.add(leftNode);
                visitedSet.add(leftNode);
                leftNode.setParent(currentNode);
            }
        }

        // Check upper neighbor
        if (row - 1 >= 0) {
            Node upperNode = searchArea[row - 1][col];
            if (!upperNode.isBlock() && !visitedSet.contains(upperNode)) {
                queue.add(upperNode);
                visitedSet.add(upperNode);
                upperNode.setParent(currentNode);
            }
        }

        // Check right neighbor
        if (col + 1 < searchArea[0].length) {
            Node rightNode = searchArea[row][col + 1];
            if (!rightNode.isBlock() && !visitedSet.contains(rightNode)) {
                queue.add(rightNode);
                visitedSet.add(rightNode);
                rightNode.setParent(currentNode);
            }
        }

        // Check lower neighbor
        if (row + 1 < searchArea.length) {
            Node lowerNode = searchArea[row + 1][col];
            if (!lowerNode.isBlock() && !visitedSet.contains(lowerNode)) {
                queue.add(lowerNode);
                visitedSet.add(lowerNode);
                lowerNode.setParent(currentNode);
            }
        }
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

    public Queue<Node> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Node> queue) {
        this.queue = queue;
    }

    public Set<Node> getVisitedSet() {
        return visitedSet;
    }

    public void setVisitedSet(Set<Node> visitedSet) {
        this.visitedSet = visitedSet;
    }
}
