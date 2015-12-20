'use strict';

/**
* @ngdoc function
* @name sokApp.controller:AboutCtrl
* @description
* # AboutCtrl
* Controller of the sokApp
*/
angular.module('sokApp')
.controller('TasksCtrl', 
  ['$http', 'SokApi', 'resourceData',
  function ($http, SokApi, resourceData) {
    var vm = this;

    function saveAnswer() {
      SokApi.answer.update({content: vm.currentAnswer.content, token: vm.user.token, taskId: vm.selected.id}).$promise
      .then(function() {
        vm.answerForm.$setPristine();
        getRating(vm.selected.id);
      });
    }

    function getRating(task_id) {
      SokApi.rating.get({taskId: task_id, token: vm.user.token }).$promise
      .then( function(data) {
        vm.latestRating = data;
      }, function() {
        vm.latestRating = null;
      })
    }

    function getAnswer(task_id) {
      vm.currentAnswer = SokApi.answer.get({taskId: task_id, token: vm.user.token});
      if (vm.answerForm) { vm.answerForm.$setPristine(); }
      vm.oldSelected = vm.selected; // keep reference to first-selected item
    }

    function changeTask() {
      if (vm.answerForm.taskAnswer.$dirty && !confirm('Przechodząc dalej, stracisz niezapisane zmiany. Chcesz kontynuować?')) {
        vm.selected = vm.oldSelected;
      } else {
        getAnswer(vm.selected.id);
        getRating(vm.selected.id);
      }
    }

    vm.user = resourceData.user;
    vm.taskList = resourceData.taskList;
    vm.latestRating = null;
    vm.currentAnswer = null;
    vm.oldSelected = null;
    vm.introCollapsed = false;
    vm.selected = vm.taskList[0];
    vm.getAnswer = getAnswer;
    vm.saveAnswer = saveAnswer;
    vm.changeTask = changeTask;
    getAnswer(vm.selected.id);
    getRating(vm.selected.id);

  }]);
