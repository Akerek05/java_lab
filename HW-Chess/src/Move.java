import java.util.Objects;

public class Move {
    private final int originalX, originalY;
    private final int targetX, targetY;
    private final MoveType moveType;

    public Move(int originalX, int originalY, int targetX, int targetY, MoveType moveType) {
        this.originalX = originalX;
        this.originalY = originalY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.moveType = moveType;
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getOriginalY() {
        return originalY;
    }

    public int getX() {
        return targetX;
    }

    public int getY() {
        return targetY;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return originalX == move.originalX &&
               originalY == move.originalY &&
               targetX == move.targetX &&
               targetY == move.targetY &&
               moveType == move.moveType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalX, originalY, targetX, targetY, moveType);
    }

    @Override
    public String toString() {
        return "Move{" +
               "originalX=" + originalX +
               ", originalY=" + originalY +
               ", targetX=" + targetX +
               ", targetY=" + targetY +
               ", moveType=" + moveType +
               '}';
    }

    public enum MoveType {
        MOVE,
        ATTACK
    }
}
