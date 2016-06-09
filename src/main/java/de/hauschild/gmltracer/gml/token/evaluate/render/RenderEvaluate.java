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
package de.hauschild.gmltracer.gml.token.evaluate.render;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.collect.Lists;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.base.ArrayToken;
import de.hauschild.gmltracer.gml.token.base.NumberToken;
import de.hauschild.gmltracer.gml.token.base.StringToken;
import de.hauschild.gmltracer.gml.token.evaluate.Evaluate;
import de.hauschild.gmltracer.gml.token.geometry.PointToken;
import de.hauschild.gmltracer.gml.token.geometry.ShapeToken;
import de.hauschild.gmltracer.gml.token.light.LightToken;
import de.hauschild.gmltracer.tracer.impl.GmlRaytracer;
import de.hauschild.gmltracer.tracer.light.Light;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class RenderEvaluate implements Evaluate {

    @Override
    public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
        final StringToken fileName = (StringToken) tokenStack.pop();
        final NumberToken height = (NumberToken) tokenStack.pop();
        final NumberToken width = (NumberToken) tokenStack.pop();
        final NumberToken fieldOfView = (NumberToken) tokenStack.pop();
        final NumberToken depth = (NumberToken) tokenStack.pop();
        final ShapeToken scene = (ShapeToken) tokenStack.pop();
        final ArrayToken lightArray = (ArrayToken) tokenStack.pop();
        final List<Light> lights = Lists.newArrayList();
        for (final Token light : lightArray.getValue()) {
            if (!(light instanceof LightToken)) {
                throw new ClassCastException(String.format("[%s] is [%s] expected was [%s]", light, light.getClass(),
                        LightToken.class));
            }
            lights.add(((LightToken) light).getValue());
        }
        final PointToken ambientLightIntensity = (PointToken) tokenStack.pop();
        new GmlRaytracer().render(ambientLightIntensity.getValue(), lights, scene.getValue(), depth.getValue().intValue(),
                fieldOfView.getValue(), width.getValue().intValue(), height.getValue().intValue(), fileName.getValue());
    }

}
