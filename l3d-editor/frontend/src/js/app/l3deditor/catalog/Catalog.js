var L3DEditor = (function(L3DEditor) {

  'use strict';



  var Catalog = L3DEditor.Catalog || {};

  var STORAGE_PREFIX = 'l3deditor.catalog.';

  Catalog.save = function (name, object) {
    localStorage.setItem(STORAGE_PREFIX + name, JSON.stringify(object));
  };

  Catalog.get = function(name) {
    var result = localStorage.getItem(STORAGE_PREFIX + name);
    if(result) {
      return JSON.parse(result);
    }
    var result = Catalog.repository[name];
    if (result) {
      return result
    }
    throw "No object found for reference name '" + name + "'";
  };

  Catalog.getNames = function () {
    var localStorageNames = Object.keys(localStorage)
      .filter(function(key) {
        return key && key.indexOf(STORAGE_PREFIX) !== -1;
      })
      .map(function(key) {
        return key.replace(STORAGE_PREFIX, '');
      });
    var repositoryNames = Object.keys(Catalog.repository);
    return localStorageNames.concat(repositoryNames);
  };

  Catalog.repository = {};

  L3DEditor.Catalog = Catalog;

  return L3DEditor;

}) (L3DEditor || {});
