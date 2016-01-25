'use strict';

angular.module('capacityApp')
	.controller('IcalImportListController', ['$scope', '$location', '$rootScope', '$http', '$uibModal', 'IcalImport', function ($scope, $location, $rootScope, $http, $uibModal, IcalImport) {
		IcalImport.query(function(icalImports) {
			$scope.icalImports = icalImports;
		});

		$scope.add = function() {
			$location.path('/ical_imports/new');
		};

		$scope.predicate = 'name';
		$scope.reverse = false;
		$scope.order = function(predicate) {
			$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
		};

		$scope.searchFilter = function(string) {
			$rootScope.search = string;
		};

		$scope.import = function(id) {
			$http.get('rest/ical_import/import/' + id).then(function (response) {
				var progress = {
					value: 0,
					max: 1,
					message: 'About to start'
				};
				var ws;
				var modalInstance = $uibModal.open({
					templateUrl : 'app/views/progressDialog.html',
					backdrop : 'static',
					controller : function($scope, $uibModalInstance) {
						$scope.title = 'Importing ICAL';
						$scope.progress = progress;
						$scope.confirmButtons = [ { value: 'ok', label: 'OK' }];
						$scope.cancelButtons = [ ];

						if (window.location.protocol == 'https:') {
							ws = new WebSocket("wss://" + window.location.host + window.location.pathname + "async/progress/" + response.data);
						} else {
							ws = new WebSocket("ws://" + window.location.host + window.location.pathname + "async/progress/" + response.data);
						}

						ws.onmessage = function(event) {
							var newProgress = JSON.parse(event.data);
							progress.value = newProgress.value;
							progress.max = newProgress.max ? newProgress.max : 1;
							progress.message = newProgress.message;
							progress.result = newProgress.result;
							if (progress.result) {
								$scope.ok('ok');
							}
							$scope.$apply();
						};

						ws.onerror = function(event){
							ws.close();
						};

						$scope.ok = function(value) {
							$uibModalInstance.close(value);

							if (progress.result) {
								var modalInstanceCompleted = $uibModal.open({
									templateUrl: 'app/views/modalDialog.html',
									backdrop : 'static',
									controller : function($scope, $uibModalInstance) {
										$scope.title = 'Import completed';
										$scope.message = 'The import was completed successfully (Skipped: ' + progress.result.skipped + ', Created: ' + progress.result.created  + ', Updated: ' + progress.result.updated + ')';
										$scope.confirmButtons = [ { value: 'ok', label: 'OK' }];
										$scope.cancelButtons = [ ];

										$scope.ok = function(value) {
											$uibModalInstance.close(value);
										};

										$scope.cancel = function(value) {
											$uibModalInstance.dismiss(value);
										};
									}
								});
								modalInstanceCompleted.result.then(function(value) {
									IcalImport.query(function(icalImports) {
										$scope.icalImports = icalImports;
									});
								});
							}

						};
					}
				});
				modalInstance.result.then(function(value) {
					if (ws) {
						ws.close();
					}
				});


			});
		};
	}]
);
