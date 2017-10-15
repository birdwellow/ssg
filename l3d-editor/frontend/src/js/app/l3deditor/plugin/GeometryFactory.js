L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var factories = {

    "box": function (definition) {
      return new THREE.BoxGeometry(
        definition.dimensions[0],
        definition.dimensions[1],
        definition.dimensions[2]
      );
    },

    "cylinder": function (definition) {
      return new THREE.CylinderGeometry(
        definition.radii[0],
        definition.radii[1],
        definition.height,
        20
      );
    },

    "extrude": function (definition) {
      var points = definition.points;
      var firstPoint = points[0];

      var shape = new THREE.Shape();
      shape.moveTo(firstPoint [0], firstPoint [1]);
      for (var i in points) {
        var point = points[i];
        shape.lineTo(point[0], point[1]);
      }

      var extrudeSettings = {
        steps: definition.roundness || 20,
        amount: definition.width || 20,
        bevelEnabled: definition.bevelEnabled != undefined ? definition.bevelEnabled : true,
        bevelThickness: definition.bevelThickness || 1,
        bevelSize: definition.bevelSize || 1,
        bevelSegments: definition.bevelSegments || 1
      };

      return new THREE.ExtrudeGeometry(shape, extrudeSettings);
    }

  };

  L3DEditor.GeometryFactory = {

    createCenter: function (definition) {
      return new THREE.BoxGeometry(0, 0, 0);
    },

    createFromDefinition: function (definition) {
      var type = definition.type;
      if (!type) {
        throw '"' + type + '" is not a valid type';
      }

      var factory = factories[type];

      if(!factory){
        throw 'No factory exists for type "' + type + '"';
      }

      return factory(definition);
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
