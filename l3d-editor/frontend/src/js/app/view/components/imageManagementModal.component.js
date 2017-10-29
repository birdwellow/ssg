(function () {

  'use strict';


  angular
    .module('Editor')
    .component('imageManagementModalComponent', {
      templateUrl: 'js/app/view/components/imageManagementModal.component.html',
      bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
      },
      controller: function () {

        var $ctrl = this;

        $ctrl.url = 'http://localhost:8880/images';

        $ctrl.ok = function () {
          $ctrl.close({$value: 'ok'});
        };

        $ctrl.cancel = function () {
          $ctrl.dismiss({$value: 'cancel'});
        };

      }

    });


})();
