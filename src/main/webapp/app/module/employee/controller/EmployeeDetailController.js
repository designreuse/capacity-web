'use strict';

angular.module('capacityApp')
	.controller('EmployeeDetailController', ['$scope', '$route', '$location', '$http', '$filter', 'Employee', function ($scope, $route, $location, $http, $filter, Employee) {

		$scope.datepicker = {
			startOpened: false,
			endOpened: false
		};

		function prepareDto() {
			if ($scope.employee.contract.start && typeof $scope.employee.contract.start.getMonth === 'function') {
				$scope.employee.contract.start = moment($scope.employee.contract.start).format('YYYY-MM-DD');
			}
			if ($scope.employee.contract.end && typeof $scope.employee.contract.end.getMonth === 'function') {
				$scope.employee.contract.end = moment($scope.employee.contract.end).format('YYYY-MM-DD');
			}
		}

		$scope.events = [];
		$scope.eventSources = [ $scope.events ];
		function workingHoursToEvents(employee) {
			$scope.events.splice(0, $scope.events.length);
			angular.forEach(employee.contract.workingHours, function(element, index) {
				var start = moment(element.start, 'HH:mm:ss.SSS').day(element.dayOfWeek);
				var end = moment(element.end, 'HH:mm:ss.SSS').day(element.dayOfWeek);
				$scope.events.push({
					title: moment.duration(end.diff(start)).asHours() + 'h',
					start: start,
					end: end
				});
			});
		}

		if ($route.current.params.id == 'new') {
			$scope.employee = new Employee();
			$scope.employee.contract = {
				workingHours: [
					{dayOfWeek:1,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:2,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:3,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:4,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:5,start:'08:00:00.000',end:'16:00:00.000'}]
			};
			workingHoursToEvents($scope.employee);
			$scope.save = function() {
				prepareDto();
				$scope.employee.$save(function() {
					$location.path('/employees');
				});
			};
		} else {
			$scope.employee = {};
			Employee.get({id: $route.current.params.id}, function(employee) {
				$scope.employee = employee;
				workingHoursToEvents($scope.employee);
				if ($scope.employee.contract.start) {
					$scope.employee.contract.start = new Date($scope.employee.contract.start);
				}
				if ($scope.employee.contract.end) {
					$scope.employee.contract.end = new Date($scope.employee.contract.end);
				}
			});
			$scope.save = function() {
				prepareDto();
				$scope.employee.$update(function() {
					$location.path('/employees');
				});
			};
		}

		$scope.loadAbilities = function($query) {
			return $http.get('rest/abilities').then(function(response) {
				var abilities = response.data;
				return abilities.filter(function(abilitiy) {
					return abilitiy.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};

		$scope.removeWorkingHours = function(dayOfWeek) {
			var i = $scope.employee.contract.workingHours.length;
			while (i--){
				if ($scope.employee.contract.workingHours[i].dayOfWeek == dayOfWeek){
					$scope.employee.contract.workingHours.splice(i, 1);
				}
			}
		};

		// JSR 310 (Java 8 datetime) uses 1-7, while moment uses 0-6 (though both use 1 as monday)
		function getOneBasedDayOfWeek(date) {
			var dayOfWeek = date.day();
			if (dayOfWeek === 0) {
				dayOfWeek = 7;
			}
			return dayOfWeek;
		}

		$scope.calendarConfig = {
			// Hide the header completely 
			header: {
				left: '',
				center: '',
				right: ''
			},
			// No allDay row
			allDaySlot: false,
			// Show the week view
			defaultView: 'agendaWeek',
			columnFormat: 'dddd',
			// Monday is first day
			firstDay: '1',
			// Make selectable
			selectable: true,
			selectHelper: true,
			select: function(start, end, jsEvent, view) {
				var dayOfWeek = getOneBasedDayOfWeek(start);
				var workingHours;
				$scope.employee.contract.workingHours.forEach(function(element, index) {
					if (element.dayOfWeek == dayOfWeek) {
						workingHours = element;
					}
				});
				if (workingHours === undefined) {
					workingHours = {
						dayOfWeek: dayOfWeek
					};
					$scope.employee.contract.workingHours.push(workingHours);
				}
				workingHours.start = start.format('HH:mm:ss.SSS');
				workingHours.end = end.format('HH:mm:ss.SSS');
				workingHoursToEvents($scope.employee);
				view.calendar.unselect();
			},
			// Make events movable
			editable: true,
			eventDrop: function(event, delta, revertFunc) {
				if (delta.days() !== 0) {
					revertFunc();
					return;
				}
				var dayOfWeek = getOneBasedDayOfWeek(event.start);
				var workingHours;
				$scope.employee.contract.workingHours.forEach(function(element, index) {
					if (element.dayOfWeek == dayOfWeek) {
						workingHours = element;
					}
				});
				workingHours.start = event.start.format('HH:mm:ss.SSS');
				workingHours.end = event.end.format('HH:mm:ss.SSS');
			},
			eventResizeStart: function(event) {
				event.title = '';
			},
			eventResize: function(event, delta, revertFunc) {
				if (event.start.day() != event.end.day()) {
					revertFunc();
					return;
				}
				var dayOfWeek = getOneBasedDayOfWeek(event.start);
				var workingHours;
				$scope.employee.contract.workingHours.forEach(function(element, index) {
					if (element.dayOfWeek == dayOfWeek) {
						workingHours = element;
					}
				});
				event.title = moment.duration(event.end.diff(event.start)).asHours() + 'h';
				workingHours.start = event.start.format('HH:mm:ss.SSS');
				workingHours.end = event.end.format('HH:mm:ss.SSS');
			},
			// Allow non-overlapping times from 07:00 to 19:00
			minTime: '07:00',
			maxTime: '19:00',
			contentHeight: 'auto',
			businessHours: {
				start: '08:00',
				end: '18:00'
			},
			slotEventOverlap: false,
			eventOverlap: false,
			// Set the first of the current week
			defaultDate: moment('00:00:00.000', 'HH:mm:ss.SSS').day(1),
			// Show 24h times
			timeFormat: 'HH:mm',
			axisFormat: 'HH:mm',
			slotLabelFormat: 'HH:mm'
		};

	}]
);