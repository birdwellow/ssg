L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var factories = {

    "phong": function (definition) {
      return new THREE.MeshPhongMaterial();
    },

    "lambert": function (definition) {
      return new THREE.MeshLambertMaterial();
    },

    "standard": function (definition) {
      return new THREE.MeshStandardMaterial();
    }

  };

  L3DEditor.MaterialFactory = {

    createFromDefinition: function (definition) {
      if (definition && definition.isMouseOver && L3DEditor.Config.isMarkOnMouseOver) {
        return new THREE.MeshBasicMaterial({
          color: "red"
        });
      }
      var material = 'phong';
      if (definition && definition.material && definition.material.type) {
        material = definition.material.type;
      }
      var factory = factories[material];
      var material = factory(definition);
      return material;
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
