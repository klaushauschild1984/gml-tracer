% test-sphere.gml
%
% This file tests directional lights, spheres, union, and translate.

{ /color
  { /v /u /face   % bind arguments
    color         % surface color
    0.8 0.2 10.0  % kd ks n
  } sphere
} /mkSphere

1.0 0.0 0.0 point mkSphere apply /redSphere
0.0 1.0 0.0 point mkSphere apply /greenSphere
0.0 0.0 1.0 point mkSphere apply /blueSphere

redSphere   -2.5 0.0 9.0 translate
greenSphere  0.0 0.0 9.0 translate
blueSphere   2.5 0.0 9.0 translate
union union /scene

        % directional light
1.0 -1.0 1.0 point          % direction
1.0 1.0 1.0 point light /l % directional light

        % render
0.25 0.25 0.25 point      % ambient light
[ l ]                     % lights
scene                     % scene to render
2                         % tracing depth
60.0                      % field of view
800 800                   % image width and height
"target/test-sphere.png"  % output file
render
