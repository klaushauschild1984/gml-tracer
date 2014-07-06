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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.testng.annotations.Test;

import de.hauschild.gmltracer.gml.GMLParser.ArrayContext;
import de.hauschild.gmltracer.gml.GMLParser.FunctionContext;
import de.hauschild.gmltracer.gml.GMLParser.TokenContext;
import de.hauschild.gmltracer.gml.GMLParser.TokenListContext;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class GMLParserTest {

  @Test
  public void gmlParserTest() throws Exception {
    final GMLLexer gmlLexer = new GMLLexer(new ANTLRInputStream(getClass().getResourceAsStream("test.gml")));
    final GMLParser gmlParser = new GMLParser(new CommonTokenStream(gmlLexer));
    gmlParser.addErrorListener(new BaseErrorListener() {

      @Override
      public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line,
          final int charPositionInLine, final String message, final RecognitionException exception) {
        throw new IllegalStateException(String.format("Failed to parse in line %s at %s due to %s", line,
            charPositionInLine, message), exception);
      }

    });
    gmlParser.addParseListener(new GMLBaseListener() {

      @Override
      public void exitArray(final ArrayContext ctx) {
        System.out.println("Array: " + ctx);
        System.out.println();
      }

      @Override
      public void exitFunction(final FunctionContext ctx) {
        System.out.println("Function: " + ctx);
        System.out.println();
      }

      @Override
      public void exitToken(final TokenContext ctx) {
        System.out.println("Operator:   " + ctx.Operator());
        System.out.println("Bool:       " + ctx.Bool());
        System.out.println("Identifier: " + ctx.Identifier());
        System.out.println("Binder:     " + ctx.Binder());
        System.out.println("Number:     " + ctx.Number());
        System.out.println("String:     " + ctx.String());
        System.out.println();
      }

    });
    final TokenListContext tokenList = gmlParser.tokenList();
  }
}
