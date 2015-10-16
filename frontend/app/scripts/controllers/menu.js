'use strict';

/**
 * @ngdoc function
 * @name sokApp.controller:MenuctrlCtrl
 * @description
 * # MenuctrlCtrl
 * Controller of the sokApp
 */
 angular.module('sokApp')
 .controller('MenuCtrl', 
  ['$location',
  function ($location) {
    var vm = this;
    
    vm.navbarCollapsed = true;

    vm.isActive = function (viewLocation) {
      var regex = new RegExp("^/" +viewLocation, "i");
      return (regex.test($location.path()));
    };

  }]);
