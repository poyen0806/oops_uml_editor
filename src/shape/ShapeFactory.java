package shape;

@FunctionalInterface
public interface ShapeFactory {
    BasicObject create(int startX, int startY, int endX, int endY);
}