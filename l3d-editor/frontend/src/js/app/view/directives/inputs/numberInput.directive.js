(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('simpleInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/simpleInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
