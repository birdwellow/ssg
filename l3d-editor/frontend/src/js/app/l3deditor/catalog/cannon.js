var L3DEditor = (function(L3DEditor) {

  'use strict';


  var Catalog = L3DEditor.Catalog || {};
  Catalog.repository = Catalog.repository || {};

  Catalog.repository.cannon = {
    "type": "composite",
    "parts": [
      {
        "type": "extrude",
        "points": [
          [-45, 0],
          [65, 0],
          [65, 10],
          [30, 15],
          [-45, 15]
        ],
        "width": 44,
        "position": [0, -10, -22]
      },
      {
        "type": "box",
        "dimensions": [60, 20, 15],
        "position": [-20, 2, -10]
      },
      {
        "type": "box",
        "dimensions": [60, 20, 15],
        "position": [-20, 2, 10]
      },
      {
        "type": "cylinder",
        "radii": [5, 3],
        "height": 60,
        "position": [60, 2, 10],
        "rotation": [0, 0, 92]
      },
      {
        "type": "cylinder",
        "radii": [5, 3],
        "height": 60,
        "position": [60, 2, -10],
        "rotation": [0, 0, 92]
      },
      {
        "type": "cylinder",
        "radii": [20, 20],
        "height": 5,
        "position": [0, -13, 0]
      }
    ]
  };

  L3DEditor.Catalog = Catalog;

  return L3DEditor;

}) (L3DEditor || {});
