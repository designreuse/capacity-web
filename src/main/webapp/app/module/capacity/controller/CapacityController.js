(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('CapacityController', CapacityController);

	CapacityController.$inject = ['$http', 'Episode', '$filter'];

	function CapacityController($http, Episode, $filter) {
		/* jshint validthis: true */
		var vm = this;

		vm.list = [];

		// Boolean option: Whether or not to calculate the capacity using the velocity per employee or not
		vm.useVelocity = true;
		// Selection option which display format to use (default "hours", other possible values "days" and "weeks")
		vm.units = "hours";

		vm.selected = 'chart';
		vm.select = function(tab) {
			vm.selected = tab;
		};

		vm.switchUnits = function(units) {
			vm.units = units;
			vm.loadChart();
		};

		vm.datepicker = {
			startOpened: false,
			endOpened: false
		};
		vm.duration = {
			start: moment().add(-10, 'd').toDate(),
			end: moment().add(10, 'd').toDate()
		};

		vm.chartConfig = {
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
					text: 'Capacity (' + $filter('limitTo')(vm.units, 1) + ')'
				}
			},
			series: [],
			credits: {
				enabled: false
			}
		};

		vm.andFilters = {1: []};
		vm.lastIndex = 1;
		vm.addAndFilter = function() {
			vm.lastIndex++;
			vm.andFilters[vm.lastIndex] = [];
			vm.loadChart();
		};
		vm.removeAndFilter = function(id) {
			delete vm.andFilters[id];
			vm.loadChart();
		};
		vm.loadAbilities = function($query) {
			return $http.get('rest/abilities').then(function(response) {
				var abilities = response.data;
				return abilities.filter(function(abilitiy) {
					return abilitiy.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};

		vm.loadChart = function() {
			var url = 'rest/capacity/workinghours';
			var data = {
				useVelocity: vm.useVelocity,
				episodeId: vm._selectedEpisode.id
			};
			if (vm._selectedEpisode.id == '') {
				if (vm.duration.start) {
					vm.duration.start = new Date(vm.duration.start);
				}
				if (vm.duration.end) {
					vm.duration.end = new Date(vm.duration.end);
				}
				data.start = vm.duration.start;
				data.end = vm.duration.end;
			}
			var andFilters = Object.keys(vm.andFilters);
			if (andFilters.length > 0) {
				var filter = [];
				for (var k in vm.andFilters) {
					if (!vm.andFilters.hasOwnProperty(k)) {
						continue;
					}
					var andFilter = vm.andFilters[k];
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
						if (vm.units == 'hours') {
							// No calculation necessary
						} else if (vm.units == 'days') {
							value /= 8;
						} else if (vm.units == 'weeks') {
							value /= 40;
						}
						seriesvalues.push(value);
					});
					series.push({
						name: vm.useVelocity ? (element.employee.name + ' (' + element.velocity + '%)') : element.employee.name,
						data: seriesvalues,
						color: element.employee.color
					});
				});
				vm.chartConfig.series = series;
				vm.chartConfig.yAxis.title.text = 'Capacity (' + $filter('limitTo')(vm.units, 1) + ')';

				var categories = [];
				if (response.data.length > 0) {
					response.data[0].workingHours.details.forEach(function(element, index) {
						var dayOfWeek = moment(element.date).day();
						categories.push({y: element.date, color: (dayOfWeek == 0 || dayOfWeek == 6) ? '#aaaaaa' : '#000000' });
					});
				}
				vm.chartConfig.xAxis.categories = categories;

				if (response.data.length > 0) {
					vm.chartConfig.title.text = 'Capacity (in ' + vm.units + ') for ' + response.data[0].workingHours.from + ' - ' + response.data[0].workingHours.until;
				} else {
					vm.chartConfig.title.text = 'No capacities';
				}
			});
		};

		Episode.query(function(episodes) {
			vm.episodes = episodes;
			vm.loadChart();
		});

		vm._selectedEpisode = {
			id: ''
		};
		vm.selectedEpisode = function(episodeId) {
			if (arguments.length) {
				vm._selectedEpisode = {
					id: ''
				};
				angular.forEach(vm.episodes, function(element, index) {
					if (episodeId === element.id) {
						vm._selectedEpisode = element;
					}
				});
				vm.loadChart();
			} else {
				return vm._selectedEpisode.id + '';
			}
		};

		vm.episodeName = function() {
			if (vm._selectedEpisode && vm._selectedEpisode.id) {
				return vm._selectedEpisode.name + " " + vm._selectedEpisode.start + " - " + vm._selectedEpisode.end + ")";
			}
			return "None";
		};


		vm.sum = function(values) {
			var result = 0;
			values.forEach(function(element, index) {
				result += element;
			});
			return result;
		};

		vm.rowsum = function(columnIndex) {
			var result = 0;
			vm.chartConfig.series.forEach(function(element, index) {
				result += element.data[columnIndex];
			});
			return result;
		};

		vm.sumsum = function() {
			var result = 0;
			vm.chartConfig.series.forEach(function(element, index) {
				element.data.forEach(function(subelement, subindex) {
					result += subelement;
				});
			});
			return result;
		};

		vm.isPast = function(date) {
			return moment(date).diff(moment(), 'minutes') < 0;
		}
	}
})();
