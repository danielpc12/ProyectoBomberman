package inteligentes.entities.animal.intelligent;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BeamSearch {
    private int beamWidth;

    public BeamSearch(int beamWidth) {
        this.beamWidth = beamWidth;
    }

    public List<Node> search(Node startNode, Node finalNode, List<Node> allNodes) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(new NodeComparator());
        List<Node> closedSet = new ArrayList<>();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            List<Node> beam = new ArrayList<>();

            for (int i = 0; i < beamWidth && !openSet.isEmpty(); i++) {
                beam.add(openSet.poll());
            }

            for (Node currentNode : beam) {
                closedSet.add(currentNode);

                if (currentNode.equals(finalNode)) {
                    return reconstructPath(currentNode);
                }

                List<Node> neighbors = getNeighbors(currentNode, allNodes);
                for (Node neighbor : neighbors) {
                    if (closedSet.contains(neighbor)) {
                        continue;
                    }

                    int tentativeG = currentNode.getG() + 1;
                    int heuristicCost = calculateManhattanDistance(neighbor, finalNode);
                    int f = tentativeG + heuristicCost;

                    if (!openSet.contains(neighbor)) {
                        neighbor.setParent(currentNode);
                        neighbor.setG(tentativeG);
                        neighbor.setH(heuristicCost);
                        neighbor.setF(f);
                        openSet.add(neighbor);
                    } else if (f < neighbor.getF()) {
                        neighbor.setParent(currentNode);
                        neighbor.setG(tentativeG);
                        neighbor.setH(heuristicCost);
                        neighbor.setF(f);
                    }
                }
            }
        }

        // No se encontró un camino
        return new ArrayList<>();
    }

    private List<Node> reconstructPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(0, currentNode);
            currentNode = currentNode.getParent();
        }
        return path;
    }

    private List<Node> getNeighbors(Node node, List<Node> allNodes) {
        List<Node> neighbors = new ArrayList<>();
        int row = node.getRow();
        int col = node.getCol();

        // Agregar vecinos arriba, abajo, izquierda y derecha
        addNeighbor(neighbors, row - 1, col, allNodes);
        addNeighbor(neighbors, row + 1, col, allNodes);
        addNeighbor(neighbors, row, col - 1, allNodes);
        addNeighbor(neighbors, row, col + 1, allNodes);

        return neighbors;
    }

    private void addNeighbor(List<Node> neighbors, int row, int col, List<Node> allNodes) {
        for (Node node : allNodes) {
            if (node.getRow() == row && node.getCol() == col && !node.isBlock()) {
                neighbors.add(node);
                break;
            }
        }
    }

    private int calculateManhattanDistance(Node node1, Node node2) {
        int dx = Math.abs(node1.getCol() - node2.getCol());
        int dy = Math.abs(node1.getRow() - node2.getRow());
        return dx + dy;
    }

    private class NodeComparator implements java.util.Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.getF() - node2.getF();
        }
    }
}
