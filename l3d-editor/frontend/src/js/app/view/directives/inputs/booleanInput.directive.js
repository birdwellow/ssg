(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('booleanInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/booleanInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        label: '@'
      },
      link: function(scope) {
        scope.id = Math.round(Math.random() * 1000000000);
      }
    };

  });



})(L3DEditor);
