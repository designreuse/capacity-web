'use strict';

angular.module('capacityApp')
	.controller('CalendarController', ['$scope', '$http', function($scope, $http) {

		$scope.calendars = [
			{ id: 'holidays', name: 'Holidays' },
			{ id: 'employees', name: 'Employees' }
		];

		$scope.start = moment().startOf('month').format('YYYY-MM-DD');
		$scope.end = moment().endOf('month').format('YYYY-MM-DD');
		$scope.eventSources = [];
		$http.get('rest/calendar/events/holidays?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'holidays';
			});
			$scope.eventSources.push(response.data);
		});
		$http.get('rest/calendar/events/absences?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
			});
			$scope.eventSources.push(response.data);
		});
		// Ideally we would do this: $scope.eventSources = [ 'rest/calendar/events' ];
	}]);
