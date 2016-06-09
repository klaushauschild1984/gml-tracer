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
package de.hauschild.gmltracer.tracer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import de.hauschild.gmltracer.tracer.impl.Ray;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public final class Vector3DUtils {

    private Vector3DUtils() {
    }

    public static Vector3D multiplyComponentwise(final Vector3D a, final Vector3D b) {
        return new Vector3D(a.getX() * b.getX(), a.getY() * b.getY(), a.getZ() * b.getZ());
    }

    public static Ray reflectRay(final Ray ray, final Vector3D normal) {
        // vector of incoming ray
        final Vector3D vector = new Vector3D(ray.getEnd().getX() - ray.getBegin().getX(), ray.getEnd().getY()
                - ray.getBegin().getY(), ray.getEnd().getZ() - ray.getBegin().getZ());
        // dot product of incoming ray vector and surface normal
        final double dotProduct = Vector3D.dotProduct(vector, normal);
        // reflected vector
        final Vector3D reflectedVector = new Vector3D(-vector.getX() + 2 * normal.getX() * dotProduct, -vector.getY() + 2
                * normal.getY() * dotProduct, -vector.getZ() + 2 * normal.getZ() * dotProduct);
        // reflected ray
        return new Ray(ray.getEnd(), ray.getEnd().add(reflectedVector));
    }

}
