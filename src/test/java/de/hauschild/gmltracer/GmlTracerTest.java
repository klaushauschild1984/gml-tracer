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
package de.hauschild.gmltracer;

import java.io.InputStream;
import org.testng.annotations.Test;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class GmlTracerTest {

    @Test
    public void threeSpheresTest() throws Exception {
        try (final InputStream stream = ClassLoader.getSystemResourceAsStream(
                        "de/hauschild/gmltracer/gml/samples/three-spheres.gml")) {
            GmlTracer.render(stream);
        }
    }

    @Test
    public void spheres2Test() throws Exception {
        try (final InputStream stream = ClassLoader.getSystemResourceAsStream(
                        "de/hauschild/gmltracer/gml/samples/spheres2.gml")) {
            GmlTracer.render(stream);
        }
    }

    @Test
    public void checkedCubeTest() throws Exception {
        try (final InputStream stream = ClassLoader.getSystemResourceAsStream(
                        "de/hauschild/gmltracer/gml/samples/checked-cube.gml")) {
            GmlTracer.render(stream);
        }
    }

    @Test
    public void fractalTest() throws Exception {
        try (final InputStream stream = ClassLoader.getSystemResourceAsStream(
                "de/hauschild/gmltracer/gml/samples/fractal.gml")) {
            GmlTracer.render(stream);
        }
    }

}
