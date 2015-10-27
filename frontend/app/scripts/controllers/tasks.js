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
  ['$http','$routeParams', '$window', 'SokApi',
  function ($http, $routeParams, $window, SokApi) {
    var vm = this;

    function saveAnswer() {
      SokApi.answer.update({content: vm.currentAnswer.content, candidateId: vm.user.id, taskId: vm.selected.id}).$promise
      .then(function() {
        vm.answerForm.$setPristine();
      });
    }

    function getAnswer(task_id) {
      vm.currentAnswer = SokApi.answer.get({taskId: task_id, token: $routeParams.token});
      vm.answerForm.$setPristine();
      vm.oldVal = vm.selected;
    }

    function confirmChange() {
      if (vm.answerForm.taskAnswer.$dirty && !confirm('Przechodząc dalej, stracisz niezapisane zmiany. Chcesz kontynuować?')) {
        vm.selected = vm.oldVal;
      } else {
        getAnswer(vm.selected.id);
      }
    }

    function init() {
      vm.user = SokApi.user.get({token: $routeParams.token});
      vm.taskList = SokApi.tasks.query();
      vm.taskList.$promise
      .then(function (){
        vm.selected = vm.taskList[0];
        getAnswer(vm.selected.id);
      });
    }

    vm.user = null;
    vm.taskList = null;
    vm.currentAnswer = null;
    vm.selected = null;
    vm.oldVal = null;
    vm.introCollapsed = false;
    vm.getAnswer = getAnswer;
    vm.saveAnswer = saveAnswer;
    vm.confirmChange = confirmChange;

    init();

  }]);
