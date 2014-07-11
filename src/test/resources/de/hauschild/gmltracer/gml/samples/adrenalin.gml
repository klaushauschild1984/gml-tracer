% adrenalin molecule

0.5 0.5 0.5 point /grey
1.0 0.5 0.5 point /red
0.5 0.5 1.0 point /blue
0.5 0.5 1.0 point /turquoise % not really.

{ /v /u /face
  0.3 0.3 0.4 point
  1.0 0.1 1.0
} plane /p

{ /rad /col /z /y /x
  { /v /u /face
    col
    1.0 0.9 1.0
  } sphere
  rad 0.5 mulf uscale
  x y z translate
} /atom

0.26545 0.59155 1.31675
grey
1.7
atom apply

0.28425 -0.79075 1.30915
grey
1.7
atom apply
union

1.49345 -1.46715 1.29395
grey
1.7
atom apply
union

2.68715 -0.75195 1.28635
grey
1.7
atom apply
union

2.66125 0.63345 1.29405
grey
1.7
atom apply
union

1.45145 1.30205 1.30925
grey
1.7
atom apply
union

3.87645 -1.41125 1.27185
red
1.52
atom apply
union

1.51365 -2.82685 1.28685
red
1.52
atom apply
union

-1.05065 1.32555 1.32695
grey
1.7
atom apply
union

-0.89515 2.57285 2.00665
red
1.52
atom apply
union

-1.50285 1.58175 -0.11205
grey
1.7
atom apply
union

-2.78585 2.29725 -0.10205
blue
1.55
atom apply
union

-3.16115 2.51055 -1.50615
grey
1.7
atom apply
union

-0.64365 -1.34345 1.31545
turquoise
1.2
atom apply
union

3.58635 1.19085 1.28855
turquoise
1.2
atom apply
union

1.43265 2.38195 1.31525
turquoise
1.2
atom apply
union

4.11225 -1.54205 0.34315
turquoise
1.2
atom apply
union

1.50875 -3.10165 0.35975
turquoise
1.2
atom apply
union

-1.79905 0.72245 1.84115
turquoise
1.2
atom apply
union

-0.22625 3.07445 1.52085
turquoise
1.2
atom apply
union

-0.75455 2.18485 -0.62615
turquoise
1.2
atom apply
union

-1.62155 0.63035 -0.63045
turquoise
1.2
atom apply
union

-3.46795 1.66395 0.28745
turquoise
1.2
atom apply
union

-3.25835 1.54715 -2.00665
turquoise
1.2
atom apply
union

-4.11225 3.04105 -1.55145
turquoise
1.2
atom apply
union

-2.39135 3.10165 -2.00225
turquoise
1.2
atom apply
union

0.0 2.0 10.0 translate

p 0.0 -3.0 0.0 translate union

/scene

                                % directional light
0.8 -1.0 0.4 point                % direction
0.6  0.6 0.5 point light /l1      % directional light

0.0 1.5 -0.4 point  % origin
0.4 0.5 0.6 point pointlight /l2

0.5 0.5 0.5 point                 % ambient light
[ l1 l2 ]                         % lights
scene                             % scene to render
10                                % tracing depth
90.0                              % field of view
640 480                           % image wid and height
"adrenalin.ppm"                   % output file
render

