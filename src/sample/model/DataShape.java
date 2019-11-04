package sample.model;
//todo
//add an image path
public class DataShape {
    private String name;
    private String className;
    private String tagName;
    private double x;
    private double y;
    private double height;
    private double width;
    private String color;

    public DataShape(String className,String name, double x, double y, double height, double width, String color,String tagName) {
        this.className = className;
        this.name = name;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public String getColor() {
        return color;
    }
}
