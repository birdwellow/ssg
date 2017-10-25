L3DEditor = (function (L3DEditor) {

  L3DEditor.Config = {
    isRotationActive: false,
    isMarkOnMouseOver: false
  };

  L3DEditor.Config.defaultDefinition = {
    "type": "composite",
    "parts": [
      {
        "type": "box",
        "dimensions": [200, 2, 100]
      },
      {
        "type": "composite",
        "parts": [
          {
            "type": "cylinder",
            "radii": [10, 15],
            "height": 10
          },
          {
            "type": "ref",
            "name": "plate",
            "position": [5, 5, 5],
            "rotation": [0, 45, 90],
          },
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
          }
        ],
        "position": [-85, 2, 17]
      },
      {
        "type": "ref",
        "name": "plate",
        "position": [0, 0, 0],
        "repeat": {
          "times": 13,
          "position": [0, +50, 0],
          "rotation": [0, +5, 0]
        }
      }
    ],
    // "position": [50, 50, 50],
    "rotation": [5, 5, 5],
  };

  return L3DEditor;

})(L3DEditor || {});
