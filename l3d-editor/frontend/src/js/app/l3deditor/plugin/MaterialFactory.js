L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var factories = {

    "phong": function (material) {
      return new THREE.MeshPhongMaterial({
        color: material.color
      });
    },

    "lambert": function (material) {
      return new THREE.MeshLambertMaterial({
        color: material.color
      });
    },

    "standard": function (material) {
      return new THREE.MeshStandardMaterial({
        color: material.color
      });
    }

  };

  L3DEditor.MaterialFactory = {

    createFromDefinition: function (definition) {
      if (definition && definition.isMouseOver && L3DEditor.Config.isMarkOnMouseOver) {
        return new THREE.MeshBasicMaterial({
          color: "red"
        });
      }
      var materialType = 'phong';
      if (definition && definition.material && definition.material.type) {
        materialType = definition.material.type;
      }
      var factory = factories[materialType];
      var material = factory(definition.material);
      return material;
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
