package model.repository;

import model.entity.geometry.Shape;

import java.util.Collection;

public interface IShapeRepository extends CRUD<String, Shape> {

    Collection<Shape> getAll();

    void clear();

}
