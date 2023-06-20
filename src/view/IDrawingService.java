package view;

import model.entity.geometry.Shape;

import java.awt.Graphics;

public interface IDrawingService {

    void draw(Graphics g, Shape shape);
}
