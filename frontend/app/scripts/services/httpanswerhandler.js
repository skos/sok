'use strict';

/**
 * @ngdoc service
 * @name sokApp.httpAnswerHandler
 * @description
 * # httpAnswerHandler
 * Factory in the sokApp.
 */
 angular.module('sokApp')
 .factory('httpAnswerHandler', 
  [ '$q', 'Alerts', 'HTTP',
  function ($q, Alerts, HTTP) {

    return {
      response: function(response) {
        switch(response.status) {
          case HTTP.CREATED:
            Alerts.add("success", "Odpowiedź zapisana.");
            break;
          case HTTP.ACCEPTED:
            Alerts.add("success", "Odpowiedź zaktualizowana.");
            break;
        }

        return response;
      },

      responseError: function (rejection) {
        switch (rejection.status) {
          case HTTP.INTERNAL_SERVER_ERROR:
            Alerts.add("danger", "Wystąpił błąd po stronie serwera, spróbuj ponownie później!");
            break;
          case HTTP.NOT_FOUND:
            Alerts.add("danger", "Nie znaleziono zadania.");
            break;
        }

        return $q.reject(rejection);
      }
    }
  }]);
