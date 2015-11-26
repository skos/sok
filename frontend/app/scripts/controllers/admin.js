'use strict';

/**
 * @ngdoc function
 * @name sokApp.controller:AdminCtrl
 * @description
 * # AdminCtrl
 * Controller of the sokApp
 */
 angular.module('sokApp')
 .controller('AdminCtrl',
    [ 'resourceData', 'SokApi',
    function (resourceData, SokApi) {
      var vm = this;

      function loadAnswers() {
        SokApi.answer.query({
            taskId:     vm.selectedTask.id, 
            token:      vm.selectedCandidate.token,
            authToken:  vm.user.token
        }).$promise
        .then( function(data) {
            vm.answers = data;
        }, function() {
            vm.answers = false;
        })
        .finally( function () {
            vm.ratingForm.$setPristine();
        });
      }

      function loadRating() {
        SokApi.rating.get({
            taskId:     vm.selectedTask.id, 
            token:      vm.selectedCandidate.token,
            authToken:  vm.user.token
        }).$promise
        .then( function(data) {
          vm.ratingHistory = data;
        },
        function() {
          vm.ratingHistory = false;
        });
      }

      function saveRating() {
        SokApi.rating.post({
          taskId:       vm.selectedTask.id,
          token:        vm.selectedCandidate.token,
          authToken:    vm.user.token,
          comment:      vm.ratingForm.comment,
          rating:       vm.ratingForm.rate
        })
      }

      vm.user = resourceData.user;
      vm.taskList = resourceData.taskList;
      vm.selectedTask = vm.taskList[0];
      vm.candidates = SokApi.candidates.query({authToken: vm.user.token});
      vm.candidates.$promise
      .then(function(data) {
        vm.selectedCandidate = data[0];
        loadAnswers();
        loadRating();
      });

      vm.loadAnswers = loadAnswers;
      vm.loadRating = loadRating;

  }]);
