var L3DEditor = (function(L3DEditor) {

  'use strict';



  var LocalCache = L3DEditor.LocalCache || {};

  var STORAGE_PREFIX = 'l3deditor.cache.';

  LocalCache.save = function (name, object) {
    localStorage.setItem(STORAGE_PREFIX + name, JSON.stringify(object));
  };

  LocalCache.get = function(name) {
    var result = localStorage.getItem(STORAGE_PREFIX + name);
    if(result) {
      return JSON.parse(result);
    }
    throw "No object found for reference name '" + name + "'";
  };

  LocalCache.getAll = function () {
    var result = {};
    var keysWithPrefix = Object.keys(localStorage)
      .filter(function(key) {
        return key && key.indexOf(STORAGE_PREFIX) !== -1;
      });
    keysWithPrefix.forEach(function (keyWithPrefix) {
      result[keyWithPrefix] = localStorage.getItem(keyWithPrefix);
    });
    return result;
  };

  L3DEditor.LocalCache = LocalCache;

  return L3DEditor;

}) (L3DEditor || {});
