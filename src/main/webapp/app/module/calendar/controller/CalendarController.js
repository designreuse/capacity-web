(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('CalendarController', CalendarController);

	EventController.$inject = ['$uibModalInstance', 'date', 'parentScope', 'Holiday', 'Absence', 'Employee', '$filter'];

	function EventController($uibModalInstance, date, parentScope, Holiday, Absence, Employee, $filter) {
		/* jshint validthis: true */
		var vm = this;

		vm.title = 'Add event';
		vm.confirmButtons = [{ value: 'ok', label: 'OK' }];
		vm.cancelButtons = [{ value: 'cancel', label: 'Cancel' }];

		// Holiday
		vm.holiday = new Holiday();
		vm.holiday.date = date;
		vm.holiday.name = '';
		vm.holiday.hoursReduction = 8;

		// Absence
		vm.absence = new Absence();
		vm.absence.start = date;
		vm.absence.end = date;
		vm.absence.reason = '';
		vm.employees = [];
		vm.employeesLookup = {};
		Employee.query(function(employees) {
			employees = $filter('orderBy')(employees, 'name');
			angular.forEach(employees, function(element, index) {
				vm.employeesLookup[element.id] = element;
			});
			vm.employees = employees;
			vm.absence.employeeId = employees[0].id;
		});

		vm.datepicker = {
			dateOpened: false,
			startOpened: false,
			endOpened: false
		};
		vm.parentScope = parentScope;
		vm.selected = {
			calendar: parentScope.calendars[0]
		};

		vm.ok = function(value) {
			if (vm.selected.calendar.id == 'holidays') {
				vm.holiday.date = moment(vm.holiday.date).format('YYYY-MM-DD');
				vm.holiday.$save(function(holiday) {
					parentScope.allEvents[0].push({
						title: holiday.name,
						start: new Date(holiday.date),
						end: null,
						className: 'holidays'
					});
					parentScope.publishEvents(0);
					$uibModalInstance.close(value);
				});
			} else if (vm.selected.calendar.id == 'employees') {
				vm.absence.start = moment(vm.absence.start).format('YYYY-MM-DD');
				vm.absence.end = moment(vm.absence.end).format('YYYY-MM-DD');
				vm.absence.$save(function(absence) {
					parentScope.allEvents[0].push({
						title: vm.employeesLookup[absence.employeeId].name + ': ' + absence.reason,
						start: new Date(absence.start),
						end: new Date(absence.end),
						color: vm.employeesLookup[absence.employeeId].color,
						className: 'employees'
					});
					parentScope.publishEvents(1);
					$uibModalInstance.close(value);
				});
			}
		};

		vm.cancel = function(value) {
			$uibModalInstance.dismiss(value);
		};
	}

	CalendarController.$inject = ['$http', 'uiCalendarConfig', '$uibModal'];

	function CalendarController($http, uiCalendarConfig, $uibModal) {
		/* jshint validthis: true */
		var vm = this;

		vm.calendars = [
			{ id: 'holidays', name: 'Holidays', selected: true },
			{ id: 'employees', name: 'Absences', selected: true }
		];

		vm.addEvent = function(date, jsEvent, view) {

			var modalInstance = $uibModal.open({
				animation: vm.animationsEnabled,
				templateUrl: 'app/module/calendar/view/event.html',
				controller: EventController,
				controllerAs: 'vm',
				resolve: {
					date: date.toDate(),
					parentScope: vm
				}
			});
		};

		vm.calendarConfig = {
			dayClick: vm.addEvent
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
		$http.get('rest/calendar/events/holidays?start=' + vm.start + '&end=' + vm.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'holidays';
				element.start = new Date(element.start);
				element.end = new Date(element.end);
			});
			vm.allEvents[0] = response.data;
			vm.publishEvents(0);
		});
		$http.get('rest/calendar/events/absences?start=' + vm.start + '&end=' + vm.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
				element.start = new Date(element.start);
				element.end = new Date(element.end);
			});
			vm.allEvents[1] = response.data;
			vm.publishEvents(1);
		});
		// Ideally we would do this: vm.eventSources = [ 'rest/calendar/events' ];
	}
})();
