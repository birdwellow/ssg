(function () {

  'use strict';

  var hexMeanValue = function (hexColor) {
    var rgbArray = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hexColor);
    if (!rgbArray) {
      return null;
    }
    return parseInt(rgbArray[1], 16) + parseInt(rgbArray[2], 16) + parseInt(rgbArray[3], 16);
  };

  angular.module('Editor').directive('colorInput', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/inputs/colorInput.directive.html',
      replace: true,
      scope: {
        value: '=',
        label: '@'
      },
      controller: function ($scope) {

        $scope.isDark = function () {
          if (!$scope.value) {
            return false;
          }
          var channelMean = hexMeanValue($scope.value);
          if (!channelMean) {
            return false;
          }
          return (channelMean < 255);
        };

      }
    };

  });



})();
