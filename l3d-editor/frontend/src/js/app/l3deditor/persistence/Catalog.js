var L3DEditor = (function(L3DEditor) {

  'use strict';



  var Catalog = L3DEditor.Catalog || {};
  var definitions = {};

  Catalog.get = function(name) {
    if (!name) {
      throw "Catalog: " + name + " is not a valid definition name";
    }
    var definition = definitions[name];
    if (!definition) {
      throw "Catalog: No definition found for name " + name;
    }
    return definition;
  };

  Catalog.getNames = function () {
    return Object.keys(definitions);
  };

  Catalog.setDefinitions = function (currentDefinitions) {
    if (currentDefinitions) {
      definitions = currentDefinitions;
    }
  };

  L3DEditor.Catalog = Catalog;

  return L3DEditor;

}) (L3DEditor || {});
