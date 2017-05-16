/* global angular */

var module = angular.module('LoyaltyApp', ['ngResource']);
module.controller('LoyaltyController', function ($scope, $resource) {

	if (!window.WebSocket) {
		alert("WebSockets are not supported by this browser");
	} else {
		initializeCustomerSocket();
		initializeProductSocket();
	}

	function initializeCustomerSocket() {
		ws1 = new WebSocket("ws://localhost:8087/customer");
		ws1.onmessage = function (event) {
			var object = JSON.parse(event.data);
			$scope.customer = object.customers[0];

			var pointsResource = $resource('http://localhost\\:8081/customers/:customer/points/unused', null, {
				'getPoints': {transformResponse: function (data, headers, status) {
						return {value: data};
					}
				}});
			pointsResource.getPoints({"customer": $scope.customer.id},
					  function (points) {
						  $scope.available = points.value;
					  });

			$scope.$apply();
		};
	}

	function initializeProductSocket() {
		ws2 = new WebSocket("ws://localhost:8087/product");
		ws2.onmessage = function (event) {

			$scope.$apply();
		};
	}

	$scope.signIn = function () {
		var email = document.getElementById("email").value;
		ws1.send(email);
	};

	$scope.updateEquivalent = function () {
		$scope.equivalent = (document.getElementById("wanted").value);
	};

	$scope.createCoupon = function () {
		JsBarcode("#barcode", document.getElementById("equivalent").value);
	};
}
);
