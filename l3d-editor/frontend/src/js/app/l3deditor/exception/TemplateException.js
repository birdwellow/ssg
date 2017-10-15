TemplateException = (function () {

  'use strict';

  return function (message) {
    this.message = message;
    this.name = 'TemplateException';
  };

}) ();
