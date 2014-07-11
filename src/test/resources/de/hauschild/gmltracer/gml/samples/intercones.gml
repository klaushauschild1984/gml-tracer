% truc1.gml
%
% Some highly exciting objects.
%

{ /v /u /face
  0.75 0.25 0.75 point
  1.0 0.9 1.0
} cone
0.0 -0.9 0.0 translate

{ /v /u /face
  0.75 0.75 0.25 point
  1.0 0.9 1.0
} cone
180.0 rotatex
0.0 0.9 0.0 translate

union

1.0 2.0 1.0 scale

/dcone

dcone

dcone 0.0 2.0 0.0 translate union

dcone 0.0 -2.0 0.0 translate union

0.25 uscale

90.0 rotatez

 /scene

                                % directional light
0.8 -1.0 0.4 point                % direction
0.6  0.6 0.5 point light /l1      % directional light

0.0 1.5 -0.4 point  % origin
0.4 0.5 0.6 point pointlight /l2

0.5 0.5 0.5 point                 % ambient light
[ l1 l2 ]                         % lights
scene                             % scene to render
20                                % tracing depth
90.0                              % field of view
640 480                           % image wid and height
"intercones.ppm"                       % output file
render

