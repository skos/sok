'use strict';

/**
 * @ngdoc service
 * @name sokApp.Alerts
 * @description
 * # Alerts
 * Factory in the sokApp.
 */
angular.module('sokApp')
  .factory('Alerts', function () {
    
    var alerts = [];

    var addAlert = function(type, message) {
    	alerts.push({type: type, message: message});
    };

    var close = function(index) {
    	alerts.splice(index, 1);
    };

    return {
      add: addAlert,
      close: close,
      count: function() {
      	return alerts.length;
      },
      list: alerts

    };
  });
