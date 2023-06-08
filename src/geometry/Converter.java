package geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    public static Shape StringToShape(String str){
        if(str.contains("Point"))
            return StrToPoint(str);
        if (str.contains("Line"))
            return StrToLine(str);
        if (str.contains("Rectangle"))
            return StrToRectangle(str);
        if (str.contains("Circle"))
            return StrToCircle(str);
        if(str.contains("Donut"))
            return StrToDonut(str);
        if (str.contains("Hexagon"))
            return StrToHexagon(str);

        return null;
    }

    public static String ShapeToString(Shape shape){
        switch (shape.getShapeType()){
            case POINT:
                return PointToStr((Point) shape);
            case LINE:
                return LineToStr((Line) shape);
            case RECTANGLE:
                return RectangleToStr((Rectangle) shape);
            case CIRCLE:
                return CircleToStr((Circle) shape);
            case DONUT:
                return DonutToStr((Donut) shape);
            case HEXAGON:
                return HexagonToStr((HexagonAdapter) shape);
        }
        return null;
    }

    private static String PointToStr(Point point){
        String id = point.getId();
        String coordinates = CoordinatesToStr(point);
        String color = ColorToStr(point.getColor());
        return id + ", Point " + coordinates + ", color: " + color;
    }

    private static Shape StrToPoint(String str){
        String id = str.substring(0, str.indexOf(","));
        Point point = extractCoordinates(str).get(0);
        Color color = extractColors(str).get(0);

        return new Point(id, point.getX(), point.getY(), color);
    }

    private static String LineToStr(Line line){

        String id = line.getId();
        String p1_cord = CoordinatesToStr(line.getStartPoint());
        String p2_cord = CoordinatesToStr(line.getEndPoint());
        String color = ColorToStr(line.getColor());

        return id + ", Line " + p1_cord + p2_cord + ", color: " + color;
    }

    private static Shape StrToLine(String str){
        ArrayList<Point> cords = extractCoordinates(str);
        String id = str.substring(0, str.indexOf(","));
        ArrayList<Color> colors = extractColors(str);
        return new Line(id, cords.get(0), cords.get(1), colors.get(0));
    }
    private static String RectangleToStr(Rectangle rect){
        String id = rect.getId();
        String point =  CoordinatesToStr(rect.getUpperLeftPoint());
        String innerColor = ColorToStr(rect.getInnerColor());
        String outerColor = ColorToStr(rect.getOuterColor());

        return id + ", Rectangle " + point + ", height: " + rect.getHeight() +
                ", width: " + rect.getWidth() + ", inner color: " + innerColor +
                ", outerColor: " + outerColor ;
    }

    private static Shape StrToRectangle(String str){
        String id = str.substring(0, str.indexOf(","));
        Point p = extractCoordinates(str).get(0);
        ArrayList<Color> colors = extractColors(str);
        int height = Integer.parseInt(extractProperty(str, "height"));
        int width = Integer.parseInt(extractProperty(str, "width"));

        return new Rectangle(id, p,width,height,colors.get(0),colors.get(1));
    }

    private static String CircleToStr(Circle circle){
        String id = circle.getId();
        String point =  CoordinatesToStr(circle.getCenter());
        String innerColor = ColorToStr(circle.getInnerColor());
        String outerColor = ColorToStr(circle.getOuterColor());

        return id + ", Circle " + point + ", radius: " + circle.getRadius() + ", inner color: " + innerColor +
                ", outerColor: " + outerColor ;
    }

    private static Shape StrToCircle(String str){
        String id = str.substring(0, str.indexOf(","));
        Point center = extractCoordinates(str).get(0);
        ArrayList<Color> colors = extractColors(str);
        int radius = Integer.parseInt(extractProperty(str, "radius"));
        return new Circle(id, center, radius, colors.get(0), colors.get(1));

    }

    private static String DonutToStr(Donut donut){
        String id = donut.getId();
        String point =  CoordinatesToStr(donut.getCenter());
        String innerColor = ColorToStr(donut.getInnerColor());
        String outerColor = ColorToStr(donut.getOuterColor());

        return id + ", Donut " + point + ", inner radius: " + donut.getInnerRadius() + ", outer radius:" +
                donut.getRadius() +", inner color: " + innerColor + ", outer color: " + outerColor ;
    }

    private static Shape StrToDonut(String str){
        String id = str.substring(0, str.indexOf(","));
        Point center = extractCoordinates(str).get(0);
        int innerRadius = Integer.parseInt(extractProperty(str,"inner radius"));
        int outerRadius = Integer.parseInt(extractProperty(str, "outer radius"));
        ArrayList<Color> colors = extractColors(str);
        return new Donut(id, center, innerRadius, outerRadius, colors.get(0), colors.get(1));
    }

    private static String HexagonToStr(HexagonAdapter hexagon){
        String id = hexagon.getId();
        Point p = new Point(hexagon.getX(), hexagon.getY());
        String point = CoordinatesToStr(p);
        String innerColor = ColorToStr(hexagon.getInnerColor());
        String outerColor = ColorToStr(hexagon.getOuterColor());

        return id + ", Hexagon " + point + ", radius: " + hexagon.getR() + ", inner color: "
                + innerColor + ", outer color: " + outerColor ;
    }

    private static Shape StrToHexagon(String str){
        String id = str.substring(0, str.indexOf(","));
        Point p = extractCoordinates(str).get(0);
        int radius = Integer.parseInt(extractProperty(str, "radius"));
        ArrayList<Color> colors = extractColors(str);

        return new HexagonAdapter(id, p.getX(), p.getY(), radius, colors.get(0), colors.get(1));
    }



    private static String extractProperty(String str, String property){
        String regex = property + ":\\s*(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }


    private static String CoordinatesToStr(Point p){
        return "(" + p.getX()+","+p.getY()+")";
    }

    private static Point StrToCoordinate(String str){
        String[] numbers = str.replaceAll("\\D+", " ").trim().split("\\s+");
        int x = Integer.parseInt(numbers[0]);
        int y = Integer.parseInt(numbers[1]);
        return new Point(x, y);
    }

    private static String ColorToStr(Color color){
        return "[" + color.getRed()+ ", "+ color.getGreen()+ ", " + color.getBlue() +"]";
    }

    private static Color StrToColor(String str){
        String[] numbers = str.replaceAll("\\D+", " ").trim().split("\\s+");
        int red = Integer.parseInt(numbers[0]);
        int green = Integer.parseInt(numbers[1]);
        int blue = Integer.parseInt(numbers[2]);
        return new Color(red,green,blue);
    }
    private static ArrayList<Point> extractCoordinates(String str) {
        Pattern pointPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher matcher = pointPattern.matcher(str);
        ArrayList<Point> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(StrToCoordinate(str.substring(matcher.start(), matcher.end())));
        }
        return result;
    }

    private static ArrayList<Color> extractColors(String str) {
        Pattern colorPattern = Pattern.compile("color:\\s*\\[(\\d+),\\s*(\\d+),\\s*(\\d+)\\]");
        Matcher matcher = colorPattern.matcher(str);
        ArrayList<Color> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(StrToColor(str.substring(matcher.start(), matcher.end())));
        }
        return result;
    }


}
