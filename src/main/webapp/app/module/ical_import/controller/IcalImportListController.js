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

				var modalInstance = $uibModal.open({
					templateUrl: 'app/views/modalDialog.html',
					backdrop : 'static',
					controller : function($scope, $uibModalInstance) {
						$scope.title = 'Import completed';
						$scope.message = 'The import was completed successfully (Skipped: ' + response.data.skipped + ', Created: ' + response.data.created  + ', Updated: ' + response.data.updated + ')';
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
				modalInstance.result.then(function(value) {
					IcalImport.query(function(icalImports) {
						$scope.icalImports = icalImports;
					});
				});
			});
		};
	}]
);