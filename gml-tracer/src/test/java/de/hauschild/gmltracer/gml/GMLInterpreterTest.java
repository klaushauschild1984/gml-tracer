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

import java.io.IOException;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.hauschild.gmltracer.gml.token.Token;
import de.hauschild.gmltracer.gml.token.base.NumberToken;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class GMLInterpreterTest {

  @Test
  public void twelveFactorial() throws IOException {
    final GMLLexer gmlLexer = new GMLLexer(new ANTLRInputStream(getClass().getResourceAsStream("fact.gml")));
    final GMLParser gmlParser = new GMLParser(new CommonTokenStream(gmlLexer));
    final GMLExtractor gmlExtractor = new GMLExtractor(gmlParser);
    final GMLInterpreter gmlInterpreter = new GMLInterpreter(gmlExtractor);
    final Stack<Token> tokenStack = gmlInterpreter.interpret();
    Assert.assertEquals(tokenStack.size(), 1);
    final NumberToken result = (NumberToken) tokenStack.pop();
    Assert.assertEquals(result.getValue(), 479001600d);
  }

}
