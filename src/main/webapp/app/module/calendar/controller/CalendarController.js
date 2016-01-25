'use strict';

angular.module('capacityApp')
	.controller('CalendarController', ['$scope', '$http', 'uiCalendarConfig', function($scope, $http, uiCalendarConfig) {

		$scope.calendars = [
			{ id: 'holidays', name: 'Holidays', selected: true },
			{ id: 'employees', name: 'Employees', selected: true }
		];

		var publishEvents = function(index) {
			if ($scope.calendars[index].selected) {
				$scope.eventSources[index] = $scope.allEvents[index];
			} else {
				$scope.eventSources[index] = [];
			}
		};

		$scope.publishAllEvents = function() {
			publishEvents(0);
			publishEvents(1);
		};

		$scope.start = moment().startOf('month').format('YYYY-MM-DD');
		$scope.end = moment().endOf('month').format('YYYY-MM-DD');
		$scope.eventSources = [];
		$scope.allEvents = [[],[]];
		$http.get('rest/calendar/events/holidays?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'holidays';
			});
			$scope.allEvents[0] = response.data;
			publishEvents(0);
		});
		$http.get('rest/calendar/events/absences?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
			});
			$scope.allEvents[1] = response.data;
			publishEvents(1);
		});
		// Ideally we would do this: $scope.eventSources = [ 'rest/calendar/events' ];
	}]);
