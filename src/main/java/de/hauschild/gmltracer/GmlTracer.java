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
package de.hauschild.gmltracer;

import java.io.FileInputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import de.hauschild.gmltracer.gml.GMLExtractor;
import de.hauschild.gmltracer.gml.GMLInterpreter;
import de.hauschild.gmltracer.gml.GMLLexer;
import de.hauschild.gmltracer.gml.GMLParser;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
class GmlTracer {

    public static void main(final String[] args) throws Exception {
        final FileInputStream fileInputStream = new FileInputStream(args[0]);
        final GMLLexer gmlLexer = new GMLLexer(new ANTLRInputStream(fileInputStream));
        final GMLParser gmlParser = new GMLParser(new CommonTokenStream(gmlLexer));
        gmlParser.addErrorListener(new BaseErrorListener() {

            @Override
            public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line,
                    final int charPositionInLine, final String message, final RecognitionException exception) {
                throw new RuntimeException(message);
            }

        });
        final GMLExtractor gmlExtractor = new GMLExtractor(gmlParser);
        final GMLInterpreter gmlInterpreter = new GMLInterpreter(gmlExtractor);
        gmlInterpreter.interpret();
    }

}
