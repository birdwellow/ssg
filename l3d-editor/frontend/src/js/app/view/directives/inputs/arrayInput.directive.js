(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('arrayInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/arrayInput.directive.html',
      replace: true,
      scope: {
        array: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
