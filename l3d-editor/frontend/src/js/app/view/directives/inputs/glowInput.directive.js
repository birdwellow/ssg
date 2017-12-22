(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('glowInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/glowInput.directive.html',
      replace: true,
      scope: {
        glow: '=',
        label: '@'
      },
      controller: function ($scope) {

        $scope.toggleGlow = function () {
          $scope.glow.enabled = !$scope.glow.enabled;
        };

      }
    };

  });



})(L3DEditor);
