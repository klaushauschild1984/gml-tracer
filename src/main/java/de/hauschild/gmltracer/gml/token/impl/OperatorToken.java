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
package de.hauschild.gmltracer.gml.token.impl;

import java.util.Map;
import java.util.Stack;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.evaluate.Evaluate;
import de.hauschild.gmltracer.gml.token.evaluate.control.ApplyEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.control.IfEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.LessEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.MulEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.SubEvaluate;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class OperatorToken implements Token {

  private static enum Type {

    // control operators

    IF(new IfEvaluate()), //
    APPLY(new ApplyEvaluate()),

    // number operators
    ADDI(null), //
    ADDF(null), //
    ACOS(null), //
    ASIN(null), //
    CLAMPF(null), //
    COS(null), //
    DIVI(null), //
    DIVF(null), //
    EQI(null), //
    EQF(null), //
    FLOOR(null), //
    FRAC(null), //
    LESSI(new LessEvaluate()), //
    LESSF(new LessEvaluate()), //
    MODI(null), //
    MULI(new MulEvaluate()), //
    MULF(new MulEvaluate()), //
    NEGI(null), //
    NEGF(null), //
    REAL(null), //
    SIN(null), //
    SQRT(null), //
    SUBI(new SubEvaluate()), //
    SUBF(new SubEvaluate()),

    // point operators
    GETX(null), //
    GETY(null), //
    GETZ(null), //
    POINT(null),

    // array operators
    GET(null), //
    LENGTH(null),

    // geometric primitive operators
    SPHERE(null), //
    CUBE(null), //
    CYLINDER(null), //
    CONE(null), //
    PLANE(null),

    // transformation operators
    TRANSLATE(null), //
    SCALE(null), //
    USCALE(null), //
    ROTATEX(null), //
    ROTATEY(null), //
    ROTATEZ(null),

    // light operators
    LIGHT(null), //
    POINTLIGHT(null), //
    SPOTLIGHT(null),

    // constructive solid geometry operators
    UNION(null), //
    INTERSECT(null), //
    DIFFERENCE(null),

    // rendering operator
    RENDER(null),

    ;

    private final Evaluate evaluate;

    private Type(final Evaluate theEvaluate) {
      evaluate = theEvaluate;
    }

    public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
      evaluate.evaluate(tokenStack, environment);
    }

  }

  private final Type type;

  public OperatorToken(final String operator) {
    type = Type.valueOf(operator.toUpperCase());
  }

  @Override
  public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
    type.evaluate(tokenStack, environment);
  }

  @Override
  public String toString() {
    return type.name().toLowerCase();
  }

}
