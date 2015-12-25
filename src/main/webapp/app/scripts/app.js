angular.module('capacityApp', ['ngResource'])
  .factory('Employee', function($resource) {
     return $resource('rest/employee/:id', { id: '@id' }, {
    	    update: {
    	        method: 'PUT'
    	      }
    	    });
   })
  .controller('MainCtrl', ['$scope', '$resource', 'Employee', function($scope, $resource, Employee) {
    
    Employee.query(function(employees) {
        $scope.employees = employees;
    });
    /*$scope.id = 1;
    Employee.get({id: $scope.id}, function(employee) {
        console.log(employee);
    });
    $scope.employee = new Employee();
    $scope.employee.name = 'somename';
    $scope.employee.email = 'somename';

    $scope.employee.$save(function() {
      console.log('done');
      $scope.employee.id = 5;
      $scope.employee.email = 'somenewmail';

      $scope.employee.$update(function() {
        console.log('done');
      });
    });*/
    
  }]);