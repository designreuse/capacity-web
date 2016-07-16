(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('AbsenceController', AbsenceController);

	AbsenceController.$inject = ['$scope', '$http'];

	function AbsenceController($scope, $http) {
		$scope.selectedDate = new Date();
		$scope.datepicker = {
			opened: false
		};

		$scope.loadAbsencesAndAvailabilities = function() {
			if (typeof $scope.selectedDate.getMonth === 'function') {
				$scope.selectedDate = moment($scope.selectedDate).format('YYYY-MM-DD');
			}
			$http.get('rest/absent?date=' + $scope.selectedDate).then(function(response) {
				$scope.absent = response.data;
			});

			$http.get('rest/available?date=' + $scope.selectedDate).then(function(response) {
				$scope.available = response.data;
			});
		};

		$scope.loadAbsencesAndAvailabilities();
	}
})();
