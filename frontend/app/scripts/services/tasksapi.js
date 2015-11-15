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
  
      var apiAddress = '/* @echo apiAddress */';
      //@exclude
      var apiAddress = 'http://localhost:8080/backend'; // jshint ignore:line
      //@endexclude

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
