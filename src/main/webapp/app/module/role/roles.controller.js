/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('RoleListController', RoleListController);

	RoleListController.$inject = ['$location', '$rootScope', '$http', 'Role'];

	function RoleListController($location, $rootScope, $http, Role) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.isSelected = isSelected;

		activate();

		function activate() {
			Role.query(function(roles) {
				vm.roles = roles;
			});

			$http.get('rest/permissions').then(function(response) {
				vm.permissions = response.data;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/roles/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function isSelected(permission, role) {
			return role.permissions.indexOf(permission) !== -1;
		}

	}
})();
