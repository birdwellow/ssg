(function (L3DEditor) {

  'use strict';


  angular.module('Editor').directive('preview', function () {

    return {
      restrict: 'E',
      templateUrl: 'js/app/view/directives/components/preview.directive.html',
      replace: true,
      scope: {
        definition: '='
      },
      link: function (scope, element) {

        
        var room;
        var nativeElement = angular.element(element)[0];

        scope.$watch('definition', function () {
          if (!room && scope.definition) {
            room = new L3DEditor.Room(nativeElement, scope.definition);
          }
          if (scope.definition) {
            room.update(scope.definition);
          }
        }, true);
      }
    };

  });

})(L3DEditor);
