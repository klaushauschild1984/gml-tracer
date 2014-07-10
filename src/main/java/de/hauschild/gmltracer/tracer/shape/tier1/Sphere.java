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
package de.hauschild.gmltracer.tracer.shape.tier1;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import de.hauschild.gmltracer.tracer.impl.Intersection;
import de.hauschild.gmltracer.tracer.impl.Ray;
import de.hauschild.gmltracer.tracer.shape.AbstractShape;
import de.hauschild.gmltracer.tracer.shape.SurfaceFunction;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class Sphere extends AbstractShape {

  public Sphere(final SurfaceFunction surfaceFunction) {
    super(surfaceFunction);
  }

  @Override
  public Intersection intersect_(final Ray ray) {
    final Vector3D position = worldToObject(ray.getBegin());
    final Vector3D direction = worldToObject(ray.getEnd()).subtract(position).normalize();
    final double a = 1;
    final double b = 2.0 * Vector3D.dotProduct(direction, position);
    final double c = Vector3D.dotProduct(position, position) - 1;
    final double d = b * b - 4 * a * c;
    if (d < 0.0) {
      return null;
    }
    final double sqrtD = Math.sqrt(d);
    double q;
    if (b < 0) {
      q = (-b - sqrtD) / 2.0;
    } else {
      q = (-b + sqrtD) / 2.0;
    }
    double t0 = q / a;
    double t1 = c / q;
    if (t0 > t1) {
      final double temp = t0;
      t0 = t1;
      t1 = temp;
    }
    if (t1 < 0) {
      return null;
    }
    double t;
    if (t0 < 0) {
      t = t1;
    } else {
      t = t0;
    }
    final Vector3D hitPoint = direction.scalarMultiply(t).add(position);
    final Vector3D point = objectToWorld(hitPoint);
    return new Intersection(point, this, hitPoint, getSurfaceProperties(point));
  }

  @Override
  public Vector3D objectCoordinates(final Vector3D intersection) {
    final double u = Math.atan(Math.toRadians(intersection.getX() / intersection.getZ()));
    final double v = (intersection.getY() + 1.0) / 2.0;
    return new Vector3D(0.0, u, v);
  }

  @Override
  public String toString() {
    return String.format("{Sphere}");
  }

}
