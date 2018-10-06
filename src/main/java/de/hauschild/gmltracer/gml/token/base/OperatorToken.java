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
package de.hauschild.gmltracer.gml.token.base;

import java.util.Map;
import java.util.Stack;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.evaluate.Evaluate;
import de.hauschild.gmltracer.gml.token.evaluate.UnsupportedEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.constructive.UnionEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.control.ApplyEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.control.IfEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.geometricprimitives.PlaneEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.geometricprimitives.SphereEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.light.DirectionalLightEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.LessEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.MulEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.number.SubEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.point.PointEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.render.RenderEvaluate;
import de.hauschild.gmltracer.gml.token.evaluate.transformations.TranslateEvaluate;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class OperatorToken implements Token {

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

    private enum Type {

        // control operators

        IF(new IfEvaluate()), //
        APPLY(new ApplyEvaluate()),

        // number operators
        ADDI(new UnsupportedEvaluate()), //
        ADDF(new UnsupportedEvaluate()), //
        ACOS(new UnsupportedEvaluate()), //
        ASIN(new UnsupportedEvaluate()), //
        CLAMPF(new UnsupportedEvaluate()), //
        COS(new UnsupportedEvaluate()), //
        DIVI(new UnsupportedEvaluate()), //
        DIVF(new UnsupportedEvaluate()), //
        EQI(new UnsupportedEvaluate()), //
        EQF(new UnsupportedEvaluate()), //
        FLOOR(new UnsupportedEvaluate()), //
        FRAC(new UnsupportedEvaluate()), //
        LESSI(new LessEvaluate()), //
        LESSF(new LessEvaluate()), //
        MODI(new UnsupportedEvaluate()), //
        MULI(new MulEvaluate()), //
        MULF(new MulEvaluate()), //
        NEGI(new UnsupportedEvaluate()), //
        NEGF(new UnsupportedEvaluate()), //
        REAL(new UnsupportedEvaluate()), //
        SIN(new UnsupportedEvaluate()), //
        SQRT(new UnsupportedEvaluate()), //
        SUBI(new SubEvaluate()), //
        SUBF(new SubEvaluate()),

        // point operators
        GETX(new UnsupportedEvaluate()), //
        GETY(new UnsupportedEvaluate()), //
        GETZ(new UnsupportedEvaluate()), //
        POINT(new PointEvaluate()),

        // array operators
        GET(new UnsupportedEvaluate()), //
        LENGTH(new UnsupportedEvaluate()),

        // geometric primitive operators
        SPHERE(new SphereEvaluate()), //
        CUBE(new UnsupportedEvaluate()), //
        CYLINDER(new UnsupportedEvaluate()), //
        CONE(new UnsupportedEvaluate()), //
        PLANE(new PlaneEvaluate()),

        // transformation operators
        TRANSLATE(new TranslateEvaluate()), //
        SCALE(new UnsupportedEvaluate()), //
        USCALE(new UnsupportedEvaluate()), //
        ROTATEX(new UnsupportedEvaluate()), //
        ROTATEY(new UnsupportedEvaluate()), //
        ROTATEZ(new UnsupportedEvaluate()),

        // light operators
        LIGHT(new DirectionalLightEvaluate()), //
        POINTLIGHT(new UnsupportedEvaluate()), //
        SPOTLIGHT(new UnsupportedEvaluate()),

        // constructive solid geometry operators
        UNION(new UnionEvaluate()), //
        INTERSECT(new UnsupportedEvaluate()), //
        DIFFERENCE(new UnsupportedEvaluate()),

        // rendering operator
        RENDER(new RenderEvaluate()),

        ;

        private final Evaluate evaluate;

        Type(final Evaluate theEvaluate) {
            evaluate = theEvaluate;
        }

        public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
            if (evaluate instanceof UnsupportedEvaluate) {
                throw new UnsupportedOperationException(
                                String.format("[%s] not implemented!", this));
            }
            evaluate.evaluate(tokenStack, environment);
        }

    }

}
