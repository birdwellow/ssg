(function (L3DEditor) {

  'use strict';


  var materialNames = L3DEditor.DefinitionService.getMaterialNames();

  angular.module('Editor').directive('optionalObjectInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/optionalObjectInput.directive.html',
      replace: true,
      scope: {
        object: '=',
        label: '@'
      },
      controller: function ($scope) {

        $scope.materialNames = materialNames;

      }
    };

  });



})(L3DEditor);
