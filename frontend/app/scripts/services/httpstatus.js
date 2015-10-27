'use strict';

/**
 * @ngdoc service
 * @name sokApp.HTTPERRORS
 * @description
 * # HTTPERRORS
 * Constant in the sokApp.
 */
angular.module('sokApp')
  .constant('HTTP', {
    OK:                     200,
    CREATED:                201,
    ACCEPTED:               202,
    FORBIDDEN:              403,
    NOT_FOUND:              404,
    CONFLICT:               409, 
    INTERNAL_SERVER_ERROR:  500
  });
