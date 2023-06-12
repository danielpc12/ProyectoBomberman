package inteligentes.entities.animal.intelligent;

import java.util.ArrayList;
import java.util.List;

public class HillClimbing {
    private Node initialNode;
    private Node finalNode;
    private Node[][] searchArea;

    public HillClimbing(Node initialNode, Node finalNode, Node[][] searchArea) {
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        setSearchArea(searchArea);
    }

    public List<Node> findPath() {
        List<Node> currentPath = new ArrayList<>();
        currentPath.add(initialNode);

        while (true) {
            List<Node> neighbors = generateNeighbors(currentPath.get(currentPath.size() - 1));

            Node bestNeighbor = null;
            int bestNeighborDistance = Integer.MAX_VALUE;

            for (Node neighbor : neighbors) {
                int distance = calculateManhattanDistance(neighbor, finalNode);
                if (distance < bestNeighborDistance) {
                    bestNeighbor = neighbor;
                    bestNeighborDistance = distance;
                }
            }

            if (bestNeighbor == null || bestNeighborDistance >= calculateManhattanDistance(currentPath.get(currentPath.size() - 1), finalNode)) {
                // No se encontró un vecino mejor o el vecino actual está más cerca del objetivo
                return currentPath;
            } else {
                currentPath.add(bestNeighbor);
            }
        }
    }

    private List<Node> generateNeighbors(Node currentNode) {
        List<Node> neighbors = new ArrayList<>();

        int row = currentNode.getRow();
        int col = currentNode.getCol();

        // Agregar vecinos arriba, abajo, izquierda y derecha
        addNeighbor(neighbors, row - 1, col);
        addNeighbor(neighbors, row + 1, col);
        addNeighbor(neighbors, row, col - 1);
        addNeighbor(neighbors, row, col + 1);

        return neighbors;
    }

    private void addNeighbor(List<Node> neighbors, int row, int col) {
        if (row >= 0 && row < searchArea.length && col >= 0 && col < searchArea[0].length && !searchArea[row][col].isBlock()) {
            neighbors.add(searchArea[row][col]);
        }
    }

    private int calculateManhattanDistance(Node node1, Node node2) {
        int dx = Math.abs(node1.getCol() - node2.getCol());
        int dy = Math.abs(node1.getRow() - node2.getRow());
        return dx + dy;
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
