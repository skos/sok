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
  ['$location', '$scope',
  function ($location, $scope) {
      var vm = this;

      $scope.$on("$routeChangeError", function () {
        $location.path('error')
    });
  }]);
