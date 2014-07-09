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
package de.hauschild.gmltracer.gml.token.base;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.collect.Maps;

import de.hauschild.gmltracer.gml.token.Token;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class FunctionToken extends AbstractContainerToken {

  private Map<String, Token> environment;

  public FunctionToken(final List<Token> theTokens) {
    super(theTokens);
  }

  @Override
  public void evaluate(final Stack<Token> theTokenStack, final Map<String, Token> theEnvironment) {
    super.evaluate(theTokenStack, theEnvironment);
    environment = Maps.newHashMap(theEnvironment);
  }

  public Map<String, Token> getEnvironment() {
    return environment;
  }

  @Override
  protected String toStringBegin() {
    return "{";
  }

  @Override
  protected String toStringEnd() {
    return "}";
  }

  @Override
  protected String toStringSeparator() {
    return " ";
  }

}
