'use strict';

angular.module('capacityApp')
	.config(['$routeProvider', function($routeProvider) {
		
		function addRoute(route, templateUrl, controller) {
			$routeProvider.when(route, {
				templateUrl: templateUrl,
				controller: controller
			});
		}
		
		addRoute('/', 'app/module/home/view/home.html', 'HomeController');
		
		addRoute('/employees', 'app/module/employee/view/list.html', 'EmployeeListController');
		addRoute('/employees/:id', 'app/module/employee/view/detail.html', 'EmployeeDetailController');

	}]);