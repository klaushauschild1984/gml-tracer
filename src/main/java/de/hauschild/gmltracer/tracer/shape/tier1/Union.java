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
import de.hauschild.gmltracer.tracer.shape.Shape;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class Union extends AbstractShape {

  private final Shape first;
  private final Shape second;

  public Union(final Shape theFirst, final Shape theSecond) {
    super(null);
    first = theFirst;
    second = theSecond;
  }

  @Override
  public Intersection intersect_(final Ray ray) {
    final Intersection firstIntersection = first.intersect(ray);
    final Intersection secondIntersection = second.intersect(ray);
    if (firstIntersection == null && secondIntersection == null) {
      return null;
    }
    if (firstIntersection != null && secondIntersection == null) {
      return firstIntersection;
    }
    if (firstIntersection == null && secondIntersection != null) {
      return secondIntersection;
    }
    final double firstSquareLength = firstIntersection.getPoint().getX() * firstIntersection.getPoint().getX()
        + firstIntersection.getPoint().getY() * firstIntersection.getPoint().getY() + firstIntersection.getPoint().getZ()
        * firstIntersection.getPoint().getZ();
    final double secondSquareLength = secondIntersection.getPoint().getX() * secondIntersection.getPoint().getX()
        + secondIntersection.getPoint().getY() * secondIntersection.getPoint().getY() + secondIntersection.getPoint().getZ()
        * secondIntersection.getPoint().getZ();
    if (firstSquareLength < secondSquareLength) {
      return firstIntersection;
    }
    return secondIntersection;
  }

  @Override
  public Vector3D objectCoordinates(final Vector3D intersection) {
    return null;
  }

  @Override
  public String toString() {
    return String.format("{Union: %s, %s}", first, second);
  }

}
