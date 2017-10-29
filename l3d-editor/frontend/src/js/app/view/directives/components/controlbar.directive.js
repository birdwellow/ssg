(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('controlbar', function ($uibModal) {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/components/controlbar.directive.html',
      replace: true,
      controller: function ($scope) {

        $scope.imageManagementModal = function () {
          var modal = $uibModal.open({
            animation: false,
            component: 'imageManagementModalComponent',
            appendTo: angular.element(document.body),
            size: 'lg'
          });
        };

      }
    };

  });

})(L3DEditor);
