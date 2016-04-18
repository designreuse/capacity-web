'use strict';

angular.module('capacityApp')
	.controller('CalendarController', ['$scope', '$http', 'uiCalendarConfig', '$uibModal', function($scope, $http, uiCalendarConfig, $uibModal) {

		$scope.calendars = [
			{ id: 'holidays', name: 'Holidays', selected: true },
			{ id: 'employees', name: 'Absences', selected: true }
		];

		$scope.alertEventOnClick = function(date, jsEvent, view) {

			var modalInstance = $uibModal.open({
				animation: $scope.animationsEnabled,
				templateUrl: 'app/module/calendar/view/event.html',
				controller: function ($scope, $uibModalInstance, date, parentScope, Holiday) {
					$scope.title = 'Add event';
					$scope.confirmButtons = [{ value: 'ok', label: 'OK' }];
					$scope.cancelButtons = [{ value: 'cancel', label: 'Cancel' }];
					$scope.holiday = new Holiday();
					$scope.holiday.date = moment(date).format('YYYY-MM-DD');
					$scope.holiday.name = '';
					$scope.holiday.hoursReduction = 8;
					$scope.datepicker = {
						startOpened: false
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
									start: holiday.date,
									end: null,
									className: 'holidays'
								});
								publishEvents(0);
								$uibModalInstance.close(value);
							});
						}
					};

					$scope.cancel = function(value) {
						$uibModalInstance.dismiss(value);
					};
				},
				resolve: {
					date: date,
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
			});
			$scope.allEvents[0] = response.data;
			publishEvents(0);
		});
		$http.get('rest/calendar/events/absences?start=' + $scope.start + '&end=' + $scope.end).then(function(response) {
			response.data.forEach(function(element, index) {
				element.className = 'employees';
			});
			$scope.allEvents[1] = response.data;
			publishEvents(1);
		});
		// Ideally we would do this: $scope.eventSources = [ 'rest/calendar/events' ];
	}]);
