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
package de.hauschild.gmltracer.gml.token.evaluate.geometricprimitives;

import java.util.Map;
import java.util.Stack;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.base.FunctionToken;
import de.hauschild.gmltracer.gml.token.evaluate.AbstractSingleEvaluate;
import de.hauschild.gmltracer.gml.token.geometry.ShapeToken;
import de.hauschild.gmltracer.tracer.shape.tier1.Sphere;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class SphereEvaluate extends AbstractSingleEvaluate<FunctionToken> {

  @Override
  protected void evaluate(final FunctionToken token, final Stack<Token> tokenStack, final Map<String, Token> environment) {
    final ShapeToken sphereToken = new ShapeToken(new Sphere());
    tokenStack.push(sphereToken);
  }

}
