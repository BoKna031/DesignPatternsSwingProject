package model.service;

import model.entity.Converter;
import model.entity.LogType;
import model.entity.geometry.Shape;

public class LogService {

    private static String separator = " - ";
    public static String add(Shape shape){
        return Converter.ShapeToString(shape) + separator + LogType.ADD;
    }

    public static String remove(String shapeId){
        return log(shapeId, LogType.REMOVE);
    }
    public static String modify(Shape oldShape, Shape newShape){
        return Converter.ShapeToString(oldShape) + ", " + LogType.MODIFY_TO + Converter.ShapeToString(newShape);
    }
    public static String select(String shapeId){
        return log(shapeId, LogType.SELECT);
    }

    public static String deselect(String shapeId){
        return log(shapeId, LogType.DESELECT);
    }

    public static String toFront(String shapeId){
        return log(shapeId, LogType.TO_FRONT);
    }
    public static String toBack(String shapeId){
        return log(shapeId, LogType.TO_BACK);
    }

    public static String bringFront(String shapeId){
        return log(shapeId, LogType.BRING_FRONT);
    }
    public static String bringBack(String shapeId){
        return log(shapeId, LogType.BRING_BACK);
    }

    private static String log(String shapeId, LogType type){
        return shapeId + separator + type;
    }
}
