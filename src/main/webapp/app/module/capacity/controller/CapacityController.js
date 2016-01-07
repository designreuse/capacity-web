'use strict';

angular.module('capacityApp')
	.controller('CapacityController', ['$scope', '$http', 'Employee', 'Episode', function($scope, $http, Employee, Episode) {

		$scope.useVelocity = true;

		$scope.selected = 'chart';
		$scope.select = function(tab) {
			$scope.selected = tab;
		}

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
				min: 0,
				title: {
					text: 'Capacity (h)'
				}
			},
			series: [],
			credits: {
				enabled: false
			}
		};

		$scope.onlyForSelectedAbilities = function(item) {
			if ($scope.abilities === undefined) {
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
		};

		$scope.onlyForSelectedEpisodes = function(item) {
			if ($scope._selectedEpisode.id === '') {
				return true;
			}
			var found = false;
			$scope._selectedEpisode.employeeEpisodes.forEach(function(element, index) {
				if (element.employee.id == item.id) {
					found = true;
				}
			});
			return found;
		};

		$scope.loadChart = function() {
			var url = 'rest/capacity/workinghours?useVelocity=' + $scope.useVelocity + '&episodeId=' + $scope._selectedEpisode.id;
			var filteredEmployees = $scope.employees.filter($scope.onlyForSelectedAbilities).filter($scope.onlyForSelectedEpisodes);
			if (filteredEmployees.length > 0) {
				var urlExtra = '';
				filteredEmployees.forEach(function(element, index) {
					if (element.selected) {
						urlExtra += '&employeeIds=' + element.id;
					}
				});
				if (urlExtra.length > 0) {
					url += urlExtra;
				}
			}
			$http.get(url).then(function(response) {
				var series = [];
				response.data.forEach(function(element, index) {
					var seriesvalues = [];
					element.workingHours.details.forEach(function(childelement, childindex) {
						seriesvalues.push(childelement.hours);
					});
					series.push({
						name: $scope.useVelocity ? (element.employee.name + ' (' + element.velocity + '%)') : element.employee.name,
						data: seriesvalues,
						color: element.employee.color
					});
				});
				$scope.chartConfig.series = series;

				var categories = [];
				if (response.data.length > 0) {
					response.data[0].workingHours.details.forEach(function(element, index) {
						categories.push(element.date);
					});
				}
				$scope.chartConfig.xAxis.categories = categories;

				if (response.data.length > 0) {
					$scope.chartConfig.title.text = 'Capacity for ' + response.data[0].workingHours.from + ' - ' + response.data[0].workingHours.until;
				} else {
					$scope.chartConfig.title.text = 'No capacities';
				}
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

		Episode.query(function(episodes) {
			$scope.episodes = episodes;
		});

		$scope._selectedEpisode = {
			id: ''
		};
		$scope.selectedEpisode = function(episodeId) {
			if (arguments.length) {
				$scope._selectedEpisode = {
					id: ''
				};
				angular.forEach($scope.episodes, function(element, index) {
					if (episodeId == element.id) {
						$scope._selectedEpisode = element;
					}
				});
				$scope.loadChart();
			} else {
				return $scope._selectedEpisode.id + '';
			}
		};

		$scope.sum = function(values) {
			var result = 0;
			values.forEach(function(element, index) {
				result += element;
			});
			return result;
		};

		$scope.rowsum = function(columnIndex) {
			var result = 0;
			$scope.chartConfig.series.forEach(function(element, index) {
				result += element.data[columnIndex];
			});
			return result;
		};

		$scope.sumsum = function() {
			var result = 0;
			$scope.chartConfig.series.forEach(function(element, index) {
				element.data.forEach(function(subelement, subindex) {
					result += subelement;
				});
			});
			return result;
		};

	}]);
