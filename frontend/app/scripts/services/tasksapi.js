'use strict';

/**
 * @ngdoc service
 * @name sokApp.SokApi
 * @description
 * # SokApi
 * Factory in the sourcesApp.
 */
angular.module('sokApp')
  .factory('SokApi', 
    ['$resource', 'httpAnswerHandler', 'httpRegistrationHandler', 'httpGeneralHandler',
    function ($resource, httpAnswerHandler, httpRegistrationHandler, httpGeneralHandler) {
  
      //@if env='production'
      var apiAddress = 'http://sok.ds.pg.gda.pl/backend';
      //@endif
      //@if env!='production'
      var apiAddress = 'http://sok-dev.ds.pg.gda.pl/backend';
      //@endif

      return {
        tasks: $resource(apiAddress + '/tasks', {}, {
          get: {interceptor: httpGeneralHandler}
        }),
        user:  $resource(apiAddress + '/user/:token', {}, {
          save: {method: 'PUT', interceptor: httpRegistrationHandler},
          get: {interceptor: httpGeneralHandler}
        }),
        answer: $resource(apiAddress + '/answer/:taskId/:token', {}, {
          update: {method: 'POST', interceptor: httpAnswerHandler}
        })
      };
    }]);
