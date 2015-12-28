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

		addRoute('/calendar', 'app/module/calendar/view/calendar.html', 'CalendarController');

		addRoute('/capacity', 'app/module/capacity/view/capacity.html', 'CapacityController');

		addRoute('/absence', 'app/module/absence/view/absence.html', 'AbsenceController');

	}]);