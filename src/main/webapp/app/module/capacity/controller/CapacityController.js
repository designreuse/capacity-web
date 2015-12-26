'use strict';

angular.module('capacityApp')
	.controller('CapacityController', ['$scope', function($scope) {
		/*$scope.chartConfig = {

			options: {
				chart: {
					type: 'bar'
				},
				tooltip: {
					style: {
						padding: 10,
						fontWeight: 'bold'
					}
				}
			},

			series: [{
				data: [10, 15, 12, 8, 7]
			}],

			title: {
				 text: 'Hello'
			},

			loading: false,
				xAxis: {
				currentMin: 0,
				currentMax: 4,
				title: {text: 'values'}
			},
			size: {
				width: 400,
				height: 300
			}
		};*/
		
		$scope.chartConfig = {
				options: {
		        chart: {
		        	type: 'column'
		        },
		        plotOptions: {
		            column: {
		                stacking: 'normal',
		                dataLabels: {
		                    enabled: true,
		                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
		                    style: {
		                        textShadow: '0 0 3px black'
		                    }
		                }
		            },series: {
		                animation: false
		            }
		        }
				},
		        title: {
		            text: 'Stacked bar chart'
		        },
		        xAxis: {
		            categories: ['2016Q1.1', '2016Q1.2', '2016Q1.3', '2016Q1.4', '2016Q1.5']
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'Total fruit consumption'
		            },
		            stackLabels: {
		                enabled: true,
		                style: {
		                    fontWeight: 'bold',
		                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
		                }
		            }
		        },
		        legend: {
		            reversed: true
		        },
		        series: [{
		            name: 'Development',
		            data: [5, 3, 4, 7, 2]
		        }, {
		            name: 'Quality assurance',
		            data: [2, 2, 3, 2, 1]
		        }]
		    };
	}]);
