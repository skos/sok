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
  [ 'resourceData', 'SokApi', '$q',
  function (resourceData, SokApi, $q) {
    var vm = this;

    function loadAll() {
      $q.all([
        loadAnswers(),
        loadRating()
        ])
      .then(function () {
        vm.ratingForm.$setPristine();
      });
    }

    function loadAnswers() {
      return SokApi.answer.query({
        taskId:     vm.selectedTask.id, 
        token:      vm.selectedCandidate.token,
        authToken:  vm.user.token
      }).$promise
      .then( function(data) {
        vm.answers = data;
      }, function() {
        vm.answers = false;
      });
    }

    function loadRating() {
      console.log('loadRating...')
      return SokApi.rating.query({
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
      console.log('saveRating...');
      SokApi.rating.save({
        taskId:       vm.selectedTask.id,
        token:        vm.selectedCandidate.token,
        authToken:    vm.user.token,
        comment:      vm.ratingForm.comment,
        rating:       vm.ratingForm.rating
      }).$promise
      .then (loadRating());
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

    vm.loadAnswers = loadAll;
    vm.saveRating = saveRating;

  }]);
