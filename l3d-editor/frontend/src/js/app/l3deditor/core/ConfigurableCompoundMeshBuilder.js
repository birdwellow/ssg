L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


  var camera;

  var translate = function (mesh, position) {
    if (Array.isArray(position)) {
      mesh.position.set(
        position[0],
        position[1],
        position[2]
      );
    }
  };

  var rotate = function (mesh, rotation) {
    if (Array.isArray(rotation)) {
      mesh.rotateX(rotation[0]/180*Math.PI);
      mesh.rotateY(rotation[1]/180*Math.PI);
      mesh.rotateZ(rotation[2]/180*Math.PI);
    }
  };

  var positionAndAdjustMesh = function (mesh, definition) {
    translate(mesh, definition.position);
    rotate(mesh, definition.rotation);
  };

  var createMesh = function (geometry, material, definition) {
    var mesh = new THREE.Mesh(geometry, material);
    mesh.castShadow = true;
    mesh.receiveShadow = true;
    positionAndAdjustMesh(mesh, definition);
    return mesh;
  };

  var createMeshByType = function (definition, inheritedMaterial) {
    var geometry = L3DEditor.GeometryFactory.createFromDefinition(definition);
    var material = L3DEditor.MaterialFactory.createFromDefinition(definition, inheritedMaterial);
    var mesh = createMesh(geometry, material, definition);
    return mesh;
  };

  var createMeshes = function (definitions, inheritedMaterial) {
    var meshes = [];
    meshes.push(L3DEditor.GeometryFactory.createCenter());
    for (var i in definitions) {
      var partialDefinition = definitions[i];
      var mesh = create(partialDefinition, inheritedMaterial);
      meshes.push(mesh);
      if (partialDefinition.glow && partialDefinition.glow.enabled) {
        var glowMesh = L3DEditor.GlowFactory.createFromDefinitionAndGeometry(partialDefinition, mesh, camera);
        meshes.push(glowMesh);
      }
    }
    return meshes;
  };

  var createCompositeMesh = function (definition, inheritedMaterial) {
    var material = (definition.material && definition.material.enabled) ? definition.material : inheritedMaterial;
    var meshes = createMeshes(definition.parts, material);
    var mesh = new THREE.CompoundMesh(meshes);
    positionAndAdjustMesh(mesh, definition);
    return mesh;
  };

  var create = function (definition, inheritedMaterial) {
    camera = definition.camera || camera;
    if (definition.type === "composite") {
      return createCompositeMesh(definition, inheritedMaterial);
    } else {
      return createMeshByType(definition, inheritedMaterial);
    }
  };

  L3DEditor.ConfigurableCompoundMeshBuilder = {
    create: create
  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
