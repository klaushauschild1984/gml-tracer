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
package de.hauschild.gmltracer.tracer.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Stopwatch;
import de.hauschild.gmltracer.tracer.Raytracer;
import de.hauschild.gmltracer.tracer.Vector3DUtils;
import de.hauschild.gmltracer.tracer.light.Light;
import de.hauschild.gmltracer.tracer.shape.Shape;
import de.hauschild.gmltracer.tracer.shape.SurfaceProperties;

/**
 * @since 1.0
 * @author Klaus Hauschild
 */
public class GmlRaytracer implements Raytracer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmlRaytracer.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public void render(final Vector3D ambientLightIntensity, final List<Light> lights,
                    final Shape scene, final int depth, final double fieldOfView, final int width,
                    final int height, final String fileName) {
        final double aspectRatio = (double) height / width;
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
        final JFrame preview = new JFrame() {

            {
                setTitle(fileName);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyPressed(final KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            dispose();
                            System.exit(0);
                        }
                    }

                });
                setResizable(false);
                setLayout(new BorderLayout());
                add(new JPanel() {

                    {
                        setPreferredSize(new Dimension(800, (int) (800 * aspectRatio)));
                    }

                    @Override
                    public void paint(final Graphics graphics) {
                        final double scaleX = getPreferredSize().getWidth() / width;
                        final double scaleY = getPreferredSize().getHeight() / height;
                        ((Graphics2D) graphics).scale(scaleX, scaleY);
                        graphics.drawImage(image, 0, 0, null);
                    }

                });
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
            }

        };
        final Iterator<Point> pointIterator = new RandomPointIterator(width, height);
        final Stopwatch stopwatch = Stopwatch.createStarted();
        while (pointIterator.hasNext()) {
            final Point point = pointIterator.next();
            executorService.execute(() -> {
                final Color color = renderPoint(ambientLightIntensity, lights, scene, depth,
                                fieldOfView, width, height, point);
                synchronized (image) {
                    final Graphics graphics = image.getGraphics();
                    graphics.setColor(color);
                    graphics.drawLine(point.x, point.y, point.x, point.y);
                    preview.repaint();
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (final InterruptedException exception) {
            throw new RuntimeException(exception);
        }
        stopwatch.stop();
        LOGGER.info("raytracing took {}", stopwatch);
        stopwatch.reset().start();
        LOGGER.info("begin file writing...");
        final File file = new File(fileName);
        try {
            file.createNewFile();
            ImageIO.write(image, "PNG", file);
            Thread.sleep(3 * 1000);
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
        LOGGER.info("file writing took {}", stopwatch);
    }

    private Color convert(final Vector3D color) {
        final int r = (int) (255 * Math.min(1.0, color.getX()));
        final int g = (int) (255 * Math.min(1.0, color.getY()));
        final int b = (int) (255 * Math.min(1.0, color.getZ()));
        return new Color(r, g, b);
    }

    private Color renderPoint(final Vector3D ambientLightIntensity, final List<Light> lights,
                    final Shape scene, final int depth, final double fieldOfView, final int width,
                    final int height, final Point point) {
        final double worldWidth = 2.0 * Math.tan(Math.toRadians(0.5 * fieldOfView));
        final double worldHeight = worldWidth * width / height;
        final double pixelSize = worldWidth / width;
        final Ray ray = new Ray(new Vector3D(0.0, 0.0, -1.0),
                        new Vector3D(-(worldWidth - 2 * (point.x + 0.5) * pixelSize),
                                        worldHeight - 2 * (point.y + 0.5) * pixelSize, 1.0));
        final Vector3D color = renderRay(ambientLightIntensity, lights, scene, depth, ray);
        return convert(color);
    }

    private Vector3D renderRay(final Vector3D ambientLightIntensity, final List<Light> lights,
                    final Shape scene, final int depth, final Ray ray) {
        if (depth == 0) {
            return Vector3D.ZERO;
        }
        final Intersection intersection = scene.intersect(ray);
        if (intersection == null) {
            return Vector3D.ZERO;
        }
        final SurfaceProperties surfaceProperties = intersection.getSurfaceProperties();
        // ambient color
        final Vector3D ambientColor = Vector3DUtils
                        .multiplyComponentwise(surfaceProperties.getColor(), ambientLightIntensity)
                        .scalarMultiply(surfaceProperties.getDiffuseReflectionCoefficient());
        // light color
        Vector3D lightColor = Vector3D.ZERO;
        for (final Light light : lights) {
            // if (!light.isInShadow(scene, intersection)) {
            final Vector3D currentLightColor =
                            light.illuminates(surfaceProperties.getColor(), intersection);
            lightColor = lightColor.add(currentLightColor);
            // }
        }
        lightColor = lightColor.scalarMultiply(surfaceProperties.getDiffuseReflectionCoefficient());
        // specular color for reflected ray
        final Ray reflectedRay = Vector3DUtils.reflectRay(ray, intersection.getNormal());
        Vector3D specularColor =
                        renderRay(ambientLightIntensity, lights, scene, depth - 1, reflectedRay);
        specularColor = Vector3DUtils
                        .multiplyComponentwise(specularColor, surfaceProperties.getColor())
                        .scalarMultiply(surfaceProperties.getSpecularReflectionCoefficient());
        // compute color
        return ambientColor.add(lightColor).add(specularColor);
    }

}
