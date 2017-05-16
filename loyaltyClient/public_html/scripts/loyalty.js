/* global angular */

var module = angular.module('LoyaltyApp', ['ngResource']);
module.controller('LoyaltyController', function ($scope, $resource) {

	var serverURI = "http://localhost\\:8081/customers/:customer/";
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
			var pointsResource = $resource(serverURI + 'points/unused', null, {
				'getPoints': {transformResponse: function (data, headers, status) {
						return {value: data};
					}
				}});

			try {
				pointsResource.getPoints({"customer": $scope.customer.id},
						  function (points) {
							  $scope.available = points.value;
						  });
			} catch (err) {
				$scope.messages = "That is not a valid email address.";
			}
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
		$scope.messages = null;
		ws1.send(document.getElementById("email").value);
	};

	$scope.updateEquivalent = function () {
		var wanted = document.getElementById("wanted").value;
		if (wanted === "") {
			wanted = 0;
		}
		$scope.equivalent = parseFloat(wanted).toFixed(2);
	};

	$scope.createCoupon = function () {
		if (parseInt($scope.coupon.points) > parseInt($scope.available) || parseInt($scope.coupon.points) < 0) {
			$scope.messages = "That is not a valid number of points.";
		} else {
			$scope.messages = null;
			var coupons = $resource(serverURI + 'coupons', null, {create: {method: "POST", headers: {'Content-Type': 'application/json'}}});
			coupons.create({customer: $scope.customer.id}, $scope.coupon, function (data) {
				var newCoupon = data;

				var product = new Object();
				product.handle = newCoupon.id;
				product.type = "coupon";
				product.name = "adath325 Coupon";
				product.retail_price = -(newCoupon.points);

				var json = JSON.stringify(product);
				ws2.send(json);

				JsBarcode('#barcode', newCoupon.id);

				// Clear wanted points and equivalent $ fields
				$scope.signIn();
				$scope.coupon.points = null;
				$scope.equivalent = null;
			});
		}
	};
}
);
