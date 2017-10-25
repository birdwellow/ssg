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
      var material = 'phong';
      if (definition && definition.material && definition.material.type) {
        material = definition.material.type;
      }
      var factory = factories[material];
      return factory(definition);
    }

  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
