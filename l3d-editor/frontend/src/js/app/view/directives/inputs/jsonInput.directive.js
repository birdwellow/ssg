(function () {

  'use strict';


  angular.module('Editor').directive('jsonInput', function () {

    var stringify = function (object) {
      return JSON.stringify(object, null, 4);
    };

    var format = function (jsonString) {
      return stringify(JSON.parse(jsonString));
    };

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/jsonInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        error: '='
      },
      controller: function ($scope, $element) {

        $scope.valueAsString = '';

        $scope.$on('reformat', function () {
          $scope.valueAsString = format($scope.valueAsString);
        });


        // Format JSON while typing
        // TODO: Set the caret to the right position
        // $scope.$watch('valueAsString', function () {
        //   $scope.valueAsString = format($scope.valueAsString);
        // });

        $scope.$watch('value', function () {
          $scope.valueAsString = stringify($scope.value);
        });

        $scope.$watch('valueAsString', function () {
          try {
            $scope.value = JSON.parse($scope.valueAsString);
            $scope.error = false;
          } catch (e) {
            console.log(e);
            $scope.error = true;
          }

        });
      }
    };

  });

})();
