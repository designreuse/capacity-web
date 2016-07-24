/// <reference path="../../../../../typings/globals/angular/index.d.ts" />
/// <reference path="../../../../../typings/globals/angular-route/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.config(config);

	config.$inject = ['$routeProvider'];

	/**
	 * @param {ng.route.IRouteProvider} $routeProvider
	 */
	function config($routeProvider) {

		/**
		 * @param {string} route
		 * @param {string} templateUrl
		 * @param {string} controller
		 */
		function addRoute(route, templateUrl, controller) {
			$routeProvider.when(route, {
				templateUrl: templateUrl,
				controller: controller,
				controllerAs: 'vm'
			});
		}

		addRoute('/', 'app/module/home/view/home.html', 'HomeController');

		addRoute('/employees', 'app/module/employee/view/employees.html', 'EmployeeListController');
		addRoute('/employees/:id', 'app/module/employee/view/employee.html', 'EmployeeDetailController');

		addRoute('/calendar', 'app/module/calendar/view/calendar.html', 'CalendarController');

		addRoute('/episodes', 'app/module/episode/view/episodes.html', 'EpisodeListController');
		addRoute('/episodes/:id', 'app/module/episode/view/episode.html', 'EpisodeDetailController');
		addRoute('/episodes/clone/:id', 'app/module/episode/view/episode.html', 'EpisodeDetailController');

		addRoute('/ical_imports', 'app/module/ical_import/view/ical_imports.html', 'IcalImportListController');
		addRoute('/ical_imports/:id', 'app/module/ical_import/view/ical_import.html', 'IcalImportDetailController');

		addRoute('/capacity', 'app/module/capacity/view/capacity.html', 'CapacityController');

		addRoute('/absence', 'app/module/absence/view/absence.html', 'AbsenceController');

		addRoute('/roles', 'app/module/role/roles.html', 'RoleListController');
		addRoute('/roles/:id', 'app/module/role/role.html', 'RoleDetailController');

	}

})();
