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
      });
    }

    function getAnswer(task_id) {
      vm.currentAnswer = SokApi.answer.get({taskId: task_id, token: vm.user.token});
      if (vm.answerForm) { vm.answerForm.$setPristine(); }
      vm.oldSelected = vm.selected; // keep reference to first-selected item
    }

    function confirmChange() {
      if (vm.answerForm.taskAnswer.$dirty && !confirm('Przechodząc dalej, stracisz niezapisane zmiany. Chcesz kontynuować?')) {
        vm.selected = vm.oldSelected;
      } else {
        getAnswer(vm.selected.id);
      }
    }

    vm.user = resourceData[0];
    vm.taskList = resourceData[1];
    vm.currentAnswer = null;
    vm.selected = vm.taskList[0];
    vm.oldSelected = null;
    vm.introCollapsed = false;
    vm.getAnswer = getAnswer;
    vm.saveAnswer = saveAnswer;
    vm.confirmChange = confirmChange;
    getAnswer(vm.selected.id);

  }]);
