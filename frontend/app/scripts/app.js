'use strict';

/**
 * @ngdoc overview
 * @name sokApp
 * @description
 * # sokApp
 *
 * Main module of the application.
 */
angular
  .module('sokApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config([
    '$routeProvider',
    function ($routeProvider) {
      $routeProvider
        .when('/', {
          templateUrl: 'views/main.html',
          controller: 'MainCtrl',
          controllerAs: 'main'
        })
        .when('/tasks/:token', {
          templateUrl: 'views/tasks.html',
          controller: 'TasksCtrl',
          controllerAs: 'tasks'
        })
        .when('/contact', {
          templateUrl: 'views/contact.html'
        })
        .otherwise({
          redirectTo: '/'
        });
  }]);

