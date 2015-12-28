'use strict';

angular.module('capacityApp')
	.controller('CapacityController', ['$scope', '$http', 'Employee', function($scope, $http, Employee) {

		$scope.chartConfig = {
			options: {
				chart: {
					type: 'column',
					animation: false
				},
				plotOptions: {
					column: {
						stacking: 'normal'
					},
					series: {
						animation: false
					}
				}
			},
			title: {
				text: ''
			},
			xAxis: {
				categories: []
			},
			yAxis: {
				min: 0
			},
			series: [],
			credits: {
				enabled: false
			}
		};

		$scope.onlyForSelectedAbilities = function(item) {
			if ($scope.abilities == undefined) {
				return true;
			}
			var found = false;
			var anySelected = false;
			$scope.abilities.forEach(function(element, index) {
				if (element.selected) {
					anySelected = true;
					item.abilities.forEach(function(childelement, childindex) {
						if (element.name == childelement.name) {
							found = true;
						}
					});
				}
			});
			if (!anySelected) {
				found = true;
			}
			return found;
		}

		$scope.loadChart = function() {
			var url = 'rest/capacity/workinghours';
			var filteredEmployees = $scope.employees.filter($scope.onlyForSelectedAbilities);
			if (filteredEmployees.length > 0) {
				var urlExtra = '';
				filteredEmployees.forEach(function(element, index) {
					if (element.selected) {
						if (urlExtra.length > 0) {
							urlExtra += '&';
						}
						urlExtra += 'employeeIds=' + element.id;
					}
				});
				if (urlExtra.length > 0) {
					url += '?' + urlExtra;
				}
			}
			$http.get(url).then(function(response) {
				var categories = [];
				response.data[0].workingHours.details.forEach(function(element, index) {
					categories.push(element.date)
				});
				var series = [];
				response.data.forEach(function(element, index) {
					var seriesvalues = []
					element.workingHours.details.forEach(function(childelement, childindex) {
						seriesvalues.push(childelement.hours);
					});
					series.push({
						name: element.employee.name,
						data: seriesvalues,
						color: element.employee.color
					});
				});
				$scope.chartConfig.xAxis.categories = categories;
				$scope.chartConfig.series = series;
				$scope.chartConfig.title.text = 'Capacity for ' + response.data[0].workingHours.from + ' - ' + response.data[0].workingHours.until;
			});
		};
		
		$http.get('rest/abilities').then(function(response) {
			$scope.abilities = response.data;
		});

		Employee.query(function(employees) {
			employees.forEach(function(element, index) {
				element.selected = true;
			});
			$scope.employees = employees;

			$scope.loadChart();
		});

	}]);
