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

    createFromDefinition: function (definition, alternativeMaterial) {
      if (definition && definition.isMouseOver && L3DEditor.Config.isMarkOnMouseOver) {
        return new THREE.MeshBasicMaterial({
          color: "red"
        });
      }

      var materialDefinition = (definition.material && definition.material.enabled) ? definition.material : alternativeMaterial;
      var materialType = 'phong';
      if (materialDefinition && materialDefinition.type) {
        materialType = materialDefinition.type;
      }
      var factory = factories[materialType];
      var material = factory(materialDefinition || {});
      return material;
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
