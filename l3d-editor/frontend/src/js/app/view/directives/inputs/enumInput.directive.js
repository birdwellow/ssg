(function () {

  'use strict';


  angular.module('Editor').directive('enumInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/enumInput.directive.html',
      replace: true,
      scope: {
        label: '=',
        model: '=',
        field: '@',
        enumValues: '='
      }
    };

  });

})();
