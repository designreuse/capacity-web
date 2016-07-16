(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EmployeeListController', EmployeeListController);

	EmployeeListController.$inject = ['$location', '$rootScope', 'Employee'];

	function EmployeeListController($location, $rootScope, Employee) {
		/* jshint validthis: true */
		var vm = this;
		
		Employee.query(function(employees) {
			vm.employees = employees;
		});

		vm.add = function() {
			$location.path('/employees/new');
		};

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = function(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		};

		vm.searchFilter = function(string) {
			$rootScope.search = string;
		};
	}
})();
