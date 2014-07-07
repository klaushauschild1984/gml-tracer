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
package de.hauschild.gmltracer.gml;

import java.util.List;

import com.google.common.collect.Lists;

import de.hauschild.gmltracer.gml.GMLParser.ArrayContext;
import de.hauschild.gmltracer.gml.GMLParser.BinderContext;
import de.hauschild.gmltracer.gml.GMLParser.BoolContext;
import de.hauschild.gmltracer.gml.GMLParser.FunctionContext;
import de.hauschild.gmltracer.gml.GMLParser.IdentifierContext;
import de.hauschild.gmltracer.gml.GMLParser.NumberContext;
import de.hauschild.gmltracer.gml.GMLParser.OperatorContext;
import de.hauschild.gmltracer.gml.GMLParser.StringContext;
import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.base.ArrayToken;
import de.hauschild.gmltracer.gml.token.base.BinderToken;
import de.hauschild.gmltracer.gml.token.base.BooleanToken;
import de.hauschild.gmltracer.gml.token.base.FunctionToken;
import de.hauschild.gmltracer.gml.token.base.IdentifierToken;
import de.hauschild.gmltracer.gml.token.base.NumberToken;
import de.hauschild.gmltracer.gml.token.base.OperatorToken;
import de.hauschild.gmltracer.gml.token.base.StringToken;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class GMLExtractor {

  private final GMLParser gmlParser;

  public GMLExtractor(final GMLParser theGmlParser) {
    gmlParser = theGmlParser;
  }

  public List<Token> extract() {
    return new GMLBaseVisitor<List<Token>>() {

      @Override
      public List<Token> visitArray(final ArrayContext ctx) {
        final List<Token> arrayContent = visitChildren(ctx);
        final ArrayToken arrayToken = new ArrayToken(arrayContent);
        final List<Token> result = defaultResult();
        result.add(arrayToken);
        return result;
      };

      @Override
      public List<Token> visitBinder(final BinderContext ctx) {
        final List<Token> result = defaultResult();
        final BinderToken binderToken = new BinderToken(ctx.reference.getText());
        result.add(binderToken);
        return result;
      };

      @Override
      public List<Token> visitBool(final BoolContext ctx) {
        final List<Token> result = defaultResult();
        final BooleanToken booleanToken = new BooleanToken(ctx.getText());
        result.add(booleanToken);
        return result;
      };

      @Override
      public List<Token> visitFunction(final FunctionContext ctx) {
        final List<Token> functionContent = visitChildren(ctx);
        final FunctionToken functionToken = new FunctionToken(functionContent);
        final List<Token> result = defaultResult();
        result.add(functionToken);
        return result;
      };

      @Override
      public List<Token> visitIdentifier(final IdentifierContext ctx) {
        final List<Token> result = defaultResult();
        final IdentifierToken identifierToken = new IdentifierToken(ctx.getText());
        result.add(identifierToken);
        return result;
      };

      @Override
      public List<Token> visitNumber(final NumberContext ctx) {
        final List<Token> result = defaultResult();
        final NumberToken numberToken = new NumberToken(ctx.value.getText());
        result.add(numberToken);
        return result;
      };

      @Override
      public List<Token> visitOperator(final OperatorContext ctx) {
        final List<Token> result = defaultResult();
        final OperatorToken operatorToken = new OperatorToken(ctx.getText());
        result.add(operatorToken);
        return result;
      };

      @Override
      public List<Token> visitString(final StringContext ctx) {
        final List<Token> result = defaultResult();
        final StringToken stringToken = new StringToken(ctx.value.getText());
        result.add(stringToken);
        return result;
      };

      @Override
      protected List<Token> aggregateResult(final List<Token> aggregate, final List<Token> nextResult) {
        aggregate.addAll(nextResult);
        return aggregate;
      };

      @Override
      protected List<Token> defaultResult() {
        return Lists.newArrayList();
      };

    }.visit(gmlParser.tokenList());
  }
}
