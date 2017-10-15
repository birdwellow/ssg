(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('twoDimArrayInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/twoDimArrayInput.directive.html',
      replace: true,
      scope: {
        array: '=',
        label: '@',
        minimalLength: '@'
      },
      controller: function ($scope) {

        $scope.addElement = function() {
          $scope.array.push([0, 0]);
          delete $scope.error;
        };

        $scope.removeElement = function (index) {
          if($scope.array.length <= $scope.minimalLength) {
            $scope.error = 'Array must have at least ' + $scope.minimalLength + ' elements';
            return;
          }
          $scope.array.splice(index, 1);
          delete $scope.error;
        };

      }
    };

  });



})(L3DEditor);
