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
package de.hauschild.gmltracer.tracer.impl;

import java.util.Map;
import java.util.Stack;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.google.common.collect.Maps;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.base.FunctionToken;
import de.hauschild.gmltracer.gml.token.base.NumberToken;
import de.hauschild.gmltracer.gml.token.evaluate.control.ApplyEvaluate;
import de.hauschild.gmltracer.gml.token.geometry.PointToken;
import de.hauschild.gmltracer.tracer.shape.SurfaceFunction;
import de.hauschild.gmltracer.tracer.shape.SurfaceProperties;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class GmlSurfaceFunction implements SurfaceFunction {

    private final FunctionToken surfaceFunction;

    public GmlSurfaceFunction(final FunctionToken theSurfaceFunction) {
        surfaceFunction = theSurfaceFunction;
    }

    @Override
    public SurfaceProperties apply(final int face, final double u, final double v) {
        // prepare function
        final Stack<Token> tokenStack = new Stack<>();
        final Map<String, Token> environment = Maps.newHashMap();
        tokenStack.push(new NumberToken(face));
        tokenStack.push(new NumberToken(u));
        tokenStack.push(new NumberToken(v));
        tokenStack.push(surfaceFunction);
        // evaluate
        new ApplyEvaluate().evaluate(tokenStack, environment);
        // extract properties
        final double phongExponent = ((NumberToken) tokenStack.pop()).getValue();
        final double specularReflectionCoefficient = ((NumberToken) tokenStack.pop()).getValue();
        final double diffuseReflectionCoefficient = ((NumberToken) tokenStack.pop()).getValue();
        final Vector3D color = ((PointToken) tokenStack.pop()).getValue();
        return new SurfaceProperties(color, diffuseReflectionCoefficient,
                        specularReflectionCoefficient, phongExponent);
    }

}
