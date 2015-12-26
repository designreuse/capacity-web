'use strict';

angular.module('capacityApp')
	.controller('CalendarController', ['$scope', function($scope) {
		$scope.eventSources = ['rest/calendar/events'];
	}]);
