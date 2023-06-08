package inteligentes.entities.animal.intelligent;

import java.util.*;

import inteligentes.BombermanGame;

public class UniformCost {
    private PriorityQueue<Node> queue;
    private Set<Node> visitedNodes;
    private Node initialNode;
    private Node finalNode;

    public UniformCost(Node initialNode, Node finalNode) {
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Node::getG));
        this.visitedNodes = new HashSet<>();
        setInitialNode(initialNode);
        setFinalNode(finalNode);
    }

    public List<Node> findPath() {
        queue.add(initialNode);
        visitedNodes.add(initialNode);

        while (!queue.isEmpty()) {
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

        // Check right node
        int rightCol = col + 1;
        if (rightCol < BombermanGame.WIDTH) {
            Node rightNode = new Node(row, rightCol);
            if (!rightNode.isBlock() && !visitedNodes.contains(rightNode)) {
                visitedNodes.add(rightNode);
                rightNode.setParent(currentNode);
                rightNode.setG(currentNode.getG() + 1);
                queue.add(rightNode);
            }
        }

        // Check left node
        int leftCol = col - 1;
        if (leftCol >= 0) {
            Node leftNode = new Node(row, leftCol);
            if (!leftNode.isBlock() && !visitedNodes.contains(leftNode)) {
                visitedNodes.add(leftNode);
                leftNode.setParent(currentNode);
                leftNode.setG(currentNode.getG() + 1);
                queue.add(leftNode);
            }
        }

        // Check lower node
        int lowerRow = row + 1;
        if (lowerRow < BombermanGame.HEIGHT) {
            Node lowerNode = new Node(lowerRow, col);
            if (!lowerNode.isBlock() && !visitedNodes.contains(lowerNode)) {
                visitedNodes.add(lowerNode);
                lowerNode.setParent(currentNode);
                lowerNode.setG(currentNode.getG() + 1);
                queue.add(lowerNode);
            }
        }

        // Check upper node
        int upperRow = row - 1;
        if (upperRow >= 0) {
            Node upperNode = new Node(upperRow, col);
            if (!upperNode.isBlock() && !visitedNodes.contains(upperNode)) {
                visitedNodes.add(upperNode);
                upperNode.setParent(currentNode);
                upperNode.setG(currentNode.getG() + 1);
                queue.add(upperNode);
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
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
}
