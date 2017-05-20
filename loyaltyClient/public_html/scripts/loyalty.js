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
	document.getElementById("barcodeSet").style.visibility = "hidden";
	
	function initializeCustomerSocket() {
		ws1 = new WebSocket("ws://localhost:8087/customer");
		ws1.onmessage = function (event) {
			try {
				var object = JSON.parse(event.data);
				if (object.customers[0].id === 
						  '06bf537b-c77f-11e7-ff13-0c871e86361a') {
					$scope.messages = "That is not a valid email address.";
				} else {
					$scope.customer = object.customers[0];
					var pointsResource = $resource(serverURI + 'points/unused', 
					null, {
						'getPoints': {transformResponse: function (data, headers, 
							status) {
								return {value: data};
							}
						}});
					pointsResource.getPoints({"customer": $scope.customer.id},
							  function (points) {
								  $scope.available = points.value;
							  });
					document.getElementById("wanted").disabled = false;
					document.getElementById("createButton").disabled = false;
				}
			} catch (err) {
				$scope.messages = "That is not a valid email address.";
			}
			$scope.$apply();
		};
		ws1.onerror = function (event) {
			$scope.messages = "That is not a valid email address.";
		};
		ws1.onclose = function (event) {
			$scope.messages = "There is a problem with the server.";
		};
	}

	function initializeProductSocket() {
		ws2 = new WebSocket("ws://localhost:8087/product");
		ws2.onmessage = function (event) {
			$scope.$apply();
		};
		ws2.onerror = function (event) {
			$scope.messages = "There was a problem creating that coupon.";
		};
		ws2.onclose = function (event) {
			$scope.messages = "There is a problem with the server.";
		};
	}

	$scope.signIn = function () {
		$scope.messages = null;
		ws1.send(document.getElementById("email").value);
	};
	$scope.updateEquivalent = function () {
		$scope.messages = null;
		var wanted = document.getElementById("wanted").value;
		if (wanted === "" || isNaN(wanted) === true) {
			wanted = 0;
		}
		$scope.equivalent = parseFloat(wanted).toFixed(2);
	};
	$scope.createCoupon = function () {
		$scope.messages = null;
		if (typeof $scope.coupon === 'undefined' || 
				  parseInt($scope.coupon.points) > parseInt($scope.available) || 
				  parseInt($scope.coupon.points) <= 0) {
			$scope.messages = "That is not a valid number of points.";
		} else {
			$scope.messages = null;
			var coupons = $resource(serverURI + 'coupons', null, {create: 
						  {method: "POST", headers: 
							  {'Content-Type': 'application/json'}}});
			coupons.create({customer: $scope.customer.id}, $scope.coupon, 
			function (data) {
				var newCoupon = data;
				var product = new Object();
				product.handle = newCoupon.id;
				product.type = "coupon";
				product.name = "adath325 Coupon";
				product.retail_price = -(newCoupon.points);
				var json = JSON.stringify(product);
				ws2.send(json);
				JsBarcode('#barcode', newCoupon.id);
				$scope.value = $scope.equivalent;
				document.getElementById("barcodeSet").style.visibility = "visible";
				// Clear fields
				$scope.signIn();
				$scope.coupon.points = null;
				$scope.equivalent = null;
			});
		}
	};
}
);
