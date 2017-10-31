L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var textureLoader = new THREE.TextureLoader();

  var factories = {

    "lambert": function (material) {
      var config = {};
      config.color = material.color;

      return new THREE.MeshLambertMaterial(config);
    },

    "phong": function (material) {
      var config = {};
      config.color = material.color;
      config.bumpMap = material.topology ? textureLoader.load(material.topology) : undefined;

      return new THREE.MeshPhongMaterial(config);
    },

    "standard": function (material) {
      var config = {};
      config.color = material.color;
      config.bumpMap = material.topology ? textureLoader.load(material.topology) : undefined;

      return new THREE.MeshStandardMaterial(config);
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
