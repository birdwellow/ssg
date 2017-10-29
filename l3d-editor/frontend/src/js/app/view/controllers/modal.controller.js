(function () {

  'use strict';


  angular
    .module('Editor')
    .controller('ModalInstanceCtrl', function ($uibModalInstance) {

      var $ctrl = this;

      $ctrl.ok = function () {
        console.log('ok');
        $uibModalInstance.close('ok');
      };

      $ctrl.cancel = function () {
        console.log('cancel');
        $uibModalInstance.dismiss('cancel');
      };

  });


})();
