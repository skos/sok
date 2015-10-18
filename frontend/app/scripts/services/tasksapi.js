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
    ['$resource',
    function ($resource) {
  
      var apiAddress = 'http://localhost:8080/backend';
  
      return {
        tasks: $resource(apiAddress + '/tasks'),
        user:  $resource(apiAddress + '/user/:token', {}, {
          save: {method: 'PUT'}
        }),
        answer: $resource(apiAddress + '/answer/:taskId/:token', {}, {
          update: {method: 'POST'}
        })
      }
    }]);
