(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('UserDetailController', UserDetailController);

	UserDetailController.$inject = ['$route', '$location', '$http', 'User'];

	function UserDetailController($route, $location, $http, User) {
		/* jshint validthis: true */
		var vm = this;

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.user = new User();
				vm.save = function() {
					if (vm.user.password == '') {
						vm.user.password = null;
					}
					delete vm.user.passwordRepeated;
					vm.user.$save(function() {
						$location.path('/users');
					});
				};
			} else {
				User.get({id: $route.current.params.id}).$promise.then(function(user) {
					vm.user = user;
				});
				vm.save = function() {
					if (vm.user.password == '') {
						vm.user.password = null;
					}
					delete vm.user.passwordRepeated;
					vm.user.$update(function() {
						$location.path('/users');
					});
				};
			}
		}
	}
})();
