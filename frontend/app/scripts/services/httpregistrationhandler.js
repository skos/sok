'use strict';

/**
 * @ngdoc service
 * @name sokApp.httpErrorHandler
 * @description
 * # httpErrorHandler
 * Factory in the sokApp.
 */
 angular.module('sokApp')
 .factory('httpRegistrationHandler', 
  [ '$q', 'Alerts', 'HTTP',
  function ($q, Alerts, HTTP) {

    return {
      responseError: function (rejection) {
        switch (rejection.status) {
          case HTTP.INTERNAL_SERVER_ERROR:
            Alerts.add("danger", "Wystąpił błąd po stronie serwera, spróbuj ponownie później!");
            break;
          case HTTP.NOT_FOUND:
            Alerts.add("danger", "Nie znaleziono zadania.");
            break;
          case HTTP.CONFLICT:
            Alerts.add("danger", "Użytkownik o takim adresie e-mail już istnieje!");
            break;
        }

        return $q.reject(rejection);
      }
    };
  }]);
