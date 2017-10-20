(function (L3DEditor) {

  'use strict';


  angular.module('Editor').controller('EditorController', function ($scope, $timeout, PersistenceService) {

    PersistenceService.getAllDefinitions()
      .then(function(definitionMap) {
        L3DEditor.Catalog.setDefinitions(definitionMap);
        update();
    }, function(error) {
      console.error(error);
    });

    $scope.config = L3DEditor.Config;

    var error = function(message, exceptionText) {
      $scope.error = message + (exceptionText ? ': ' +exceptionText : '');
      $timeout(function () {
        $scope.error = '';
      }, 10000);
    };

    var success = function(message) {
      $scope.success = message;
      $timeout(function () {
        $scope.success = '';
      }, 5000);
    };

    $scope.newDefinition = function () {
      $scope.save();
      $scope.definition = L3DEditor.DefinitionService.getDefinitionTemplate('composite');
      $scope.saveCatalogDefinitionName = '';
    };

    $scope.load = function (catalogDefinitionName) {
      if (catalogDefinitionName) {
        $scope.definition = L3DEditor.Catalog.get(catalogDefinitionName);
      }
    };

    $scope.save = function () {
      if (!$scope.definition) {
        return;
      }
      if(!$scope.saveCatalogDefinitionName) {
        error('"' + $scope.saveCatalogDefinitionName + '" is not a valid name');
      }
      PersistenceService.saveDefinition($scope.saveCatalogDefinitionName, $scope.definition)
        .then(function () {
          update();
          success('Successfully saved!');
        }, function () {
          error('An error occurred', e);
        });
    };

    var update = function () {
      $scope.catalogDefinitionNames = L3DEditor.Catalog.getNames();
      $scope.catalogDefinitionName = $scope.catalogDefinitionName || $scope.catalogDefinitionNames[0];
    };
    update();


    $scope.$watch('catalogDefinitionName', function () {
      $scope.load($scope.catalogDefinitionName);
      $scope.saveCatalogDefinitionName = $scope.catalogDefinitionName;
    });

    $scope.$watch('definition', function () {
      if($scope.definition) {
        L3DEditor.DefinitionService.sanitize($scope.definition);
      }
    }, true);

  });


})(L3DEditor || {});
