(function () {

  'use strict';


  angular.module('Editor').directive('textareaKeyhandler', function ($rootScope) {

    return function (scope, element) {

      var attrName = angular.element(element).controller('ngModel').$$attr.ngModel;

      var listener = function (event) {
        event.stopPropagation();
        if (event.keyCode == 9) {
          event.preventDefault();
          var nativeElement = element[0];
          var start = nativeElement.selectionStart;
          var end = nativeElement.selectionEnd;

          var content = nativeElement.value;
          var newContent = content.substring(0, start) + '\t' + content.substring(end);

          // set textarea value to: text before caret + tab + text after caret
          // This currently destroys the ngModel binding, since the value is modified directly and not the ngModel
          element.val(newContent);

          // // put caret at right position again
          nativeElement.selectionStart =
          nativeElement.selectionEnd = start + 1;
        }
      };

      // element[0].addEventListener('keypress', listener);
      element.bind('keydown', listener);
    };

  });

})();
