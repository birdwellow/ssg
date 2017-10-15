(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('modeller', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/components/modeller.directive.html',
      replace: true
    };

  });

})(L3DEditor);
