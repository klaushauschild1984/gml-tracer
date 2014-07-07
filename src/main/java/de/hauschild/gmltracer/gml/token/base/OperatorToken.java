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

import java.util.Map;
import java.util.Stack;

import de.hauschild.gmltracer.gml.token.Token;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class OperatorToken implements Token {

  private static abstract class DoubleEvaluate<FIRST extends Token, SECOND extends Token> implements Evaluate {

    @Override
    @SuppressWarnings("unchecked")
    public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
      final SECOND secondToken = (SECOND) tokenStack.pop();
      final FIRST firstToken = (FIRST) tokenStack.pop();
      evaluate(firstToken, secondToken, tokenStack, environment);
    }

    protected abstract void evaluate(FIRST firstToken, SECOND secondToken, Stack<Token> tokenStack,
        Map<String, Token> environment);

  }

  private static interface Evaluate {

    void evaluate(Stack<Token> tokenStack, Map<String, Token> environment);

  }

  private static abstract class SingleEvaluate<T extends Token> implements Evaluate {

    @Override
    @SuppressWarnings("unchecked")
    public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
      final T token = (T) tokenStack.pop();
      evaluate(token, tokenStack, environment);
    }

    protected abstract void evaluate(T token, Stack<Token> tokenStack, Map<String, Token> environment);

  }

  private static enum Type {

    // control operators

    APPLY(new SingleEvaluate<FunctionToken>() {

      @Override
      protected void evaluate(final FunctionToken functionToken, final Stack<Token> tokenStack,
          final Map<String, Token> environment) {
        final Map<String, Token> functionEnvironment = functionToken.getEnvironment();
        for (final Token token : functionToken.getValue()) {
          token.evaluate(tokenStack, functionEnvironment);
        }
      }

    }),

    IF(new Evaluate() {

      @Override
      public void evaluate(final Stack<Token> tokenStack, final Map<String, Token> environment) {
        final FunctionToken elseFunction = (FunctionToken) tokenStack.pop();
        final FunctionToken ifFunction = (FunctionToken) tokenStack.pop();
        final BooleanToken condition = (BooleanToken) tokenStack.pop();
        if (condition.getValue()) {
          ifFunction.evaluate(tokenStack, environment);
        } else {
          elseFunction.evaluate(tokenStack, environment);
        }
        APPLY.evaluate(tokenStack, environment);
      }

    }),

    // numbers

    LESSI(new DoubleEvaluate<NumberToken, NumberToken>() {

      @Override
      protected void evaluate(final NumberToken firstToken, final NumberToken secondToken,
          final Stack<Token> tokenStack, final Map<String, Token> environment) {
        if (firstToken.getValue() < secondToken.getValue()) {
          tokenStack.push(BooleanToken.TRUE);
        } else {
          tokenStack.push(BooleanToken.FALSE);
        }
      }

    }),

    SUBI(new DoubleEvaluate<NumberToken, NumberToken>() {

      @Override
      protected void evaluate(final NumberToken firstToken, final NumberToken secondToken,
          final Stack<Token> tokenStack, final Map<String, Token> environment) {
        final double result = firstToken.getValue() - secondToken.getValue();
        tokenStack.push(new NumberToken(result));
      }

    }),

    MULI(new DoubleEvaluate<NumberToken, NumberToken>() {

      @Override
      protected void evaluate(final NumberToken firstToken, final NumberToken secondToken,
          final Stack<Token> tokenStack, final Map<String, Token> environment) {
        final double result = firstToken.getValue() * secondToken.getValue();
        tokenStack.push(new NumberToken(result));
      }

    }),

    // points

    // arrays

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