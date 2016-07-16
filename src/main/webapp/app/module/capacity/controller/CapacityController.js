(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('CapacityController', CapacityController);

	CapacityController.$inject = ['$scope', '$http', 'Episode', '$filter'];

	function CapacityController($scope, $http, Episode, $filter) {

		// Boolean option: Whether or not to calculate the capacity using the velocity per employee or not
		$scope.useVelocity = true;
		// Selection option which display format to use (default "hours", other possible values "days" and "weeks")
		$scope.units = "hours";

		$scope.selected = 'chart';
		$scope.select = function(tab) {
			$scope.selected = tab;
		};

		$scope.switchUnits = function(units) {
			$scope.units = units;
			$scope.loadChart();
		};

		$scope.datepicker = {
			startOpened: false,
			endOpened: false
		};
		$scope.duration = {
			start: moment().add(-10, 'd').toDate(),
			end: moment().add(10, 'd').toDate()
		};

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
				categories: [],
				labels: {
					formatter: function () {
						return '<span style="fill: ' + this.value.color + ';">' + this.value.y + '</span>';
					}
				}
			},
			yAxis: {
				min: 0,
				title: {
					text: 'Capacity (' + $filter('limitTo')($scope.units, 1) + ')'
				}
			},
			series: [],
			credits: {
				enabled: false
			}
		};

		$scope.andFilters = {1: []};
		$scope.lastIndex = 1;
		$scope.addAndFilter = function() {
			$scope.lastIndex++;
			$scope.andFilters[$scope.lastIndex] = [];
		};
		$scope.removeAndFilter = function(id) {
			delete $scope.andFilters[id];
		};
		$scope.loadAbilities = function($query) {
			return $http.get('rest/abilities').then(function(response) {
				var abilities = response.data;
				return abilities.filter(function(abilitiy) {
					return abilitiy.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};

		$scope.loadChart = function() {
			var url = 'rest/capacity/workinghours';
			var data = {
				useVelocity: $scope.useVelocity,
				episodeId: $scope._selectedEpisode.id
			};
			if ($scope._selectedEpisode.id == '') {
				if ($scope.duration.start) {
					$scope.duration.start = new Date($scope.duration.start);
				}
				if ($scope.duration.end) {
					$scope.duration.end = new Date($scope.duration.end);
				}
				data.start = $scope.duration.start;
				data.end = $scope.duration.end;
			}
			var andFilters = Object.keys($scope.andFilters);
			if (andFilters.length > 0) {
				var filter = [];
				for (var k in $scope.andFilters) {
					if (!$scope.andFilters.hasOwnProperty(k)) {
						continue;
					}
					var andFilter = $scope.andFilters[k];
					if (andFilter.length > 0) {
						filter.push(andFilter);
					}
				}
				if (filter.length > 0) {
					data.filter = filter;
				}
			}
			$http.post(url, data).then(function(response) {
				var series = [];
				response.data.forEach(function(element, index) {
					var seriesvalues = [];
					element.workingHours.details.forEach(function(childelement, childindex) {
						var value = childelement.hours;
						if ($scope.units == 'hours') {
							// No calculation necessary
						} else if ($scope.units == 'days') {
							value /= 8;
						} else if ($scope.units == 'weeks') {
							value /= 40;
						}
						seriesvalues.push(value);
					});
					series.push({
						name: $scope.useVelocity ? (element.employee.name + ' (' + element.velocity + '%)') : element.employee.name,
						data: seriesvalues,
						color: element.employee.color
					});
				});
				$scope.chartConfig.series = series;
				$scope.chartConfig.yAxis.title.text = 'Capacity (' + $filter('limitTo')($scope.units, 1) + ')';

				var categories = [];
				if (response.data.length > 0) {
					response.data[0].workingHours.details.forEach(function(element, index) {
						var dayOfWeek = moment(element.date).day();
						categories.push({y: element.date, color: (dayOfWeek == 0 || dayOfWeek == 6) ? '#aaaaaa' : '#000000' });
					});
				}
				$scope.chartConfig.xAxis.categories = categories;

				if (response.data.length > 0) {
					$scope.chartConfig.title.text = 'Capacity (in ' + $scope.units + ') for ' + response.data[0].workingHours.from + ' - ' + response.data[0].workingHours.until;
				} else {
					$scope.chartConfig.title.text = 'No capacities';
				}
			});
		};

		Episode.query(function(episodes) {
			$scope.episodes = episodes;
			$scope.loadChart();
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
					if (episodeId === element.id) {
						$scope._selectedEpisode = element;
					}
				});
				$scope.loadChart();
			} else {
				return $scope._selectedEpisode.id + '';
			}
		};

		$scope.episodeName = function() {
			if ($scope._selectedEpisode && $scope._selectedEpisode.id) {
				return $scope._selectedEpisode.name + " " + $scope._selectedEpisode.start + " - " + $scope._selectedEpisode.end + ")";
			}
			return "None";
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

		$scope.isPast = function(date) {
			return moment(date).diff(moment(), 'minutes') < 0;
		}
	}
})();
