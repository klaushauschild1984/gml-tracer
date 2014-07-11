% snowflake.gml
%
% Fractal thingie with flat boxes.
%

{ /v /u /face
  0.3 0.3 0.4 point
  1.0 0.1 1.0
} plane /p

{ /col
  { /v /u /face
    col
    0.1 0.99 1.0
  } cube -0.5 -0.5 -0.5 translate 1.0 0.05 2.0 scale
} /mkfrac

[
  0.5 0.7 0.9 point
  0.5 0.9 0.5 point
  0.6 0.6 0.7 point
  1.0 0.7 0.5 point
  0.9 1.0 0.6 point
  1.0 0.5 0.3 point
  1.0 0.8 0.9 point
  1.0 1.0 0.6 point
  1.0 1.0 1.0 point
] /colors

{ 1 addi colors length modi } /incrmod

{
  /self /col /depth /base
  depth 0 eqi
  { colors col get base apply }
  { 
    col incrmod apply /col
    base depth 1 subi col self self apply
    -1.0 0.0 0.0 translate

    col incrmod apply /col
    base depth 1 subi col self self apply
    0.5 0.0 0.0 translate 60.0 rotatez -0.5 0.0 0.0 translate

    union

    col incrmod apply /col
    base depth 1 subi col self self apply
    -0.5 0.0 0.0 translate -60.0 rotatez 0.5 0.0 0.0 translate

    union

    col incrmod apply /col
    base depth 1 subi col self self apply
    1.0 0.0 0.0 translate

    union

    1.0 3.0 divf uscale
  }
  if
} /rec

mkfrac 3 0 rec rec apply
/piece


piece 

piece -0.5 0.0 0.0 translate 120.0 rotatez -0.5 0.0 0.0 translate

union

piece 0.5 0.0 0.0 translate -120.0 rotatez 0.5 0.0 0.0 translate

union


30.0 rotatex 40.0 rotatey
2.0 uscale 0.0 1.0 0.5 translate

p 0.0 -1.0 0.0 translate union
0.0 -0.2 0.3 translate
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
1024 768                          % image wid and height
"snowflake.ppm"                   % output file
render

