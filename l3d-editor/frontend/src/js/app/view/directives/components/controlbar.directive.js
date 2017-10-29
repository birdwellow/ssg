(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('controlbar', function ($uibModal, $document) {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/components/controlbar.directive.html',
      replace: true,
      controller: function ($scope) {

        var modalParent = angular.element($document[0].querySelector('.modal-parent'));

        $scope.imageManagementModal = function () {
          var modal = $uibModal.open({
            animation: false,
            templateUrl: 'js/app/view/directives/components/imageManagement.modal.html',
            controller: 'ModalInstanceCtrl',
            appendTo: modalParent,
            controllerAs: '$ctrl'
          });
        };

      }
    };

  });



})(L3DEditor);
