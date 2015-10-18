'use strict';

/**
 * @ngdoc function
 * @name sokApp.controller:AlertCtrl
 * @description
 * # AlertCtrl
 * Controller of the sokApp
 */
angular.module('sokApp')
  .controller('AlertCtrl', 
  	[ 'Alerts',
  	function (Alerts) {
      var vm = this;

      vm.list = Alerts.list;
      vm.count = Alerts.count;
      vm.close = Alerts.close;
      vm.add = Alerts.add;
      
    }]);
