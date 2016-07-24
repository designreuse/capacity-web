/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('AbsenceController', AbsenceController);

	AbsenceController.$inject = ['$http'];

	/**
	 * @param {ng.IHttpService} $http
	 */
	function AbsenceController($http) {
		/* jshint validthis: true */
		var vm = this;

		vm.selectedDate = new Date();
		vm.datepicker = {
			opened: false
		};

		vm.loadAbsencesAndAvailabilities = function() {
			if (typeof vm.selectedDate.getMonth === 'function') {
				vm.selectedDate = moment(vm.selectedDate).format('YYYY-MM-DD');
			}
			$http.get('rest/absent?date=' + vm.selectedDate).then(function(response) {
				vm.absent = response.data;
			});

			$http.get('rest/available?date=' + vm.selectedDate).then(function(response) {
				vm.available = response.data;
			});
		};

		vm.loadAbsencesAndAvailabilities();
	}
})();
