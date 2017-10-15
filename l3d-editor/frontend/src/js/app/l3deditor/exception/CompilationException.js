CompilationException = (function () {

  'use strict';

  return function (message) {
    this.message = message;
    this.name = 'CompilationException';
  };

}) ();
