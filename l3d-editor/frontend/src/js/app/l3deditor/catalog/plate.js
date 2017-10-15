var L3DEditor = (function(L3DEditor) {

  'use strict';


  var Catalog = L3DEditor.Catalog || {};
  Catalog.repository = Catalog.repository || {};

  Catalog.repository.plate = {
    "type": "composite",
    "parts": [
      {
        "type": "box",
        "dimensions": [200, 2, 100]
      },
      {
        "type": "box",
        "dimensions": [50, 5, 65],
        "position": [-85, 2, 17]
      },
      {
        "type": "box",
        "dimensions": [50, 3, 30],
        "position": [-75, 2, -35]
      },
      {
        "type": "box",
        "dimensions": [20, 3, 30],
        "position": [-20, 2, -35]
      },
      {
        "type": "box",
        "dimensions": [20, 3, 30],
        "position": [5, 2, -35]
      },
      {
        "type": "box",
        "dimensions": [20, 3, 30],
        "position": [30, 2, -35]
      }
    ]
  };

  L3DEditor.Catalog = Catalog;

  return L3DEditor;

}) (L3DEditor || {});
