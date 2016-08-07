/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('UserListController', UserListController);

	UserListController.$inject = ['$location', '$rootScope', '$http', 'User', 'Role'];

	function UserListController($location, $rootScope, $http, User, Role) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.roleLookup = [];

		activate();

		function activate() {
			Role.query(function(roles) {
				angular.forEach(roles, function(element) {
					vm.roleLookup[element.id] = element;
				});
				User.query(function(users) {
					vm.users = users;
				});
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/users/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

	}
})();
