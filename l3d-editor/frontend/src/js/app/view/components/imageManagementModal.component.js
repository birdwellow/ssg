(function () {

  'use strict';


  angular
    .module('Editor')
    .component('imageManagementModalComponent', {
      templateUrl: 'js/app/view/directives/components/imageManagement.modal.html',
      bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
      },
      controller: function () {
        var $ctrl = this;

        $ctrl.ok = function () {
          $ctrl.close({$value: 'ok'});
        };

        $ctrl.cancel = function () {
          $ctrl.dismiss({$value: 'cancel'});
        };

      }

    });


})();
