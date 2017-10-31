(function (L3DEditor) {

  'use strict';


  angular.module('Editor').controller('EditorController', function ($scope, $timeout, PersistenceService) {

    $scope.config = L3DEditor.Config;

    var error = function(message, exceptionText) {
      $scope.error = message + (exceptionText ? ': ' +exceptionText : '');
      $scope.success = '';
    };

    $scope.hideError = function () {
      $scope.error = '';
    };

    var success = function(message) {
      $scope.success = message;
      $scope.error = '';
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
      if (!catalogDefinitionName) {
        return;
      }
      $scope.definition = L3DEditor.Catalog.get(catalogDefinitionName);
    };

    $scope.save = function () {
      if (!$scope.definition) {
        return;
      }
      if (!$scope.saveCatalogDefinitionName || $scope.saveCatalogDefinitionName.length < 6) {
        error('Enter a valid definition name (longer than 6 characters)');
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
      PersistenceService.getAllDefinitions()
        .then(function(definitionMap) {
          L3DEditor.Catalog.setDefinitions(definitionMap);
          $scope.catalogDefinitionNames = L3DEditor.Catalog.getNames();
          $scope.catalogDefinitionName = $scope.catalogDefinitionName || $scope.catalogDefinitionNames[0];
        });
    };


    // Sanitize the definition on changes

    $scope.$watch('catalogDefinitionName', function () {
      $scope.load($scope.catalogDefinitionName);
      $scope.saveCatalogDefinitionName = $scope.catalogDefinitionName;
    });


    // Sanitize the definition on changes

    $scope.$watch('definition', function () {
      if($scope.definition) {
        L3DEditor.DefinitionService.sanitize($scope.definition);
      }
    }, true);



    // Bootstrap the whole party

    update();

  });


})(L3DEditor || {});
