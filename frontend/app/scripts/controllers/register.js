'use strict';

/**
 * @ngdoc function
 * @name sokApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the sokApp
 */
 angular.module('sokApp')
 .controller('RegisterCtrl', 
  ['$http', 'SokApi',
  function ($http, SokApi) {
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
