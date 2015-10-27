'use strict';

/**
 * @ngdoc function
 * @name sokApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sokApp
 */
 angular.module('sokApp')
 .controller('MainCtrl', 
  ['$http', 'SokApi', 'Alerts',
  function ($http, SokApi, Alerts) {
    var vm = this;

    vm.registered = false;
    vm.error = false;
    vm.user = {name: '', email: ''};

    vm.registerUser = function() {
      if (vm.registerForm.$valid) {

        SokApi.user.save(vm.user).$promise
        .then(
          function() {
            vm.registered = true;
          });
      }
    };

  }]);
