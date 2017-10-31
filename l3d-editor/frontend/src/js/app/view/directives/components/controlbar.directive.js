(function () {

  'use strict';


  angular.module('Editor').directive('controlbar', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/components/controlbar.directive.html',
      replace: true
    };

  });

})();
