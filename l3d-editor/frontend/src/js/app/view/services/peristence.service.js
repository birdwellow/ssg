(function (L3DEditor) {

  'use strict';


  angular
    .module('Editor')
    .service('PersistenceService', function ($http) {

      var baseUrl = 'http://localhost:8880/';

      var getAllDefinitions = function () {
        return $http.get(baseUrl + 'definitions').then(function (response) {
          var definitions = {};
          for (var key in response.data) {
            var definition = response.data[key];
            try {
              definitions[key] = JSON.parse(definition);
            } catch (e) {
              console.error('Error while parsing fetched definition: ' + definition);
              console.error(e);
            }
          }
          return definitions;
        }, function (error) {
          console.error(error);
        });
      };

      var getDefinitionNames = function () {
        return getAllDefinitions().then(function (definitionsMap) {
          return Object.keys(definitionsMap);
        });
      };

      var saveDefinition = function (name, definition) {
        var definitionPayload = {
          name: name,
          json: JSON.stringify(definition)
        };
        return $http.post(baseUrl + '/definitions', definitionPayload).then(function (response) {
          return response.data;
        }, function (error) {
          console.error(error);
        });
      };

    return {
      getAllDefinitions: getAllDefinitions,
      getDefinitionNames: getDefinitionNames,
      saveDefinition: saveDefinition
    };

  });

})(L3DEditor);
