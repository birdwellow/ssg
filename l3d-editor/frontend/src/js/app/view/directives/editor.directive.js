(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('editor', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/editor.directive.html',
      replace: true,
      controller: function ($scope, $timeout) {

        $scope.config = L3DEditor.Config;

        var error = function(message, exceptionText) {
          $scope.error = message + (exceptionText ? ': ' +exceptionText : '');
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
          $scope.definition = L3DEditor.Catalog.get(catalogDefinitionName);
        };

        $scope.save = function () {
          try {
            if(!$scope.saveCatalogDefinitionName) {
              throw '"' + $scope.saveCatalogDefinitionName + '" is not a valid name';
            }
            L3DEditor.Catalog.save($scope.saveCatalogDefinitionName, $scope.definition);
            update();
            success('Successfully saved!');
          } catch (e) {
            error('An error occurred', e);
          }
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
          L3DEditor.DefinitionService.sanitize($scope.definition);
        }, true);

      }
    };

  });



})(L3DEditor || {});
