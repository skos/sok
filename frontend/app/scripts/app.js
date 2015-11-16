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
          templateUrl: 'views/register.html',
          controller: 'RegisterCtrl',
          controllerAs: 'register'
        })
        .when('/tasks/:token', {
          templateUrl: 'views/tasks.html',
          controller: 'TasksCtrl',
          controllerAs: 'tasks',
          resolve: {
            resourceData: function($q, $route, SokApi) {

              var user = SokApi.user.get({token: $route.current.params.token});
              var taskList = SokApi.tasks.query();

              return $q.all([user.$promise, taskList.$promise]);
            }
          }
        })
        .when('/contact', {
          templateUrl: 'views/contact.html'
        })
        .when('/error', {
          templateUrl: 'views/error.html'
        })
        .otherwise({
          redirectTo: '/'
        });
  }]);

