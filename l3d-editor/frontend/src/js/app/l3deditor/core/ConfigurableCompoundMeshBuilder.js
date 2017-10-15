L3DEditor = (function (THREE, L3DEditor) {

  'use strict';


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

  var createMesh = function (geometry, definition) {
    var material = new THREE.MeshPhongMaterial();
    var mesh = new THREE.Mesh(geometry, material);
    positionAndAdjustMesh(mesh, definition);
    return mesh;
  };

  var createMeshByType = function (definition) {
    var geometry = L3DEditor.GeometryFactory.createFromDefinition(definition);
    var mesh = createMesh(geometry, definition);
    return mesh;
  };

  var createMeshes = function (definitions) {
    var meshes = [];
    meshes.push(L3DEditor.GeometryFactory.createCenter());
    for (var i in definitions) {
      var partialDefinition = definitions[i];
      var mesh = create(partialDefinition);
      meshes.push(mesh);
    }
    return meshes;
  };

  var createCompositeMesh = function (definition) {
    var meshes = createMeshes(definition.parts);
    var mesh = new THREE.CompoundMesh(meshes);
    positionAndAdjustMesh(mesh, definition);
    return mesh;
  };

  var create = function (definition) {
    if (definition.type === "composite") {
      return createCompositeMesh(definition);
    } else {
      return createMeshByType(definition);
    }
  };

  L3DEditor.ConfigurableCompoundMeshBuilder = {
    create: create
  };

  return L3DEditor;

}) (THREE || {}, L3DEditor || {});
