(function () {

  'use strict';


  angular.module('Editor').directive('keepFocus', function ($rootScope) {

    return function (scope, element) {
      element.bind('keydown', function (event) {
        event.stopPropagation();
      });
    };

  });

})();
