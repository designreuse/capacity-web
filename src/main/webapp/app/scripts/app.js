(function() {
	'use strict';

	angular.module('capacityApp', ['ngResource', 'ngRoute', 'ngTagsInput', 'ui.calendar', 'highcharts-ng', 'color.picker', 'ui.bootstrap', 'ui.bootstrap-slider'])
		.config(function (uibDatepickerConfig) {
			uibDatepickerConfig.startingDay = 1;
		});
})();
