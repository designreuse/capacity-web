(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('CalendarController', CalendarController);

	CalendarController.$inject = ['$scope', '$http', 'uiCalendarConfig', '$uibModal'];

	function CalendarController($scope, $http, uiCalendarConfig, $uibModal) {
		$scope.calendars = [
			{ id: 'holidays', name: 'Holidays', selected: true },
			{ id: 'employees', name: 'Absences', selected: true }
		];

		$scope.alertEventOnClick = function(date, jsEvent, view) {

			var modalInstance = $uibModal.open({
				animation: $scope.animationsEnabled,
				templateUrl: 'app/module/calendar/view/event.html',
				controller: function ($scope, $uibModalInstance, date, parentScope, Holiday, Absence, Employee, $filter) {
					$scope.title = 'Add event';
					$scope.confirmButtons = [{ value: 'ok', label: 'OK' }];
					$scope.cancelButtons = [{ value: 'cancel', label: 'Cancel' }];

					// Holiday
					$scope.holiday = new Holiday();
					$scope.holiday.date = date;
					$scope.holiday.name = '';
					$scope.holiday.hoursReduction = 8;

					// Absence
					$scope.absence = new Absence();
					$scope.absence.start = date;
					$scope.absence.end = date;
					$scope.absence.reason = '';
					$scope.employees = [];
					$scope.employeesLookup = {};
					Employee.query(function(employees) {
						employees = $filter('orderBy')(employees, 'name');
						angular.forEach(employees, function(element, index) {
							$scope.employeesLookup[element.id] = element;
						});
						$scope.employees = employees;
						$scope.absence.employeeId = employees[0].id;
					});

					$scope.datepicker = {
						dateOpened: false,
						startOpened: false,
						endOpened: false
					};
					$scope.parentScope = parentScope;
					$scope.selected = {
						calendar: parentScope.calendars[0]
					};

					$scope.ok = function(value) {
						if ($scope.selected.calendar.id == 'holidays') {
							$scope.holiday.date = moment($scope.holiday.date).format('YYYY-MM-DD');
							$scope.holiday.$save(function(holiday) {
								parentScope.allEvents[0].push({
									title: holiday.name,
									start: new Date(holiday.date),
									end: null,
									className: 'holidays'
								});
								publishEvents(0);
								$uibModalInstance.close(value);
							});
						} else if ($scope.selected.calendar.id == 'employees') {
							$scope.absence.start = moment($scope.absence.start).format('YYYY-MM-DD');
							$scope.absence.end = moment($scope.absence.end).format('YYYY-MM-DD');
							$scope.absence.$save(function(absence) {
								parentScope.allEvents[0].push({
									title: $scope.employeesLookup[absence.employeeId].name + ': ' + absence.reason,
									start: new Date(absence.start),
									end: new Date(absence.end),
									color: $scope.employeesLookup[absence.employeeId].color,
									className: 'employees'
								});
								publishEvents(1);
								$uibModalInstance.close(value);
							});
						}
					};

					$scope.cancel = function(value) {
						$uibModalInstance.dismiss(value);
					};
				},
				resolve: {
					date: date.toDate(),
					parentScope: $scope
				}
			});
		};

		$scope.calendarConfig = {
			dayClick: $scope.alertEventOnClick
		};

		var publishEvents = function(index) {
			if ($scope.calendars[index].selected) {
				$scope.eventSources[index] = $scope.allEvents[index];
			} else {
				$scope.eventSources[index] = [];
			}
		};

		$scope.publishAllEvents = function() {
			publishEvents(0);
			publishEvents(1);
		};

		$scope.start = moment().startOf('month').format('YYYY-MM-DD');
		$scope.end = moment().endOf('month').format('YYYY-MM-DD');
		$scope.eventSources = [];
		$scope.allEvents = [[],[]];
		$http.get('rest/calendar/events/holidays?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'holidays';
				element.start = new Date(element.start);
				element.end = new Date(element.end);
			});
			$scope.allEvents[0] = response.data;
			publishEvents(0);
		});
		$http.get('rest/calendar/events/absences?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
				element.start = new Date(element.start);
				element.end = new Date(element.end);
			});
			$scope.allEvents[1] = response.data;
			publishEvents(1);
		});
		// Ideally we would do this: $scope.eventSources = [ 'rest/calendar/events' ];
	}
})();
