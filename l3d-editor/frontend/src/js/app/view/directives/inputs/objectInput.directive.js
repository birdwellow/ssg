(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('objectInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/objectInput.directive.html',
      replace: true,
      scope: {
        object: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
