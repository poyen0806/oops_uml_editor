package shape;

public abstract class BasicObject extends Shape {
    protected int x, y, width, height;

    // 將滑鼠的起點與終點轉換為標準的 x, y, width, height
    public BasicObject(int startX, int startY, int endX, int endY) {
        this.x = Math.min(startX, endX);
        this.y = Math.min(startY, endY);
        this.width = Math.abs(startX - endX);
        this.height = Math.abs(startY - endY);
    }
}