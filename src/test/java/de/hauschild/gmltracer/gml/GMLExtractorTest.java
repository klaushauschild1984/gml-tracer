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
package de.hauschild.gmltracer.gml;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.hauschild.gmltracer.gml.token.Token;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class GMLExtractorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GMLExtractorTest.class);

    @DataProvider
    public Object[][] dataProvider() {
        return new Object[][] {{"fact.gml", 6,}, {"test.gml", 9,}, {"three-spheres.gml", 63,},
                {"checked-cube.gml", 14,},

        };
    }

    @Test(dataProvider = "dataProvider")
    public void gmlExtractorTest(final String fileName, final int expectedTokenCount)
                    throws IOException {
        final GMLLexer gmlLexer = new GMLLexer(
                        new ANTLRInputStream(getClass().getResourceAsStream(fileName)));
        final GMLParser gmlParser = new GMLParser(new CommonTokenStream(gmlLexer));
        gmlParser.addErrorListener(new BaseErrorListener() {

            @Override
            public void syntaxError(final Recognizer<?, ?> theRecognizer,
                            final Object theOffendingSymbol, final int theLine,
                            final int theCharPositionInLine, final String theMsg,
                            final RecognitionException theE) {
                Assert.fail(theMsg);
            }

        });
        final GMLExtractor gmlExtractor = new GMLExtractor(gmlParser);
        final List<Token> tokens = gmlExtractor.extract();
        LOGGER.info(tokens.toString());
        Assert.assertEquals(tokens.size(), expectedTokenCount);
    }
}
