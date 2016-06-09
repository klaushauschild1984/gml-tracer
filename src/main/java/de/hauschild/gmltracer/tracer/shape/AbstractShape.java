/*
 * Copyright (c) 2014, Klaus Hauschild
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions and
 *     the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *     and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package de.hauschild.gmltracer.tracer.shape;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import de.hauschild.gmltracer.tracer.impl.Intersection;
import de.hauschild.gmltracer.tracer.impl.Ray;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public abstract class AbstractShape implements Shape {

    private final SurfaceFunction surfaceFunction;
    private RealMatrix            transformation        = MatrixUtils.createRealIdentityMatrix(4);
    private RealMatrix            inverseTransformation = MatrixUtils.inverse(transformation);

    protected AbstractShape(final SurfaceFunction theSurfaceFunction) {
        surfaceFunction = theSurfaceFunction;
    }

    private SurfaceFunction getSurfaceFunction() {
        return surfaceFunction;
    }

    @Override
    public final Intersection intersect(final Ray ray) {
        return intersect(ray, null);
    }

    @Override
    public final Intersection intersect(final Ray ray, final Shape shapeToIgnore) {
        if (shapeToIgnore != null && shapeToIgnore == this) {
            return null;
        }
        return intersectAfterIgnore(ray);
    }

    protected abstract Vector3D objectCoordinates(final Vector3D intersection);

    @Override
    public void translate(final double x, final double y, final double z) {
        final RealMatrix translation = MatrixUtils.createRealMatrix(new double[][] { { 1, 0, 0, x, }, { 0, 1, 0, y, },
                { 0, 0, 1, z, }, { 0, 0, 0, 1, }, });
        transformBy(translation);
    }

    protected SurfaceProperties getSurfaceProperties(final Vector3D intersection) {
        final Vector3D objectCoordinates = objectCoordinates(intersection);
        return getSurfaceFunction().apply((int) objectCoordinates.getX(),
                objectCoordinates.getY(), objectCoordinates.getZ());
    }

    protected abstract Intersection intersectAfterIgnore(final Ray ray);

    protected Vector3D objectToWorld(final Vector3D point) {
        return transform(transformation, point);
    }

    protected Vector3D worldToObject(final Vector3D point) {
        return transform(inverseTransformation, point);
    }

    private Vector3D transform(final RealMatrix matrix, final Vector3D point) {
        final double[] operated = matrix.operate(new double[] { point.getX(), point.getY(), point.getZ(), 1.0, });
        return new Vector3D(operated[0], operated[1], operated[2]);
    }

    private void transformBy(final RealMatrix matrix) {
        transformation = transformation.multiply(matrix);
        inverseTransformation = MatrixUtils.inverse(transformation);
    }

}
