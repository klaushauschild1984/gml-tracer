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
package de.hauschild.gmltracer.tracer.impl;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import de.hauschild.gmltracer.tracer.Raytracer;
import de.hauschild.gmltracer.tracer.light.Light;
import de.hauschild.gmltracer.tracer.shape.Shape;

/**
 * @since 1.0
 * 
 * @author Klaus Hauschild
 */
public class GmlRaytracer implements Raytracer {

  private static final Logger LOGGER = LoggerFactory.getLogger(GmlRaytracer.class);

  @Override
  public void render(final Vector3D ambientLightIntensity, final List<Light> lights, final Shape scene, final int depth,
      final double fieldOfView, final int width, final int height, final String fileName) {
    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    LOGGER.info("begin raytracing...");
    LOGGER.info("ambient light intensity: {}", ambientLightIntensity);
    LOGGER.info("                 lights: {}", lights);
    LOGGER.info("                  scene: {}", scene);
    LOGGER.info("                  depth: {}", depth);
    LOGGER.info("          field of view: {}", fieldOfView);
    LOGGER.info("                  width: {}", width);
    LOGGER.info("                 height: {}", height);
    LOGGER.info("               fileName: {}", fileName);
    final Stopwatch stopwatch = Stopwatch.createStarted();
    // ...
    LOGGER.info("raytracing took {}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

}
