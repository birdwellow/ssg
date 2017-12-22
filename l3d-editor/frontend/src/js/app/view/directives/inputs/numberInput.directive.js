(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('numberInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/numberInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        step: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
