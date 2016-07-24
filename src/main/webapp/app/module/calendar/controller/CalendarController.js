/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('CalendarController', CalendarController);

	EventController.$inject = ['$uibModalInstance', 'parentScope', 'Holiday', 'Absence', 'Employee', 'eventType', 'holiday', 'absence', 'title', 'event', '$route'];

	function EventController($uibModalInstance, parentScope, Holiday, Absence, Employee, eventType, holiday, absence, title, event, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.title = title;
		vm.confirmButtons = [{ value: 'ok', label: 'OK' }];
		vm.cancelButtons = [{ value: 'cancel', label: 'Cancel' }];

		vm.holiday = holiday;
		if (vm.holiday) {
			vm.holiday.date = moment(vm.holiday.date).startOf('day').toDate();
		}
		vm.absence = absence;
		if (vm.absence) {
			vm.absence.start = moment(vm.absence.start).startOf('day').toDate();
			vm.absence.end = moment(vm.absence.end).startOf('day').toDate();
		}

		vm.employees = parentScope.employees;
		vm.employeesLookup = parentScope.employeesLookup;

		vm.datepicker = {
			dateOpened: false,
			startOpened: false,
			endOpened: false
		};
		vm.parentScope = parentScope;
		vm.selected = {
			calendar: eventType
		};

		vm.ok = function(value) {
			if (vm.selected.calendar.id == 'holidays') {
				var holiday = new Holiday();
				holiday.id = vm.holiday.id;
				holiday.date = moment(vm.holiday.date).format('YYYY-MM-DD');
				holiday.name = vm.holiday.name;
				holiday.hoursReduction = vm.holiday.hoursReduction;
				holiday.locations = vm.holiday.locations;
				if (holiday.id !== undefined) {
					holiday.$update(function() {
						$uibModalInstance.close(value);
						$route.reload();
					});
				} else {
					holiday.$save(function() {
						$uibModalInstance.close(value);
						$route.reload();
					});
				}
			} else if (vm.selected.calendar.id == 'employees') {
				var absence = new Absence();
				absence.id = vm.absence.id;
				absence.employeeId = vm.absence.employeeId;
				absence.start = moment(vm.absence.start).format('YYYY-MM-DD');
				absence.end = moment(vm.absence.end).format('YYYY-MM-DD');
				absence.reason = vm.absence.reason;
				if (absence.id !== undefined) {
					absence.$update(function() {
						$uibModalInstance.close(value);
						$route.reload();
					});
				} else {
					absence.$save(function() {
						$uibModalInstance.close(value);
						$route.reload();
					});
				}
			}
		};

		vm.cancel = function(value) {
			$uibModalInstance.dismiss(value);
		};
	}

	CalendarController.$inject = ['$http', 'uiCalendarConfig', '$uibModal', '$filter', 'Holiday', 'Absence', 'Employee'];

	function CalendarController($http, uiCalendarConfig, $uibModal, $filter, Holiday, Absence, Employee) {
		/* jshint validthis: true */
		var vm = this;

		vm.calendars = [
			{ id: 'holidays', name: 'Holidays', selected: true },
			{ id: 'employees', name: 'Absences', selected: true }
		];

		vm.addEvent = function(date) {
			$uibModal.open({
				animation: vm.animationsEnabled,
				templateUrl: 'app/module/calendar/view/event.html',
				controller: EventController,
				controllerAs: 'vm',
				resolve: {
					parentScope: vm,
					title: function() {
						return 'Add event';
					},
					eventType: vm.calendars[0],
					holiday: function() {
						var holiday = new Holiday();
						holiday.date = date.toDate();
						holiday.name = '';
						holiday.hoursReduction = 8;
						return holiday;
					},
					absence: function() {
						var absence = new Absence();
						absence.start = date.toDate();
						absence.end = date.toDate();
						absence.reason = '';
						if (vm.employees.length > 0) {
							absence.employeeId = vm.employees[0].id;
						}
						return absence;
					},
					event: undefined
				}
			});
		};

		vm.editEvent = function(event) {

			// We cannot edit imported events
			if (event.imported) {
				return;
			}

			$uibModal.open({
				animation: vm.animationsEnabled,
				templateUrl: 'app/module/calendar/view/event.html',
				controller: EventController,
				controllerAs: 'vm',
				resolve: {
					parentScope: vm,
					title: function() {
						return 'Edit event';
					},
					eventType: (event.className[0] === 'holidays') ? vm.calendars[0] : vm.calendars[1],
					holiday: function() {
						if (event.className[0] === 'holidays') {
							return event;
						}
						return undefined;
					},
					absence: function () {
						if (event.className[0] !== 'holidays') {
							return event;
						}
						return undefined;
					},
					event: event
				}
			});
		};

		vm.calendarConfig = {
			dayClick: vm.addEvent,
			eventClick: vm.editEvent
		};

		vm.publishEvents = function(index) {
			if (vm.calendars[index].selected && vm.allEvents[index] !== undefined) {
				vm.eventSources[index] = vm.allEvents[index];
			} else {
				vm.eventSources[index] = [];
			}
		};

		vm.publishAllEvents = function() {
			vm.publishEvents(0);
			vm.publishEvents(1);
		};

		vm.start = moment().startOf('month').format('YYYY-MM-DD');
		vm.end = moment().endOf('month').format('YYYY-MM-DD');
		vm.eventSources = [];
		vm.allEvents = [[],[]];

		vm.employees = {};
		vm.employeesLookup = {};
		Employee.query(function(employees) {
			employees = $filter('orderBy')(employees, 'name');
			angular.forEach(employees, function(element) {
				vm.employeesLookup[element.id] = element;
			});
			vm.employees = employees;

			vm.allEvents[0] = Holiday.query({start: vm.start, end: vm.end}, function() {
				vm.allEvents[0].forEach(function(element) {
					element.className = 'holidays';
					if (element.imported) {
						element.className += ' imported';
					}
					element.date = element.date + ' 12:00:00';
					element.title = element.name;
					element.start = element.date;
				});
				vm.publishEvents(0);

				vm.allEvents[1] = Absence.query({start: vm.start, end: vm.end}, function() {
					vm.allEvents[1].forEach(function(element) {
						element.className = 'employees';
						if (element.imported) {
							element.className += ' imported';
						}
						element.start = element.start + ' 00:00:00';
						element.end = element.end + ' 23:59:59';
						element.title = vm.employeesLookup[element.employeeId].name + ': ' + element.reason;
						element.color = vm.employeesLookup[element.employeeId].color;
					});
					vm.publishEvents(1);
				});
			});
		});
	}
})();
