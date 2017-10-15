(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('controls', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/controls.directive.html',
      replace: true
    };

  });



})(L3DEditor);
