(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EmployeeDetailController', EmployeeDetailController);

	EmployeeDetailController.$inject = ['$route', '$location', '$http', '$filter', 'Employee'];

	function EmployeeDetailController($route, $location, $http, $filter, Employee) {
		/* jshint validthis: true */
		var vm = this;

		vm.calculateDaysForCurrentYear = calculateDaysForCurrentYear;
		vm.currentYear = currentYear;

		vm.datepicker = {
			startOpened: false,
			endOpened: false
		};

		function prepareDto() {
			if (vm.employee.contract.start && typeof vm.employee.contract.start.getMonth === 'function') {
				vm.employee.contract.start = moment(vm.employee.contract.start).format('YYYY-MM-DD');
			}
			if (vm.employee.contract.end && typeof vm.employee.contract.end.getMonth === 'function') {
				vm.employee.contract.end = moment(vm.employee.contract.end).format('YYYY-MM-DD');
			}
		}

		vm.events = [];
		vm.eventSources = [ vm.events ];
		function workingHoursToEvents(employee) {
			vm.events.splice(0, vm.events.length);
			angular.forEach(employee.contract.workingHours, function(element) {
				var start = moment(element.start, 'HH:mm:ss.SSS').day(element.dayOfWeek);
				var end = moment(element.end, 'HH:mm:ss.SSS').day(element.dayOfWeek);
				vm.events.push({
					title: moment.duration(end.diff(start)).asHours() + 'h',
					start: start,
					end: end
				});
			});
		}

		if ($route.current.params.id == 'new') {
			vm.employee = new Employee();
			vm.employee.contract = {
				workingHours: [
					{dayOfWeek:1,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:2,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:3,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:4,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:5,start:'08:00:00.000',end:'16:00:00.000'}]
			};
			workingHoursToEvents(vm.employee);
			vm.save = function() {
				prepareDto();
				vm.employee.$save(function() {
					$location.path('/employees');
				});
			};
		} else {
			vm.employee = {};
			Employee.get({id: $route.current.params.id}, function(employee) {
				vm.employee = employee;
				workingHoursToEvents(vm.employee);
				if (vm.employee.contract.start) {
					vm.employee.contract.start = new Date(vm.employee.contract.start);
				}
				if (vm.employee.contract.end) {
					vm.employee.contract.end = new Date(vm.employee.contract.end);
				}
			});
			vm.save = function() {
				prepareDto();
				vm.employee.$update(function() {
					$location.path('/employees');
				});
			};
		}

		vm.loadAbilities = function($query) {
			return $http.get('rest/abilities').then(function(response) {
				var abilities = response.data;
				return abilities.filter(function(abilitiy) {
					return abilitiy.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};

		vm.removeWorkingHours = function(dayOfWeek) {
			var i = vm.employee.contract.workingHours.length;
			while (i--){
				if (vm.employee.contract.workingHours[i].dayOfWeek == dayOfWeek){
					vm.employee.contract.workingHours.splice(i, 1);
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

		vm.calendarConfig = {
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
				vm.employee.contract.workingHours.forEach(function(element) {
					if (element.dayOfWeek == dayOfWeek) {
						workingHours = element;
					}
				});
				if (workingHours === undefined) {
					workingHours = {
						dayOfWeek: dayOfWeek
					};
					vm.employee.contract.workingHours.push(workingHours);
				}
				workingHours.start = start.format('HH:mm:ss.SSS');
				workingHours.end = end.format('HH:mm:ss.SSS');
				workingHoursToEvents(vm.employee);
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
				vm.employee.contract.workingHours.forEach(function(element) {
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
				vm.employee.contract.workingHours.forEach(function(element) {
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

		function calculateDaysForCurrentYear() {
			var days = 365;
			if (!vm.employee || !vm.employee.contract) {
				return '';
			}
			var startAffected = !!vm.employee.contract.start && moment(vm.employee.contract.start).year() == moment().year();
			var endAffected = !!vm.employee.contract.end && moment(vm.employee.contract.end).year() == moment().year();
			if (startAffected) {
				days += moment().date(1).month(0).diff(moment(vm.employee.contract.start), 'days');
			}
			if (endAffected) {
				days -= moment().day(31).month(11).diff(moment(vm.employee.contract.end), 'days');
			}

			return Math.round(10 * vm.employee.contract.vacationDaysPerYear * (days / 365)) / 10;
		}

		function currentYear() {
			return moment().year();
		}
	}
})();
