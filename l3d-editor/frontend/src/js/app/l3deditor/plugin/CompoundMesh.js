THREE = (function (THREE) {

  'use strict';


  var checkCondition = function (evaluatedCondition, checkFailureMessage) {
    if (!evaluatedCondition) {
      throw checkFailureMessage;
    }
  };

  var assertParameterIsArray = function (meshArray) {
    checkCondition(Array.isArray(meshArray), 'Cannot instantiate - array of child elements required');
  };

  var assertArrayNotEmpty = function (meshArray) {
    checkCondition(meshArray.length > 0, 'Cannot instantiate - array contains no child elements');
  };

  var assertCompoundMeshInstantiable = function (meshArray) {
    assertParameterIsArray(meshArray);
    assertArrayNotEmpty(meshArray);
  };

  THREE.CompoundMesh = function (meshArray) {

    this.type = 'CompoundMesh';

    assertCompoundMeshInstantiable(meshArray);

    var parent = meshArray[0];

    this.geometry = parent.geometry;
    this.material = parent.material;

    THREE.Mesh.call( this, this.geometry, this.material );

    for (var i = 1; i < meshArray.length; i++) {
      var childShape = meshArray[i];
      this.add(childShape);
    }

  };

  THREE.CompoundMesh.prototype = Object.create( THREE.Mesh.prototype );
  THREE.CompoundMesh.prototype.constructor = THREE.CompoundMesh;
  THREE.CompoundMesh.prototype.getMesh = function() {
    return this.mesh;
  };

  return THREE;

}) (THREE || {});
