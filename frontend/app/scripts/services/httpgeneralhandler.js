'use strict';

/**
 * @ngdoc service
 * @name sokApp.httpGeneralHandler
 * @description
 * # httpGeneralHandler
 * Factory in the sokApp.
 */
angular.module('sokApp')
  .factory('httpGeneralHandler', 
  [ '$q', 'Alerts', 'HTTP',
  function ($q, Alerts, HTTP) {

    return {
      responseError: function (rejection) {
        switch (rejection.status) {
          case HTTP.INTERNAL_SERVER_ERROR:
            Alerts.add("danger", "Wystąpił błąd po stronie serwera, spróbuj ponownie później!");
            break;
          case HTTP.NOT_FOUND:
            Alerts.add("danger", "Nie znaleziono obiektu.");
            break;
          default:
            Alerts.add("danger", "Wystąpił błąd.");
            break;
        }

        return $q.reject(rejection);
      }
    };
  }]);