(function (L3DEditor) {

  'use strict';


  var materialNames = L3DEditor.DefinitionService.getMaterialNames();

  angular.module('Editor').directive('materialInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/materialInput.directive.html',
      replace: true,
      scope: {
        material: '=',
        label: '@'
      },
      controller: function ($scope) {

        $scope.materialNames = materialNames;

        $scope.toggleMaterial = function () {
          $scope.material.enabled = !$scope.material.enabled;
        }

      }
    };

  });



})(L3DEditor);
