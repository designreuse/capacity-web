'use strict';

angular.module('capacityApp')
	.controller('CalendarController', ['$scope', '$http', function($scope, $http) {

		$scope.calendars = [
			{ id: 'holidays', name: 'Holidays' },
			{ id: 'employees', name: 'Employees' }
		];

		$scope.eventSources = [];
		$http.get('rest/calendar/events/holidays?start=2015-12-01&end=2015-12-31').then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'holidays';
			});
			$scope.eventSources.push(response.data);
		});
		$http.get('rest/calendar/events/employees?start=2015-12-01&end=2015-12-31').then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
			});
			$scope.eventSources.push(response.data);
		});
		// Ideally we would do this: $scope.eventSources = [ 'rest/calendar/events' ];
	}]);
