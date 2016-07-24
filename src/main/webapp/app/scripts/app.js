/// <reference path="../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular
		.module('capacityApp', ['ngResource', 'ngRoute', 'ngTagsInput', 'ui.calendar', 'highcharts-ng', 'color.picker', 'ui.bootstrap', 'ui.bootstrap-slider'])
		.config(config);

	function config(uibDatepickerConfig) {
		uibDatepickerConfig.startingDay = 1;
	}
})();
