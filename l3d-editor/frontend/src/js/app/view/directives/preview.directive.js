(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('preview', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/preview.directive.html',
      replace: true,
      scope: {
        definition: '='
      },
      link: function (scope, element) {

        var nativeElement = angular.element(element)[0];
        var room = new L3DEditor.Room(nativeElement, scope.definition);

        scope.$watch('definition', function () {
          room.update(scope.definition);
        }, true);
      }
    };

  });

})(L3DEditor);
