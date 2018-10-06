/*
 * Copyright (c) 2014, Klaus Hauschild All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.hauschild.gmltracer.tracer.light.tier1;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import de.hauschild.gmltracer.tracer.Vector3DUtils;
import de.hauschild.gmltracer.tracer.impl.Intersection;
import de.hauschild.gmltracer.tracer.impl.Ray;
import de.hauschild.gmltracer.tracer.light.AbstractLight;
import de.hauschild.gmltracer.tracer.shape.Shape;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class DirectionalLight extends AbstractLight {

    private final Vector3D direction;
    private final Vector3D color;

    public DirectionalLight(final Vector3D theDirection, final Vector3D theColor) {
        direction = theDirection;
        color = theColor;
    }

    @Override
    public Vector3D illuminates(final Vector3D surfaceColor, final Intersection intersection) {
        final double dotProduct = Vector3D.dotProduct(intersection.getNormal(), direction.negate());
        final double lightIntensity = Math.max(0.0, dotProduct);
        return Vector3DUtils.multiplyComponentwise(surfaceColor, color)
                        .scalarMultiply(lightIntensity);
    }

    @Override
    public boolean isInShadow(final Shape scene, final Intersection intersection) {
        final Ray shadowRay = new Ray(intersection.getPoint(),
                        intersection.getPoint().add(direction.negate()));
        final Intersection shadowIntersection = scene.intersect(shadowRay, intersection.getShape());
        return shadowIntersection != null;
    }

    @Override
    public String toString() {
        return String.format("{Light, direction: %s; color: %s}", direction, color);
    }

}
