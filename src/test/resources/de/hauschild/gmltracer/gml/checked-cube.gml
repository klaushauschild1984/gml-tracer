0.0 0.0 0.0 point /black
1.0 1.0 1.0 point /white

[                                 % 3x3 pattern
  [ black white black ]
  [ white black white ]
  [ black white black ]
] /texture

{ /v /u /face                     % bind parameters
  {                               % toIntCoord : float -> int
    3.0 mulf floor /i               % i = floor(3.0*r)
    i 3 eqi { 2 } { i } if           % make sure i is not 3
  } /toIntCoord
  texture u toIntCoord apply get  % color = texture[u][v]
    v toIntCoord apply get
  1.0                             % kd = 1.0
  0.0                             % ks = 0.0
  1.0                             % n = 1.0
} cube