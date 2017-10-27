(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('stringInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/stringInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
