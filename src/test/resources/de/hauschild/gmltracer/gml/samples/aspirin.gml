% aspirin molecule

1.0 1.0 1.0 point /white
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
  rad uscale
  x y z translate
} /atom

-2.1016  -1.1628  -0.4897  %  -0.0592 CAR    
grey
1.0
atom
apply

-1.5789  -2.3192   0.0985  %  -0.0608 CAR    
grey
1.0
atom
apply union

-0.2880  -2.3169   0.6237  %  -0.0463 CAR    
grey
1.0
atom
apply union

 0.4982  -1.1562   0.5665  %   0.0767 CAR    
grey
1.0
atom
apply union

-0.0060  -0.0142  -0.1081  %   0.0989 CAR    
grey
1.0
atom
apply union

-1.3176  -0.0099  -0.5663  %  -0.0302 CAR    
grey
1.0
atom
apply union

 1.8754  -1.2008   1.1918  %   0.2937 C2     
grey
1.0
atom
apply union

 2.3237  -2.2044   1.7241  %  -0.2593 O2     
red
1.0
atom
apply union

 2.6701  -0.1455   1.1998  %  -0.3274 O3     
red
1.0
atom
apply union

 0.5437   1.1640   0.0040  %  -0.2771 O3     
red
1.0
atom
apply union

 0.4587   1.8545   1.1222  %   0.2640 C2     
grey
1.0
atom
apply union

 1.0904   2.8925   1.2405  %  -0.2648 O2     
red
1.0
atom
apply union

-0.4520   1.4066   2.2316  %   0.0218 C3     
grey
1.0
atom
apply union

-3.0887  -1.1326  -0.8969  %   0.0618 H      
white
0.7
atom
apply union

-2.1972  -3.2070   0.1331  %   0.0617 H      
white
0.7
atom
apply union

 0.0906  -3.2334   1.0831  %   0.0626 H      
white
0.7
atom
apply union

-1.6981   0.9134  -0.9708  %   0.0643 H      
white
0.7
atom
apply union

 2.3570   0.6120   0.7625  %   0.2205 H      
white
0.7
atom
apply union

-0.9700   0.4657   2.0214  %   0.0330 H      
white
0.7
atom
apply union

-1.2187   2.1784   2.3783  %   0.0330 H      
white
0.7
atom
apply union

 0.1367   1.2646   3.1468  %   0.0330 H      
white
0.7
atom
apply union

180.0 rotatex

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
"aspirin.ppm"                     % output file
render

