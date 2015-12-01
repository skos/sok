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
    .when('/tasks/:authToken', {
      templateUrl: 'views/tasks.html',
      controller: 'TasksCtrl',
      controllerAs: 'tasks',
      resolve: {
        resourceData: function($q, $route, SokApi) {

          var user = SokApi.user.get({token: $route.current.params.authToken});
          var taskList = SokApi.tasks.query();

          return $q.all({user: user.$promise, taskList: taskList.$promise});
        }
      }
    })
    .when('/contact', {
      templateUrl: 'views/contact.html'
    })
    .when('/error', {
      templateUrl: 'views/error.html'
    })
    .when('/admin/:authToken', {
      templateUrl: 'views/admin.html',
      controller: 'AdminCtrl',
      controllerAs: 'admin',
      resolve: {
        resourceData: function($q, $route, SokApi) {

          var user = SokApi.user.get({token: $route.current.params.authToken});
          var taskList = SokApi.tasks.query();

          return $q.all({user: user.$promise, taskList: taskList.$promise})
          .then(function(response) {
            if(!response.user.canAdmin) {
              return $q.reject("Not autorized!");
            } else {
              return response;
            }
          })
        }
      }
    })
    .otherwise({
      redirectTo: '/'
    });
  }]);

