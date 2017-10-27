(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('colorInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/colorInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        label: '@'
      }
    };

  });



})(L3DEditor);
