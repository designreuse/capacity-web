'use strict';

angular.module('capacityApp')
	.controller('AbsenceController', ['$scope', '$http', function($scope, $http) {
		$http.get('rest/absent').then(function(response) {
			$scope.absent = response.data;
		});
		$http.get('rest/available').then(function(response) {
			$scope.available = response.data;
		});
	}]);