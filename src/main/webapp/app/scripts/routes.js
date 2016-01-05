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

		addRoute('/employees', 'app/module/employee/view/employees.html', 'EmployeeListController');
		addRoute('/employees/:id', 'app/module/employee/view/employee.html', 'EmployeeDetailController');

		addRoute('/calendar', 'app/module/calendar/view/calendar.html', 'CalendarController');

		addRoute('/episodes', 'app/module/episode/view/episodes.html', 'EpisodeListController');
		addRoute('/episodes/:id', 'app/module/episode/view/episode.html', 'EpisodeDetailController');

		addRoute('/capacity', 'app/module/capacity/view/capacity.html', 'CapacityController');

		addRoute('/absence', 'app/module/absence/view/absence.html', 'AbsenceController');

	}]);