/* global angular */

var module = angular.module('LoyaltyApp', ['ngResource']);
module.controller('LoyaltyController', function ($scope, $resource) {
	
	var loyaltyResource = $resource('http://localhost\\:8081/customers');
	
	$scope.summaries = loyaltyResource.get();
	
	$scope.displayCustomer = function () {
		if ($scope.email !== null) {
			var cust = 
			var uri = $scope.cust.uri;
			$scope.customer = $resource(uri, null, {update: {method: 'PUT'}}).get();
		} else {
			$scope.customer = null;
		}
	};
	
	$scope.createProduct = function () {
		inventoryResource.save($scope.product,
				  // response callback
							 function () {
								 // GET the latest the summaries from service
								 $scope.summaries.$get();
								 // notify the user
								 $scope.messages = "New product '" + $scope.product.name
											+ "' was successfully created.";
							 }
				  );
	};
	$scope.deleteSelectedProduct = function () {
		// do nothing if there is no valid product selected
		if ($scope.product === null)
			return;
		$scope.product.$remove(
				  // response callback
							 function () {
								 // update the summaries
								 $scope.summaries.$get();
								 // notify the user
								 $scope.messages = "Product '" + $scope.product.name
											+ "' was successfully deleted.";
								 // clear the models since we just deleted the product 
								 $scope.product = null;
								 $scope.selected = null;
							 }
				  );
	};
	$scope.updateSelectedProduct = function () {
		// do nothing if there is no valid product selected
		if ($scope.product === null)
			return;
		$scope.product.$update(
				  // response callback
							 function () {
								 // update the summaries
								 $scope.summaries.$get();
								 // notify the user
								 $scope.messages = "Product '" + $scope.product.name
											+ "' was successfully updated.";
							 }
				  );
	};

}
);
