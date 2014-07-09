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

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class SurfaceProperties {

  private final Vector3D color;
  private final double diffuseReflectionCoefficient;
  private final double specularReflectionCoefficient;
  private final double phongExponent;

  public SurfaceProperties(final Vector3D theColor, final double theDiffuseReflectionCoefficient,
      final double theSpecularReflectionCoefficient, final double thePhongExponent) {
    color = theColor;
    diffuseReflectionCoefficient = theDiffuseReflectionCoefficient;
    specularReflectionCoefficient = theSpecularReflectionCoefficient;
    phongExponent = thePhongExponent;
  }

  public Vector3D getColor() {
    return color;
  }

  public double getDiffuseReflectionCoefficient() {
    return diffuseReflectionCoefficient;
  }

  public double getPhongExponent() {
    return phongExponent;
  }

  public double getSpecularReflectionCoefficient() {
    return specularReflectionCoefficient;
  }

}
