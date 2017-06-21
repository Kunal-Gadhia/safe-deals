angular.module("safedeals.states.guidelines", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('main.guidelines', {
                'url': '/guidelines',
                'templateUrl': templateRoot + '/guidelines/guidelines.html',
                'controller': 'GuidelinesController'
            });
            $stateProvider.state("main.guidelines.report_type", {
                'url': '/report_type',
                'params': {userDetails: null},
                'templateUrl': templateRoot + '/guidelines/report_type.html',
                'controller': 'GuidelinesReportController'
            });
            $stateProvider.state("budget_report", {
                'url': '/budget_report',
                'params': {budgetDetails: null},
                'templateUrl': templateRoot + '/guidelines/budget_report.html',
                'controller': 'BudgetReportController'
            });
//            $stateProvider.state("main.guidelines.loan_eligibility", {
//                'url': '/loan_eligibility',
//                'templateUrl': templateRoot + '/guidelines/loan_eligibility.html',
//                'controller': 'LoanEligibilityController'
//            });
        })
        .controller('GuidelinesReportController', function ($scope, $stateParams, $state, restRoot, $window, GuidelinesService) {
            console.log("$stateParams", $stateParams.userDetails);
            $scope.personalDetails = $stateParams.userDetails;
            $scope.personalDetails.cashInHand = $scope.cashInHand;
            $scope.personalDetails.loanEligibility = $scope.loanEligibility;
            $scope.personalDetails.grossBudget = $scope.grossBudget;
            $scope.personalDetails.emiEligibility = $scope.emiEligibility;
            $scope.personalDetails.eligiblePropertyValue = $scope.eligiblePropertyValue;
//            console.log("Inside GuidelinesReportController", $scope.personalDetails);
//            /* @ngInject */
            $scope.saveGuidelinesReportDetail = function (details) {
//                $scope.personalDetails = details;
                $scope.personalDetails.name = details.name;
                $scope.personalDetails.email = details.email;
                $scope.personalDetails.cashInHand = Number($scope.cashInHand.toFixed(2));
                $scope.personalDetails.loanEligibility = Number($scope.loanEligibility.toFixed(2));
                $scope.personalDetails.grossBudget = Number($scope.grossBudget.toFixed(2));
                $scope.personalDetails.emiEligibility = Number($scope.emiEligibility.toFixed(2));
                $scope.personalDetails.eligiblePropertyValue = Number($scope.eligiblePropertyValue.toFixed(2));
//                $window.open('//google.com');
//                $window.location.href = 'templates/guidelines/property_requirement.html';
//                GuidelinesService.getGuidelinesReport(personalDetails);
//                  return restRoot + "/guidelines/report";
                console.log("post saving details:", $scope.personalDetails);
                GuidelinesService.sendGuidelinesReportByMail({
                    'name': $scope.personalDetails.name,
                    'email': $scope.personalDetails.email,
                    'cashInHand': $scope.personalDetails.cashInHand,
                    'loanEligibility': $scope.personalDetails.loanEligibility,
                    'grossBudget': $scope.personalDetails.grossBudget,
                    'emiEligibility': $scope.personalDetails.emiEligibility,
                    'eligiblePropertyValue': $scope.personalDetails.eligiblePropertyValue}, function (a) {
                    alert("Mail sent successfully");
                    console.log("Return report", a);
                });
                $state.go('budget_report', {budgetDetails: $scope.personalDetails});
//                $window.open($state.href('budget_report', {budgetDetails: $scope.personalDetails}, {absolute: true}), '_blank');
//                var url = $state.href('budget_report', {budgetDetails : $scope.personalDetails});
//                window.open(url, '_blank');
            };
        })
        .controller('BudgetReportController', function ($scope, $stateParams, $state, restRoot, $window) {
            console.log("$stateParams", $stateParams.budgetDetails);
            $scope.budgetReportDetails = $stateParams.budgetDetails;

        })
        .controller('GuidelinesController', function (IncomeSlabService, $scope, $stateParams, $window, BankService, CityService, PropertyTypeService) {

            /****************tab functions**************/
            $scope.steps = [
                'Personal Information',
                'Size of Property',
                'Income Details',
                'Liabilities & Assets',
                'Reports'
            ];
            $scope.selection = $scope.steps[0];

            $scope.searchCities = function (searchTerm) {
                console.log("State Id :%O", $scope.stateId);
                if ($scope.stateId === undefined) {
                    return CityService.findByNameLike({
                        'name': searchTerm
                    }).$promise;
                } else {
                    return CityService.findByNameAndStateId({
                        'name': searchTerm,
                        'stateId': $scope.stateId
                    }).$promise;
                }
            };
            $scope.selectCity = function (city) {
                //only change if not same as previously selected state
                console.log("City Name KG:%O", city);
                $scope.cityName = city.name;
                $scope.cityId = city.id;
                $scope.city = city;
            };
            /////////////////////////GPS Control//////////////////////////////////////
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    $scope.$apply(function () {
                        $scope.position = position;
                        var lat = $scope.position.coords.latitude;
                        var long = $scope.position.coords.longitude;
                        $scope.latLng = new google.maps.LatLng(lat, long);
                        //////////////////////Reverse Geocoding/////////////////////////
                        new google.maps.Geocoder().geocode({'latLng': $scope.latLng}, function (results, status) {
                            console.log("Results :%O", results);
                            console.log("Status :%O", status);
                            if (status === google.maps.GeocoderStatus.OK) {
                                $scope.gpsCityName = results[5].address_components[0].long_name;
                                CityService.findByCityName({
                                    'name': $scope.gpsCityName
                                }, function (cityDate) {
                                    $scope.selectCity(cityDate);
                                });
                            } else {
                                console.log('Geocoder failed due to: ' + status);
                            }
                        });
                        /////////////////////////////////////////////////////////////
                    });
                });
            }

            $scope.getCurrentStepIndex = function () {
                // Get the index of the current step given selection
                return _.indexOf($scope.steps, $scope.selection);
            };
            // Go to a defined step index
            $scope.goToStep = function (index) {
                if (!_.isUndefined($scope.steps[index]))
                {
                    $scope.selection = $scope.steps[index];
                }
            };
            $scope.hasNextStep = function () {
                var stepIndex = $scope.getCurrentStepIndex();
                var nextStep = stepIndex + 1;
                // Return true if there is a next step, false if not
                return !_.isUndefined($scope.steps[nextStep]);
            };
            $scope.hasPreviousStep = function () {
                var stepIndex = $scope.getCurrentStepIndex();
                var previousStep = stepIndex - 1;
                // Return true if there is a next step, false if not
                return !_.isUndefined($scope.steps[previousStep]);
            };
            $scope.incrementStep = function () {
                if ($scope.hasNextStep())
                {
                    var stepIndex = $scope.getCurrentStepIndex();
                    var nextStep = stepIndex + 1;
                    $scope.selection = $scope.steps[nextStep];
                    console.log("$scope.selection", $scope.selection);
                }
            };
            $scope.decrementStep = function () {
                if ($scope.hasPreviousStep())
                {
                    var stepIndex = $scope.getCurrentStepIndex();
                    var previousStep = stepIndex - 1;
                    $scope.selection = $scope.steps[previousStep];
                }
            };
            /*****************************************/
            /************Personal Information************/

            $scope.getAge = function (birthday) {
                if (birthday !== undefined) {
                    var date = new Date(birthday);
                    var ageDifMs = Date.now() - date.getTime();
                    var ageDate = new Date(ageDifMs);
                    return Math.abs(ageDate.getUTCFullYear() - 1970);
                }
                ;
            };
            $scope.primaryApplicant = {
                'id': 1,
                'name': null,
                'gender': null,
                'dob': new Date(),
                'age': 0
            };
            $scope.coApplicant1 = {
                'id': 2,
                'name': null,
                'gender': null,
                'dob': new Date(),
                'age': 0
            };
            $scope.coApplicant2 = {
                'id': 3,
                'name': null,
                'gender': null,
                'dob': new Date(),
                'age': 0
            };
            $scope.setAge = function (applicant, dob) {
                switch (applicant.id) {
                    case 1:
                        $scope.primaryApplicant.age = $scope.getAge(dob);
                        break;
                    case 2:
                        $scope.coApplicant1.age = $scope.getAge(dob);
                        break;
                    case 3:
                        $scope.coApplicant2.age = $scope.getAge(dob);
                        break;
                    default:
                }
            };
            var getMainApplicant = function (user1, user2, user3) {
                var maxAge;
                maxAge = Math.max.apply(Math, [user1.age, user2.age, user3.age]);
                switch (maxAge) {
                    case user1.age:
                        return user1;
                        break;
                    case user2.age:
                        return user2;
                        break;
                    case user3.age:
                        return user3;
                        break;
                    default:
                }
            };
            $scope.options = [{name: "primary_app", id: 1}, {name: "co_app_1", id: 2}, {name: "co_app_2", id: 3}];
            $scope.priApplicant = true;
            $scope.selectedOption = $scope.options[0];
            $scope.getValue = function (val) {
                $scope.selectedOption = val;
                console.log("$scope.selectedOption", $scope.selectedOption);
                var applicantValue = val;
                if (applicantValue.id === 1) {
                    $scope.priApplicant = true;
                } else {
                    $scope.applicant = false;
                    $scope.applicantTwo = false;
                }
                if (applicantValue.id === 2) {
                    $scope.priApplicant = true;
                    $scope.applicant = true;
                } else {
                    $scope.applicant = false;
                }
                if (applicantValue.id === 3) {
                    $scope.priApplicant = true;
                    $scope.applicantTwo = true;
                    $scope.applicant = true;
                } else {
                    $scope.applicantTwo = false;
                }
            };
            $scope.datePicker = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.datePicker1 = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.datePicker2 = {
                opened: false,
                toggle: function () {
                    this.opened = !this.opened;
                }
            };
            $scope.personalInfo = function () {
                console.log("selectedOption", $scope.selectedOption.id);
                switch ($scope.selectedOption.id) {
                    case 1:
                        if ($scope.primaryApplicant.name !== null && $scope.primaryApplicant.dob !== null && $scope.primaryApplicant.gender !== null) {
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    case 2:
                        if ($scope.primaryApplicant.name !== null
                                && $scope.primaryApplicant.dob !== null
                                && $scope.primaryApplicant.gender !== null
                                && $scope.coApplicant1.name !== null
                                && $scope.coApplicant1.dob !== null
                                && $scope.coApplicant1.gender !== null) {
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    case 3:
                        if ($scope.primaryApplicant.name !== null && $scope.primaryApplicant.dob !== null && $scope.primaryApplicant.gender !== null && $scope.coApplicant1.name !== null && $scope.coApplicant1.dob !== null && $scope.coApplicant1.gender !== null && $scope.coApplicant2.name !== null && $scope.coApplicant2.dob !== null && $scope.coApplicant2.gender !== null) {
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    default:
                }
            };

//            $scope.propertyTypesList = PropertyTypeService.findAllEntries();
            PropertyTypeService.findAllEntries(function (propertyType) {
                $scope.propertyTypesList = propertyType;
                console.log("Property type List :%O", $scope.propertyTypesList);
            });
            $scope.family = {};
            $scope.$watch('family.numberOfRooms', function (room) {
                console.log("Change Detected:%O", room);
                $scope.totalNoOfRooms = 0;
                if (room === "1 BHK") {
                    $scope.totalNoOfRooms = 1;
                } else if (room === "2 BHK") {
                    $scope.totalNoOfRooms = 2;
                } else if (room === "3 BHK") {
                    $scope.totalNoOfRooms = 3;
                } else if (room === "4 BHK") {
                    $scope.totalNoOfRooms = 4;
                } else if (room === "5 BHK") {
                    $scope.totalNoOfRooms = 5;
                } else if (room === "6 BHK") {
                    $scope.totalNoOfRooms = 6;
                } else {
                    $scope.totalNoOfRooms = 0;
                }
                console.log("What are total Number Of Rooms :%O", $scope.totalNoOfRooms);
                $scope.submitFamilyDetails($scope.totalNoOfRooms);
            });
            $scope.submitFamilyDetails = function (totalRooms) {
                console.log("family", totalRooms);

//                $scope.totalRooms = [];
//                $scope.totalNoOfRooms = 0;
//
//                angular.forEach(totalRooms, function (room) {
//                    console.log("room", room);
//                    $scope.totalRooms.push(room);
//                });
//                $.each($scope.totalRooms, function () {
//                    $scope.totalNoOfRooms += this;
//                    console.log("total", $scope.totalNoOfRooms);
//                });
//                angular.forEach(totalRooms, function (a) {
//                    console.log("aaaaaaaaaaa::::::::", a);
//                });
//                if (totalRooms != null) {
//                    alert("Saved successfully");
//                } else {
//                    alert("Please fill the details");
//                }
                console.log("Total Number Of Rooms Kunal:%O", totalRooms);

                $scope.propertyType = PropertyTypeService.findByNumberOfBhkLike({
                    'numberOfBhkLike': totalRooms
                }, function (propertyType) {
                    angular.forEach(propertyType, function (propertyType1) {
                        $scope.propertyTypeArea = propertyType1;
                        $scope.carpetArea = propertyType1.carpetArea;
                        console.log("mila kya", $scope.carpetArea);
                    });
                    console.log("propertType", propertyType);
//                    $scope.propertyTypeArea = propertType;
//                    $scope.carpetArea = $scope.propertyTypeArea.carpetArea;

                });

            };
            /*****************************************/
            /***********Primary Income Details***************/
            $scope.primaryAppTotalIncome = 0;
            $scope.coAppTotalIncome = 0;
            $scope.coAppTwoTotalIncome = 0;
            $scope.CoApplicantTwoMonthlyIncome = 0;
            $scope.CoApplicantMonthlyIncome = 0;
            $scope.primaryApplicant.PrimaryAppPrimaryIncome = 0;
            $scope.primaryApplicant.primaryIncome = 'salaried';
            $scope.primaryApplicant.primaryIncomeVal = 'monthlyIncome';
            $scope.primaryApplicant.primaryValueType = 'monthlyIncome';
            $scope.coApplicant1.CoAppOnePrimaryIncome = 0;
            $scope.coApplicant1.primaryIncome = 'salaried';
            $scope.coApplicant1.primaryIncomeVal = 'monthlyIncome';
            $scope.coApplicant1.primaryValueType = 'monthlyIncome';
            $scope.coApplicant2.CoAppTwoPrimaryIncome = 0;
            $scope.coApplicant2.primaryIncome = 'salaried';
            $scope.coApplicant2.primaryIncomeVal = 'monthlyIncome';
            $scope.coApplicant2.primaryValueType = 'monthlyIncome';
            $scope.primaryincomeDetailsArray = {
                primaryIncomeDetails: [{
                        incomeType: '',
                        additionalIncomeType: 'monthlyIncome',
                        income: 0}]
            };
            $scope.savePrimaryIncomeDetails = function () {
                $scope.primaryincomeDetailsArray.primaryIncomeDetails.push({
                    incomeType: '',
                    additionalIncomeType: 'monthlyIncome',
                    income: 0
                });
//                 $scope.primaryApplicant.additionalIncomeDetails = $scope.primaryincomeDetailsArray;
            };
            $scope.getPrimaryIncomePrimaryType = function (primaryIncomeType) {
                $scope.primaryApplicant.primaryIncome = primaryIncomeType;
                console.log("primaryIncomeType", primaryIncomeType);
            };
            $scope.getPrimaryIncomeType = function (incomeVal) {
//                $scope.primaryApplicant.PrimaryAppPrimaryIncome = incomeVal * 12;
//                console.log("$scope.PrimaryAppPrimaryIncome" + $scope.PrimaryAppPrimaryIncome);
                console.log("we here", incomeVal);
                $scope.primaryApplicant.primaryValueType = incomeVal;
                $scope.primaryApplicant.PrimaryAppPrimaryIncome = 0;
            };
//            $scope.getPrimaryAnnualIncome = function (incomeVal) {
//                $scope.PrimaryAppPrimaryIncome = incomeVal * 1;
//                console.log("$scope.PrimaryAppPrimaryIncome" + $scope.PrimaryAppPrimaryIncome);
//            };
//            
            $scope.getPrimaryMonthlyIncomeValue = function (val) {
                if ($scope.primaryApplicant.primaryValueType === 'monthlyIncome') {
                    console.log("val this is monthly", val);
                    if (angular.isNumber(val)) {
                        $scope.primaryApplicant.PrimaryAppPrimaryIncome = val * 12;
                        console.log("$scope.primaryApplicant.PrimaryAppPrimaryIncome", $scope.primaryApplicant.PrimaryAppPrimaryIncome);
                        console.log("$scope.primaryApplicant", $scope.primaryApplicant);
                    } else {
                        $scope.primaryApplicant.PrimaryAppPrimaryIncome = 0;
                    }
                } else
                {
                    if (angular.isNumber(val)) {
                        console.log("this is annual");
                        $scope.primaryApplicant.PrimaryAppPrimaryIncome = val * 1;
                        console.log("$scope.primaryApplicant.PrimaryAppPrimaryIncome", $scope.primaryApplicant.PrimaryAppPrimaryIncome);
                        console.log("$scope.primaryApplicant", $scope.primaryApplicant);
                    } else {
                        $scope.primaryApplicant.PrimaryAppPrimaryIncome = 0;
                    }
                }
            };
            $scope.updatePrimaryApplicantDetailIncome = function (updateDetailsValue, $index) {
                console.log("updated value", updateDetailsValue, "index", $index);
                $scope.primaryincomeDetailsArray.primaryIncomeDetails[$index].income = updateDetailsValue;
                console.log($scope.primaryincomeDetailsArray.primaryIncomeDetails);
            };
            $scope.primaryApplicantTotal = function (incomeDetails) {
                var total = 0;
                angular.forEach(incomeDetails, function (detail) {
                    total += detail.income;
                });
                var totalValue = total * 0.7;
                console.log("totalValue", totalValue);
                $scope.primaryAppAdditonalTotalIncome = Number(totalValue.toFixed(2));
                console.log("$scope.primaryAppAdditonalTotalIncome", $scope.primaryAppAdditonalTotalIncome);
//                $scope.primaryAppAdditonalTotalIncome = total * 0.7;
                $scope.primaryAppTotalIncome = $scope.primaryAppAdditonalTotalIncome + $scope.primaryApplicant.PrimaryAppPrimaryIncome;
                console.log("$scope.primaryAppTotalIncome", $scope.primaryAppTotalIncome);
                return  $scope.primaryAppAdditonalTotalIncome + $scope.primaryApplicant.PrimaryAppPrimaryIncome;
            };
            $scope.removePrimaryItem = function (index) {
                $scope.primaryincomeDetailsArray.primaryIncomeDetails.splice(index, 1);
            };
            /***********Co-applicant Income Details***************/
            $scope.coAppincomeDetailsArray = {
                coAppIncomeDetails: [{
                        incomeType: '',
                        additionalIncomeType: 'coApplicantmonthlyIncome',
                        income: 0}]
            };
            $scope.saveCoAppIncomeDetails = function () {
                $scope.coAppincomeDetailsArray.coAppIncomeDetails.push({
                    incomeType: '',
                    additionalIncomeType: 'coApplicantmonthlyIncome',
                    income: 0
                });
//                $scope.coApplicant1.additionalIncomeDetails = $scope.coAppincomeDetailsArray;
            };
            $scope.getPrimaryIncomeCoAppOneType = function (primaryCoappIncomeType) {
                $scope.coApplicant1.primaryIncome = primaryCoappIncomeType;
                console.log("$scope.coApplicant1.primaryIncome", $scope.coApplicant1.primaryIncome);
            };
            $scope.getCoAppPrimaryIncomeType = function (incomeVal) {
//                $scope.primaryApplicant.PrimaryAppPrimaryIncome = incomeVal * 12;
//                console.log("$scope.PrimaryAppPrimaryIncome" + $scope.PrimaryAppPrimaryIncome);
                console.log("we here", incomeVal);
                $scope.coApplicant1.primaryValueType = incomeVal;
                $scope.coApplicant1.CoAppOnePrimaryIncome = 0;
            };
            $scope.getCoAppPrimaryIncomeValue = function (val) {
                if ($scope.coApplicant1.primaryValueType === 'monthlyIncome') {
                    if (angular.isNumber(val)) {
                        console.log("val this is monthly", val);
                        $scope.coApplicant1.CoAppOnePrimaryIncome = val * 12;
                        console.log("$scope.coApplicant1.CoAppOnePrimaryIncome", $scope.coApplicant1.CoAppOnePrimaryIncome);
                        console.log("$scope.coApplicant1", $scope.coApplicant1);
                    } else {
                        $scope.coApplicant1.CoAppOnePrimaryIncome = 0;
                    }
                } else {
                    if (angular.isNumber(val)) {
                        console.log("this is annual");
                        $scope.coApplicant1.CoAppOnePrimaryIncome = val * 1;
                        console.log("$scope.coApplicant1.CoAppOnePrimaryIncome", $scope.coApplicant1.CoAppOnePrimaryIncome);
                        console.log("$scope.coApplicant1", $scope.coApplicant1);
                    } else {
                        $scope.coApplicant1.CoAppOnePrimaryIncome = 0;
                    }
                }
            };
//            $scope.getCoApplicantMonthlyIncome = function (incomeVal) {
//                $scope.CoApplicantMonthlyIncome = incomeVal * 12;
//                console.log("$scope.CoApplicantMonthlyIncome * 12", $scope.CoApplicantMonthlyIncome);
//            };
//            $scope.getCoApplicantAnnualIncome = function (incomeVal) {
//                $scope.CoApplicantMonthlyIncome = incomeVal * 1;
//                console.log("$scope.CoApplicantMonthlyIncome * 1", $scope.CoApplicantMonthlyIncome);
//            };
            $scope.updateCoApplicantDetailIncome = function (updateDetailsValue, $index) {
                $scope.coAppincomeDetailsArray.coAppIncomeDetails[$index].income = updateDetailsValue;
            };
            $scope.coAppTotal = function (incomeDetails) {
                var coAppTotal = 0;
                angular.forEach(incomeDetails, function (detail) {
                    coAppTotal += detail.income;
                });
                var coAppTotalValue = coAppTotal * 0.7;
                console.log("coAppTotalValue", coAppTotalValue);
                $scope.coAppOneAdditonalTotalIncome = Number(coAppTotalValue.toFixed(2));
//                $scope.coAppOneAdditonalTotalIncome = coAppTotal * 0.7;
                console.log("$scope.coAppOneAdditonalTotalIncome ", $scope.coAppOneAdditonalTotalIncome);
                $scope.coAppTotalIncome = $scope.coAppOneAdditonalTotalIncome + $scope.coApplicant1.CoAppOnePrimaryIncome;
                console.log("$scope.coAppTotalIncome total:", $scope.coAppTotalIncome);
                return $scope.coAppOneAdditonalTotalIncome + $scope.coApplicant1.CoAppOnePrimaryIncome;
            };
            $scope.removeCoAppItem = function (index) {
                $scope.coAppincomeDetailsArray.coAppIncomeDetails.splice(index, 1);
            };
            /***********Co-applicant Two Income Details***************/
            $scope.coAppTwoincomeDetailsArray = {
                coAppTwoIncomeDetails: [{
                        incomeType: '',
                        additionalIncomeType: 'coApplicantTwomonthlyIncome',
                        income: 0}]
            };
            $scope.saveCoAppTwoIncomeDetails = function () {
                $scope.coAppTwoincomeDetailsArray.coAppTwoIncomeDetails.push({
                    incomeType: '',
                    additionalIncomeType: 'coApplicantTwomonthlyIncome',
                    income: 0
                });
//                $scope.coApplicant2.additionalIncomeDetails = $scope.coAppTwoincomeDetailsArray;
            };
            $scope.getPrimaryIncomeCoAppTwoType = function (primaryCoappIncomeType) {
                $scope.coApplicant2.primaryIncome = primaryCoappIncomeType;
                console.log("$scope.coApplicant2.primaryIncome", $scope.coApplicant2.primaryIncome);
            };
            $scope.getCoAppTwoPrimaryIncomeType = function (incomeVal) {
//                $scope.primaryApplicant.PrimaryAppPrimaryIncome = incomeVal * 12;
//                console.log("$scope.PrimaryAppPrimaryIncome" + $scope.PrimaryAppPrimaryIncome);
                console.log("we here", incomeVal);
                $scope.coApplicant2.primaryValueType = incomeVal;
                $scope.coApplicant2.CoAppTwoPrimaryIncome = 0;
            };
            $scope.getCoAppTwoPrimaryIncomeValue = function (val) {
                if ($scope.coApplicant2.primaryValueType === 'monthlyIncome') {
                    if (angular.isNumber(val)) {
                        console.log("val this is monthly", val);
                        $scope.coApplicant2.CoAppTwoPrimaryIncome = val * 12;
                        console.log("$scope.coApplicant2.CoAppTwoPrimaryIncome", $scope.coApplicant2.CoAppTwoPrimaryIncome);
                        console.log("$scope.coApplicant2", $scope.coApplicant2);
                    } else {
                        $scope.coApplicant2.CoAppTwoPrimaryIncome = 0;
                    }
                } else {
                    if (angular.isNumber(val)) {
                        console.log("this is annual");
                        $scope.coApplicant2.CoAppTwoPrimaryIncome = val * 1;
                        console.log("$scope.coApplicant2.CoAppTwoPrimaryIncome", $scope.coApplicant2.CoAppTwoPrimaryIncome);
                        console.log("$scope.coApplicant2", $scope.coApplicant2);
                    } else {
                        $scope.coApplicant2.CoAppTwoPrimaryIncome = 0;
                    }
                }
            };
//            $scope.getCoApplicantTwoMonthlyIncome = function (incomeVal) {
//                $scope.CoApplicantTwoMonthlyIncome = incomeVal * 12;
//            };
//            $scope.getCoApplicantTwoAnnualIncome = function (incomeVal) {
//                $scope.CoApplicantTwoMonthlyIncome = incomeVal * 1;
//            };
            $scope.updateCoApplicantTwoDetailIncome = function (updateDetailsValue, $index) {

                $scope.coAppTwoincomeDetailsArray.coAppTwoIncomeDetails[$index].income = updateDetailsValue;
            };
            $scope.coAppTwoTotal = function (incomeDetails) {
                var coAppTwoTotal = 0;
                angular.forEach(incomeDetails, function (detail) {
                    coAppTwoTotal += detail.income;
                });
                $scope.coAppTwoAdditonalTotalIncome = coAppTwoTotal;
                $scope.coAppTwoTotalIncome = coAppTwoTotal + $scope.coApplicant2.CoAppTwoPrimaryIncome;
                return coAppTwoTotal + $scope.coApplicant2.CoAppTwoPrimaryIncome;
            };
            $scope.removeCoAppTwoItem = function (index) {
                $scope.coAppTwoincomeDetailsArray.coAppTwoIncomeDetails.splice(index, 1);
            };
            $scope.showIncomeDetailsTotal = function () {
                console.log("priamry", $scope.primaryApplicant, "co1", $scope.coApplicant1, "co2", $scope.coApplicant2);
                $scope.IncomeDetailsTotalIncome = $scope.coAppTwoTotalIncome + $scope.coAppTotalIncome + $scope.primaryAppTotalIncome;
                $scope.incomeDetail = true;
            };
            $scope.incomeDetails = function () {

                console.log("selectedOption", $scope.selectedOption.id);
                switch ($scope.selectedOption.id) {
                    case 1:
                        if ($scope.primaryApplicant.PrimaryAppPrimaryIncome !== 0) {
                            console.log("bhai chal gaya");
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    case 2:
                        if ($scope.primaryApplicant.PrimaryAppPrimaryIncome !== 0 && $scope.coApplicant1.CoAppOnePrimaryIncome !== 0) {
                            console.log("bhai chal gaya", $scope.coApplicant1);
                            console.log("bhai chal gaya", $scope.primaryApplicant);
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    case 3:
                        if ($scope.primaryApplicant.PrimaryAppPrimaryIncome !== 0 && $scope.coApplicant1.CoAppOnePrimaryIncome !== 0 && $scope.coApplicant2.CoAppTwoPrimaryIncome !== 0) {
                            console.log("bhai chal gaya");
                            $scope.incrementStep();
                        } else {
                            alert('Please fill the mandatory fields.');
                        }
                        break;
                    default:
                }

            };
            /*********************************************************/
            /*********************Liabilities************************************/
            /**************************************************************************************/
            $scope.primaryApplicantEmiLiability = null;
            $scope.emiLiabilityOnChange = null;
            $scope.emiEligibility = null;
            $scope.loanEligibility = null;
//            $scope.cashInHand = null;
//            $scope.grossBudget = null;
            $scope.eligiblePropertyValue = null;
            $scope.primaryApplicantChildrenEducation = null;
            $scope.primaryApplicantDaughtersMarriage = null;
            $scope.primaryApplicantVehiclePurchase = null;
            $scope.primaryApplicantMedicalExpenditure = null;
            $scope.primaryApplicantOtherExpense = null;
            $scope.primaryApplicantSavings = null;
            $scope.primaryApplicantLiquidableInvestment = null;
            $scope.primaryApplicantOtherPlannedIncome = null;
            $scope.totalPrimaryAssetsIncomeValue = 0;
            $scope.primaryApplicantTotalLiabilityIncomeValue = 0;
            $scope.editableliabilityBank = {};
            $scope.setBank = function (bank) {
                $scope.editableliabilityBank.bankId = bank.id;
                $scope.editableliabilityBank.bank = bank;
                console.log("$scope.editableliabilityBank.bank", $scope.editableliabilityBank.bank);
            };
//            =========Primary applicant======
            $scope.getPrimaryApplicantEmiLiability = function (primaryApplicantEmiLiability) {
                console.log("primaryApplicantEmiLiability : ", primaryApplicantEmiLiability);
                $scope.primaryApplicantEmiLiability = primaryApplicantEmiLiability;
                $scope.primaryApplicantEmiLiability = primaryApplicantEmiLiability;
                console.log("$scope.primaryApplicantEmiLiability : ", $scope.primaryApplicantEmiLiability);
            };
            $scope.getPrimaryApplicantChildrenEducation = function (primaryApplicantChildrenEducation) {
                console.log("primaryApplicantChildrenEducation : ", primaryApplicantChildrenEducation);
                $scope.primaryApplicantChildrenEducation = primaryApplicantChildrenEducation;
                console.log("$scope.primaryApplicantChildrenEducation : ", $scope.primaryApplicantChildrenEducation);
            };
            $scope.getPrimaryApplicantDaughtersMarriage = function (primaryApplicantDaughtersMarriage) {
                console.log("primaryApplicantDaughtersMarriage : ", primaryApplicantDaughtersMarriage);
                $scope.primaryApplicantDaughtersMarriage = primaryApplicantDaughtersMarriage;
                console.log("$scope.primaryApplicantDaughtersMarriage : ", $scope.primaryApplicantDaughtersMarriage);
            };
            $scope.getPrimaryApplicantVehiclePurchase = function (primaryApplicantVehiclePurchase) {
                console.log("primaryApplicantVehiclePurchase : ", primaryApplicantVehiclePurchase);
                $scope.primaryApplicantVehiclePurchase = primaryApplicantVehiclePurchase;
                console.log("$scope.primaryApplicantVehiclePurchase : ", $scope.primaryApplicantVehiclePurchase);
            };
            $scope.getPrimaryApplicantMedicalExpenditure = function (primaryApplicantMedicalExpenditure) {
                console.log("primaryApplicantMedicalExpenditure : ", primaryApplicantMedicalExpenditure);
                $scope.primaryApplicantMedicalExpenditure = primaryApplicantMedicalExpenditure;
                console.log("$scope.primaryApplicantMedicalExpenditure : ", $scope.primaryApplicantMedicalExpenditure);
            };
            $scope.getPrimaryApplicantOtherExpense = function (primaryApplicantOtherExpense) {
                console.log("primaryApplicantOtherExpense : ", primaryApplicantOtherExpense);
                $scope.primaryApplicantOtherExpense = primaryApplicantOtherExpense;
                console.log("$scope.primaryApplicantOtherExpense : ", $scope.primaryApplicantOtherExpense);
            };
            $scope.showPrimaryApplicantLiabilityTotalIncome = function () {
                console.log(" check lia total ");
                $scope.primaryApplicantTotalLiabilityIncomeValue = $scope.primaryApplicantOtherExpense + $scope.primaryApplicantMedicalExpenditure + $scope.primaryApplicantVehiclePurchase + $scope.primaryApplicantDaughtersMarriage + $scope.primaryApplicantChildrenEducation;
                console.log("$scope.primaryApplicantTotalLiabilityIncomeValue", $scope.primaryApplicantTotalLiabilityIncomeValue);
                $scope.LiablitiyValue = true;
            };
            $scope.showPrimaryApplicantAssetsTotalIncome = function () {
                console.log(" check lia total ");
                //$scope.primaryApplicantTotalLiabilityIncomeValue = $scope.primaryApplicantOtherExpense + $scope.primaryApplicantMedicalExpenditure + $scope.primaryApplicantVehiclePurchase + $scope.primaryApplicantDaughtersMarriage + $scope.primaryApplicantChildrenEducation;
                $scope.totalPrimaryAssetsIncomeValue = $scope.primaryApplicantSavings + $scope.primaryApplicantLiquidableInvestment + $scope.primaryApplicantOtherPlannedIncome;
                console.log("$scope.totalPrimaryAssetsIncomeValue", $scope.totalPrimaryAssetsIncomeValue);
                $scope.AssestsValue = true;
                $scope.AssestsValues = true;
                $scope.primarycashInHandDetails = $scope.totalPrimaryAssetsIncomeValue - $scope.primaryApplicantTotalLiabilityIncomeValue;
                console.log("$scope.primarycashInHandDetails", $scope.primarycashInHandDetails);
            };
            /*********************************************************/
            /*********************Liabilities************************************/
            /**************************************************************************************/
            $scope.primaryApplicantEmiLiability = null;
            $scope.emiLiabilityOnChange = null;
            $scope.emiEligibility = null;
            $scope.loanEligibility = null;
//            $scope.cashInHand = null;
//            $scope.grossBudget = null;
            $scope.eligiblePropertyValue = null;
            $scope.coApplicantOneChildrenEducation = null;
            $scope.coApplicantOneDaughtersMarriage = null;
            $scope.coApplicantOneVehiclePurchase = null;
            $scope.coApplicantOneMedicalExpenditure = null;
            $scope.coApplicantOneOtherExpense = null;
            $scope.coApplicantOneSavings = null;
            $scope.coApplicantOneLiquidableInvestment = null;
            $scope.coApplicantOneOtherPlannedIncome = null;
            $scope.totalCoAppOneAssetsIncomeValue = 0;
            $scope.coApplicantOneTotalLiabilityIncomeValue = 0;
//            =========Co-applicant 1======
            $scope.getCoApplicantOneEmiLiability = function (coApplicantOneEmiLiability) {
                console.log("coApplicantOneEmiLiability : ", coApplicantOneEmiLiability);
                $scope.coApplicantOneEmiLiability = coApplicantOneEmiLiability;
                console.log("$scope.coApplicantOneEmiLiability : ", $scope.coApplicantOneEmiLiability);
            };
            $scope.getCoApplicantOneChildrenEducation = function (coApplicantOneChildrenEducation) {
                console.log("coApplicantOneChildrenEducation : ", coApplicantOneChildrenEducation);
                $scope.coApplicantOneChildrenEducation = coApplicantOneChildrenEducation;
                console.log("$scope.coApplicantOneChildrenEducation : ", $scope.coApplicantOneChildrenEducation);
            };
            $scope.getCoApplicantOneDaughtersMarriage = function (coApplicantOneDaughtersMarriage) {
                console.log("coApplicantOneDaughtersMarriage : ", coApplicantOneDaughtersMarriage);
                $scope.coApplicantOneDaughtersMarriage = coApplicantOneDaughtersMarriage;
                console.log("$scope.coApplicantOneDaughtersMarriage : ", $scope.coApplicantOneDaughtersMarriage);
            };
            $scope.getCoApplicantOneVehiclePurchase = function (coApplicantOneVehiclePurchase) {
                console.log("coApplicantOneVehiclePurchase : ", coApplicantOneVehiclePurchase);
                $scope.coApplicantOneVehiclePurchase = coApplicantOneVehiclePurchase;
                console.log("$scope.coApplicantOneVehiclePurchase : ", $scope.coApplicantOneVehiclePurchase);
            };
            $scope.getCoApplicantOneMedicalExpenditure = function (coApplicantOneMedicalExpenditure) {
                console.log("coApplicantOneMedicalExpenditure : ", coApplicantOneMedicalExpenditure);
                $scope.coApplicantOneMedicalExpenditure = coApplicantOneMedicalExpenditure;
                console.log("$scope.coApplicantOneMedicalExpenditure : ", $scope.coApplicantOneMedicalExpenditure);
            };
            $scope.getCoApplicantOneOtherExpense = function (coApplicantOneOtherExpense) {
                console.log("coApplicantOneOtherExpense : ", coApplicantOneOtherExpense);
                $scope.coApplicantOneOtherExpense = coApplicantOneOtherExpense;
                console.log("$scope.coApplicantOneOtherExpense : ", $scope.coApplicantOneOtherExpense);
            };
            $scope.showCoApplicantOneLiabilityTotalIncome = function () {
                console.log(" check lia total ");
                $scope.coApplicantOneTotalLiabilityIncomeValue = $scope.coApplicantOneOtherExpense + $scope.coApplicantOneMedicalExpenditure + $scope.coApplicantOneVehiclePurchase + $scope.coApplicantOneDaughtersMarriage + $scope.coApplicantOneChildrenEducation;
                console.log("$scope.coApplicantOneTotalLiabilityIncomeValue", $scope.coApplicantOneTotalLiabilityIncomeValue);
                $scope.LiablitiyValueTwo = true;
            };
            $scope.showCoApplicantOneAssetsTotalIncome = function () {
                console.log(" check lia total ");
                //$scope.primaryApplicantTotalLiabilityIncomeValue = $scope.primaryApplicantOtherExpense + $scope.primaryApplicantMedicalExpenditure + $scope.primaryApplicantVehiclePurchase + $scope.primaryApplicantDaughtersMarriage + $scope.primaryApplicantChildrenEducation;
                $scope.totalCoAppOneAssetsIncomeValue = $scope.coApplicantOneSavings + $scope.coApplicantOneLiquidableInvestment + $scope.coApplicantOneOtherPlannedIncome;
                console.log("$scope.totalCoAppOneAssetsIncomeValue", $scope.totalCoAppOneAssetsIncomeValue);
                $scope.AssestsValueTwo = true;
                $scope.AssestsValuesTwo = true;
                $scope.CoAppOnecashInHandDetails = $scope.totalCoAppOneAssetsIncomeValue - $scope.coApplicantOneTotalLiabilityIncomeValue;
                console.log("$scope.CoAppOnecashInHandDetails", $scope.CoAppOnecashInHandDetails);
            };
//            $scope.showLiabilityAssetsIncomeTotal = function () {
////                console.log(" check lia total ");
////                //$scope.totalLiabilityIncomeValue = $scope.otherExpense + $scope.medicalExpenditure + $scope.vehiclePurchase + $scope.daughtersMarriage + $scope.childrenEducation;
////                $scope.primaryApplicantTotalAssetsIncomeValue = $scope.savings + $scope.liquidableInvestment + $scope.otherPlannedIncome;
////                console.log("$scope.totalLiabilityIncomeValue", $scope.totalLiabilityIncomeValue);
////                $scope.LiablitiyValue = true;
////                $scope.AssestsValue = true;
//                $scope.incrementStep();
//            };
            /*********************Liabilities************************************/
            /**************************************************************************************/
            $scope.primaryApplicantEmiLiability = null;
            $scope.emiLiabilityOnChange = null;
            $scope.emiEligibility = null;
            $scope.loanEligibility = null;
            $scope.cashInHand = null;
            $scope.grossBudget = null;
            $scope.eligiblePropertyValue = null;
            $scope.coApplicantTwoChildrenEducation = null;
            $scope.coApplicantTwoDaughtersMarriage = null;
            $scope.coApplicantTwoVehiclePurchase = null;
            $scope.coApplicantTwoMedicalExpenditure = null;
            $scope.coApplicantTwoOtherExpense = null;
            $scope.coApplicantTwoSavings = null;
            $scope.coApplicantTwoLiquidableInvestment = null;
            $scope.coApplicantTwoOtherPlannedIncome = null;
            $scope.coApplicantTwoTotalLiabilityIncomeValue = 0;
            $scope.totalCoAppTwoAssetsIncomeValue = 0;
//            =========Co-applicant 1======
            $scope.getCoApplicantTwoEmiLiability = function (coApplicantTwoEmiLiability) {
                console.log("coApplicantTwoEmiLiability : ", coApplicantTwoEmiLiability);
                $scope.coApplicantTwoEmiLiability = coApplicantTwoEmiLiability;
                console.log("$scope.coApplicantTwoEmiLiability : ", $scope.coApplicantTwoEmiLiability);
            };
            $scope.getCoApplicantTwoChildrenEducation = function (coApplicantTwoChildrenEducation) {
                console.log("coApplicantTwoChildrenEducation : ", coApplicantTwoChildrenEducation);
                $scope.coApplicantTwoChildrenEducation = coApplicantTwoChildrenEducation;
                console.log("$scope.coApplicantTwoChildrenEducation : ", $scope.coApplicantTwoChildrenEducation);
            };
            $scope.getCoApplicantTwoDaughtersMarriage = function (coApplicantTwoDaughtersMarriage) {
                console.log("coApplicantTwoDaughtersMarriage : ", coApplicantTwoDaughtersMarriage);
                $scope.coApplicantTwoDaughtersMarriage = coApplicantTwoDaughtersMarriage;
                console.log("$scope.coApplicantTwoDaughtersMarriage : ", $scope.coApplicantTwoDaughtersMarriage);
            };
            $scope.getCoApplicantTwoVehiclePurchase = function (coApplicantTwoVehiclePurchase) {
                console.log("coApplicantTwoVehiclePurchase : ", coApplicantTwoVehiclePurchase);
                $scope.coApplicantTwoVehiclePurchase = coApplicantTwoVehiclePurchase;
                console.log("$scope.coApplicantTwoVehiclePurchase : ", $scope.coApplicantTwoVehiclePurchase);
            };
            $scope.getCoApplicantTwoMedicalExpenditure = function (coApplicantTwoMedicalExpenditure) {
                console.log("coApplicantTwoMedicalExpenditure : ", coApplicantTwoMedicalExpenditure);
                $scope.coApplicantTwoMedicalExpenditure = coApplicantTwoMedicalExpenditure;
                console.log("$scope.coApplicantTwoMedicalExpenditure : ", $scope.coApplicantTwoMedicalExpenditure);
            };
            $scope.getCoApplicantTwoOtherExpense = function (coApplicantTwoOtherExpense) {
                console.log("coApplicantTwoOtherExpense : ", coApplicantTwoOtherExpense);
                $scope.coApplicantTwoOtherExpense = coApplicantTwoOtherExpense;
                console.log("$scope.coApplicantTwoOtherExpense : ", $scope.coApplicantTwoOtherExpense);
            };
            $scope.showCoApplicantTwoLiabilityTotalIncome = function () {
                console.log(" check lia total ");
                $scope.coApplicantTwoTotalLiabilityIncomeValue = $scope.coApplicantTwoOtherExpense + $scope.coApplicantTwoMedicalExpenditure + $scope.coApplicantTwoVehiclePurchase + $scope.coApplicantTwoDaughtersMarriage + $scope.coApplicantTwoChildrenEducation;
                console.log("$scope.coApplicantTwoTotalLiabilityIncomeValue", $scope.coApplicantTwoTotalLiabilityIncomeValue);
                $scope.LiablitiyValueTwo = true;
                $scope.LiablitiyValueThree = true;
            };
            $scope.showCoApplicantTwoAssetsTotalIncome = function () {
                console.log(" check lia total ");
                //$scope.primaryApplicantTotalLiabilityIncomeValue = $scope.primaryApplicantOtherExpense + $scope.primaryApplicantMedicalExpenditure + $scope.primaryApplicantVehiclePurchase + $scope.primaryApplicantDaughtersMarriage + $scope.primaryApplicantChildrenEducation;
                $scope.totalCoAppTwoAssetsIncomeValue = $scope.coApplicantTwoSavings + $scope.coApplicantTwoLiquidableInvestment + $scope.coApplicantTwoOtherPlannedIncome;
                console.log("$scope.totalCoAppTwoAssetsIncomeValue", $scope.totalCoAppTwoAssetsIncomeValue);
                $scope.AssestsValueThree = true;
                $scope.AssestsValuesThree = true;
                $scope.CoAppTwocashInHandDetails = $scope.totalCoAppTwoAssetsIncomeValue - $scope.coApplicantTwoTotalLiabilityIncomeValue;
                console.log("$scope.CoAppTwocashInHandDetails", $scope.CoAppTwocashInHandDetails);
            };
            $scope.showLiabilityAssetsIncomeTotal = function () {
//                console.log(" check lia total ");
//                //$scope.totalLiabilityIncomeValue = $scope.otherExpense + $scope.medicalExpenditure + $scope.vehiclePurchase + $scope.daughtersMarriage + $scope.childrenEducation;
//                $scope.primaryApplicantTotalAssetsIncomeValue = $scope.savings + $scope.liquidableInvestment + $scope.otherPlannedIncome;
//                console.log("$scope.totalLiabilityIncomeValue", $scope.totalLiabilityIncomeValue);
//                $scope.LiablitiyValue = true;
//                $scope.AssestsValue = true;
                $scope.incrementStep();
            };
            $scope.show = function () {
                $scope.showReport = true;
                console.log("Primary App Object:", $scope.primaryApplicant);
                console.log("Co App 1 Object:", $scope.coApplicant1);
                console.log("Co App 2 Object:", $scope.coApplicant2);
                $scope.totalAssetsIncomeValue = $scope.totalPrimaryAssetsIncomeValue + $scope.totalCoAppOneAssetsIncomeValue + $scope.totalCoAppTwoAssetsIncomeValue;
                $scope.totalLiabilityIncomeValue = $scope.primaryApplicantTotalLiabilityIncomeValue + $scope.coApplicantOneTotalLiabilityIncomeValue + $scope.coApplicantTwoTotalLiabilityIncomeValue;
                console.log(" $scope.totalAssetsIncomeValue", $scope.totalAssetsIncomeValue);
                console.log(" $scope.totalLiabilityIncomeValue", $scope.totalLiabilityIncomeValue);

                var sumOfAllAplicants;
                var rateOfInterestForLoan;
                sumOfAllAplicants = $scope.coAppTwoTotalIncome + $scope.coAppTotalIncome + $scope.primaryAppTotalIncome;
                console.log("sumOfAllAplicants", sumOfAllAplicants);
                var mainApplicant = getMainApplicant($scope.primaryApplicant, $scope.coApplicant1, $scope.coApplicant2);
                console.log("mainApplicant", mainApplicant);
                if (mainApplicant.primaryIncome === "salaried") {
                    $scope.loanTenureEligibility = $scope.editableliabilityBank.bank.maxAgeForSalaried - mainApplicant.age;
                    console.log(" $scope.loanTenure monthly wala", $scope.loanTenureEligibility);
                } else {
                    $scope.loanTenureEligibility = $scope.editableliabilityBank.bank.maxAgeForBusinessman - mainApplicant.age;
                    console.log(" $scope.loanTenure annual wala", $scope.loanTenureEligibility);
                }

                if ($scope.loanTenureEligibility > $scope.editableliabilityBank.bank.maxLoanTenure) {
                    $scope.loanTenureEligibility = $scope.editableliabilityBank.bank.maxLoanTenure;
                    console.log("$scope.loanTenure", $scope.loanTenure);
                } else {
                    $scope.loanTenureEligibility;
                    console.log("$scope.loanTenure", $scope.loanTenure);
                }

                if (mainApplicant.gender === "male") {
                    rateOfInterestForLoan = $scope.editableliabilityBank.bank.loanInterestRateForMale;
                } else {
                    rateOfInterestForLoan = $scope.editableliabilityBank.bank.loanInterestRateForFemale;
                }
                $scope.cashInHand = $scope.totalAssetsIncomeValue - $scope.totalLiabilityIncomeValue;
                console.log("$scope.cashInHand", $scope.cashInHand);
                $scope.entryForEMICalculator = {
                    amount: 100000,
                    tenure: $scope.loanTenureEligibility * 12,
                    interest: rateOfInterestForLoan
                };
                console.log("$scope.entryForEMICalculator.tenure", $scope.entryForEMICalculator.tenure);
                var vals = $scope.entryForEMICalculator, i;
                $scope.months = [];
                for (i = 0; i < vals.tenure; i++) {
                    var m = i + 1;
                    calculatedEMI = calcumateemi(vals.tenure, vals.amount, vals.interest, i);
                    interestTotal = calculatedEMI * vals.tenure;
                    $scope.months.push({
                        sno: m,
                        emi: calculatedEMI,
                        balance: Math.floor(interestTotal - (calculatedEMI * m))
                    });
                }
                console.log("entryForEMICalculator", $scope.entryForEMICalculator);
                console.log("calculatedEMI", calculatedEMI);
                $scope.totalInterestOnLoan = (calcumateemi($scope.entryForEMICalculator.tenure, $scope.entryForEMICalculator.amount, $scope.entryForEMICalculator.interest, i) * $scope.entryForEMICalculator.tenure) - $scope.entryForEMICalculator.amount;
                $scope.editableLiabilityIncomeSlabs = IncomeSlabService.findByBankId({
                    'bankId': $scope.editableliabilityBank.bankId
                }, function (incomeSlabs) {
                    console.log(incomeSlabs);
//                    var EMILIABILITY = $scope.emiLiability;
                    angular.forEach(incomeSlabs, function (incomeSlab) {
                        console.log("$scope.emiLiability", $scope.emiLiability);
                        if (angular.isNumber($scope.emiLiability)) {
                            if (sumOfAllAplicants >= incomeSlab.minRange && sumOfAllAplicants <= incomeSlab.maxRange) {
                                $scope.emiEligibility = ((sumOfAllAplicants * (incomeSlab.percentageDeduction / 100)) / 12) - $scope.emiLiability;
                                console.log("$scope.emiEligibility - $scope.emiLiability", $scope.emiEligibility);
                            }
                            ;
                        } else {
                            if (sumOfAllAplicants >= incomeSlab.minRange && sumOfAllAplicants <= incomeSlab.maxRange) {
                                $scope.emiEligibility = (sumOfAllAplicants * (incomeSlab.percentageDeduction / 100)) / 12;
                                console.log("$scope.emiEligibility", $scope.emiEligibility);
                            }
                            ;
                        }
                        ;
                    });
                    console.log("$scope.emiEligibility", $scope.emiEligibility);
                    $scope.loanEligibility = ($scope.emiEligibility / calculatedEMI) * 100000;
                    console.log("$scope.loanEligibility", $scope.loanEligibility);
                    $scope.grossBudget = $scope.cashInHand + $scope.loanEligibility;
                    $scope.eligiblePropertyValue = $scope.grossBudget - ($scope.grossBudget * 0.15);
                    console.log("$scope.eligiblePropertyValue", $scope.eligiblePropertyValue);
                    $scope.eligiblePropertyMaxValue = $scope.eligiblePropertyValue;
                    console.log("max value", $scope.eligiblePropertyMaxValue);
                    $scope.eligiblePropertyMinValue = $scope.eligiblePropertyValue * 0.80;
                    console.log("min value", $scope.eligiblePropertyMinValue);
                    console.log("Carpet Area :%O", $scope.carpetArea);
                    $scope.eligibleMaxSqFtArea = $scope.eligiblePropertyMaxValue / $scope.carpetArea;
                    console.log("$scope.eligibleMaxSqFtArea", $scope.eligibleMaxSqFtArea);
                    $scope.eligibleMinSqFtArea = $scope.eligiblePropertyMinValue / $scope.carpetArea;
                    console.log("$scope.eligibleMinSqFtArea", $scope.eligibleMinSqFtArea);
                });
            };
            $scope.showLoanTenureChange = function (selectedNewLoanTenure) {
                console.log("selectedNewLoanTenure", selectedNewLoanTenure);
                $scope.loanTenureEligibilityOnChange = selectedNewLoanTenure;
                console.log("loanTenureEligibilityOnChange", $scope.loanTenureEligibilityOnChange);
                console.log("$scope.emiLiabilityOnChange", $scope.emiLiabilityOnChange);
                var sumOfAllAplicantsOnChange;
                var rateOfInterestForLoanOnChange;
                sumOfAllAplicantsOnChange = $scope.coAppTwoTotalIncome + $scope.coAppTotalIncome + $scope.primaryAppTotalIncome;
                console.log("sumOfAllAplicantsOnChange", sumOfAllAplicantsOnChange);
                var mainApplicantOnChange = getMainApplicant($scope.primaryApplicant, $scope.coApplicant1, $scope.coApplicant2);
                console.log("mainApplicantOnChange", mainApplicantOnChange);
//                if (mainApplicantOnChange.primaryIncome === "salaried") {
//                    $scope.loanTenureEligibilityOnChange = 60 - mainApplicantOnChange.age;
//                    console.log(" $scope.loanTenure monthly wala", $scope.loanTenureEligibilityOnChange);
//                } else {
//                    $scope.loanTenureEligibilityOnChange = 70 - mainApplicantOnChange.age;
//                    console.log(" $scope.loanTenure annual wala", $scope.loanTenureEligibilityOnChange);
//                }
//////
//                if ($scope.loanTenureEligibilityOnChange > $scope.editableliabilityBank.bank.maxLoanTenure) {
//                    $scope.loanTenureEligibilityOnChange = $scope.editableliabilityBank.bank.maxLoanTenure;
//                    console.log("$scope.loanTenure", $scope.loanTenure);
//                } else {
//                    $scope.loanTenureEligibilityOnChange;
//                    console.log("$scope.loanTenure", $scope.loanTenure);
//                }

                if (mainApplicantOnChange.gender === "male") {
                    rateOfInterestForLoanOnChange = $scope.editableliabilityBank.bank.loanInterestRateForMale;
                } else {
                    rateOfInterestForLoanOnChange = $scope.editableliabilityBank.bank.loanInterestRateForFemale;
                }
                $scope.cashInHandOnChange = $scope.totalAssetsIncomeValue - $scope.totalLiabilityIncomeValue;
                $scope.entryForEMICalculatorOnChange = {
                    amount: 100000,
                    tenure: $scope.loanTenureEligibilityOnChange * 12,
                    interest: rateOfInterestForLoanOnChange
                };
                console.log("$scope.entryForEMICalculatorOnChange.tenure", $scope.entryForEMICalculatorOnChange.tenure);
                var valss = $scope.entryForEMICalculatorOnChange, i;
                $scope.monthsOnChange = [];
                for (i = 0; i < valss.tenure; i++) {
                    var m = i + 1;
                    calculatedEMIOnChange = calcumateemi(valss.tenure, valss.amount, valss.interest, i);
                    interestTotalOnChange = calculatedEMIOnChange * valss.tenure;
                    $scope.monthsOnChange.push({
                        sno: m,
                        emi: calculatedEMIOnChange,
                        balance: Math.floor(interestTotalOnChange - (calculatedEMIOnChange * m))
                    });
                }
                console.log("entryForEMICalculatorOnChange", $scope.entryForEMICalculatorOnChange);
                console.log("calculatedEMIOnChange", calculatedEMIOnChange);
                $scope.totalInterestOnLoanOnChange = (calcumateemi($scope.entryForEMICalculatorOnChange.tenure, $scope.entryForEMICalculatorOnChange.amount, $scope.entryForEMICalculatorOnChange.interest, i) * $scope.entryForEMICalculatorOnChange.tenure) - $scope.entryForEMICalculatorOnChange.amount;
                $scope.editableLiabilityIncomeSlabsOnChange = IncomeSlabService.findByBankId({
                    'bankId': $scope.editableliabilityBank.bankId
                }, function (incomeSlabs) {
                    console.log(incomeSlabs);
//                    var EMILIABILITY = $scope.emiLiability;
                    angular.forEach(incomeSlabs, function (incomeSlab) {
                        console.log("$scope.emiLiabilityOnChange", $scope.emiLiabilityOnChange);
                        if (angular.isNumber($scope.emiLiabilityOnChange)) {
                            if (sumOfAllAplicantsOnChange >= incomeSlab.minRange && sumOfAllAplicantsOnChange <= incomeSlab.maxRange) {
                                $scope.emiEligibilityOnChange = ((sumOfAllAplicantsOnChange * (incomeSlab.percentageDeduction / 100)) / 12) - $scope.emiLiabilityOnChange;
                                console.log("$scope.$scope.emiEligibilityOnChange - $scope.emiLiability", $scope.emiEligibilityOnChange);
                            }
                            ;
                        } else {
                            if (sumOfAllAplicantsOnChange >= incomeSlab.minRange && sumOfAllAplicantsOnChange <= incomeSlab.maxRange) {
                                $scope.emiEligibilityOnChange = (sumOfAllAplicantsOnChange * (incomeSlab.percentageDeduction / 100)) / 12;
                                console.log("$scope.$scope.emiEligibilityOnChange", $scope.emiEligibilityOnChange);
                            }
                            ;
                        }
                        ;
                    });
                    console.log("$scope.emiEligibilityOnChange", $scope.emiEligibilityOnChange);
                    $scope.loanEligibilityOnChange = ($scope.emiEligibilityOnChange / calculatedEMIOnChange) * 100000;
                    console.log("$scope.loanEligibilityOnChange", $scope.loanEligibilityOnChange);
                    $scope.grossBudgetOnChange = $scope.cashInHandOnChange + $scope.loanEligibilityOnChange;
                    $scope.eligiblePropertyValueOnChange = $scope.grossBudgetOnChange - ($scope.grossBudgetOnChange * 0.15);
                    $scope.eligiblePropertyMaxValueOnChange = $scope.eligiblePropertyValueOnChange;
                    console.log("max value", $scope.eligiblePropertyMaxValueOnChange);
                    $scope.eligiblePropertyMinValueOnChange = $scope.eligiblePropertyValue * 0.80;
                    console.log("min value", $scope.eligiblePropertyMinValueOnChange);
                    $scope.eligibleMaxSqFtAreaOnChange = $scope.eligiblePropertyMaxValueOnChange / $scope.carpetArea;
                    console.log("$scope.eligibleMaxSqFtArea", $scope.eligibleMaxSqFtAreaOnChange);
                    $scope.eligibleMinSqFtAreaOnChange = $scope.eligiblePropertyMinValueOnChange / $scope.carpetArea;
                    console.log("$scope.eligibleMinSqFtArea", $scope.eligibleMinSqFtAreaOnChange);
                });

            };
            /*********************************************************/
            /*********************Assets************************************/
            $scope.banks = [];
            $scope.banks = BankService.findAllBanks();
            console.log("banks name", $scope.banks);
            $scope.cities = CityService.findAllCities();
            console.log("my city list ", $scope.cities);

            $scope.selectedCity = {};
            $scope.setCity = function (city) {
                $scope.selectedCity = city;
                console.log("$scope.editableCity.city", $scope.selectedCity);
            };
            $scope.showGrossBudget = function () {
                $scope.grossBudget = $scope.cashInHand + $scope.loanEligibility;
                console.log("$scope.grossBudget", $scope.grossBudget);
                $scope.eligiblePropertyValue = $scope.grossBudget - ($scope.grossBudget * 0.15);
                console.log("$scope.eligiblePropertyValue", $scope.eligiblePropertyValue);
            };
            $scope.showReport = function () {
                $scope.emiEligibility;
                $scope.loanEligibility;
                $scope.cashInHand;
                $scope.grossBudget;
                $scope.eligiblePropertyValue;
            };
            $scope.getPrimaryApplicantAssetsSavings = function (primaryApplicantSavings) {
                console.log("primaryApplicantSavings", primaryApplicantSavings);
                $scope.primaryApplicantSavings = primaryApplicantSavings;
            };
            $scope.getPrimaryApplicantLiquidableInvestment = function (primaryApplicantLiquidableInvestment) {
                console.log("primaryApplicantLiquidableInvestment", primaryApplicantLiquidableInvestment);
                $scope.primaryApplicantLiquidableInvestment = primaryApplicantLiquidableInvestment;
            };
            $scope.getPrimaryApplicantOtherPlannedIncome = function (primaryApplicantOtherPlannedIncome) {
                console.log("primaryApplicantOtherPlannedIncome", primaryApplicantOtherPlannedIncome);
                $scope.primaryApplicantOtherPlannedIncome = primaryApplicantOtherPlannedIncome;
            };
            $scope.getCoApplicantOneAssetsSavings = function (coApplicantOneSavings) {
                console.log("coApplicantOneSavings", coApplicantOneSavings);
                $scope.coApplicantOneSavings = coApplicantOneSavings;
            };
            $scope.getCoApplicantOneLiquidableInvestment = function (coApplicantOneLiquidableInvestment) {
                console.log("coApplicantOneLiquidableInvestment", coApplicantOneLiquidableInvestment);
                $scope.coApplicantOneLiquidableInvestment = coApplicantOneLiquidableInvestment;
            };
            $scope.getCoApplicantOneOtherPlannedIncome = function (coApplicantOneOtherPlannedIncome) {
                console.log("coApplicantOneOtherPlannedIncome", coApplicantOneOtherPlannedIncome);
                $scope.coApplicantOneOtherPlannedIncome = coApplicantOneOtherPlannedIncome;
            };
            $scope.getCoApplicantTwoAssetsSavings = function (coApplicantTwoSavings) {
                console.log("coApplicantTwoSavings", coApplicantTwoSavings);
                $scope.coApplicantTwoSavings = coApplicantTwoSavings;
            };
            $scope.getCoApplicantTwoLiquidableInvestment = function (coApplicantTwoLiquidableInvestment) {
                console.log("coApplicantTwoLiquidableInvestment", coApplicantTwoLiquidableInvestment);
                $scope.coApplicantTwoLiquidableInvestment = coApplicantTwoLiquidableInvestment;
            };
            $scope.getCoApplicantTwoOtherPlannedIncome = function (coApplicantTwoOtherPlannedIncome) {
                console.log("coApplicantTwoOtherPlannedIncome", coApplicantTwoOtherPlannedIncome);
                $scope.coApplicantTwoOtherPlannedIncome = coApplicantTwoOtherPlannedIncome;
            };
        })
        .controller('LoanEligibilityController', function ($scope, IncomeSlabService, BankService) {
            $scope.yesnoCheck = function () {
                if ($scope.primaryIncomeType === 'monthlyIncome') {
                    $scope.employee = true;
                    $scope.business = false;
                } else {
                    $scope.employee = false;
                    $scope.business = true;
                }
            };
            $scope.liabilitiesCheck = function () {
                if ($scope.liabilitiesType === 'yes') {
                    $scope.yes = true;
                    $scope.no = false;
                } else {
                    $scope.yes = false;
                    $scope.no = true;
                }
            };
            $scope.totalIncome = 0;
            $scope.calculateTotalIncome = function () {
                var totalPrimaryIncome = 0;
                var totalAdditionalIncome = 0;
                if ($scope.primaryIncomeType === 'monthlyIncome') {
                    totalPrimaryIncome = $scope.PrimaryAppPrimaryIncome * 12;
                } else {
                    totalPrimaryIncome = $scope.primaryAnnualIncome;
                }
                if ($scope.additionalIncomeType === 'monthlyIncome') {
                    totalAdditionalIncome = $scope.additionalIncome * 12;
                } else {
                    totalAdditionalIncome = $scope.additionalIncome;
                }
                $scope.totalIncome = totalPrimaryIncome + totalAdditionalIncome;
            };
//            $scope.setBank = function (bank) {
//                if (bank !== null) {
//                    $scope.bankId = bank.id;
//                    console.log("bank ki id", $scope.bankId);
//                    $scope.bank = bank;
//                } else {
//                    $scope.bankId = null;
//                    $scope.bank = null;
//                }
//
//
//                $scope.incomeSlabs = IncomeSlabService.findByBankId({
//                    'bankId': $scope.bankId
//                }, function () {
//
//                    angular.forEach($scope.incomeSlabs, function (incomeSlab) {
//                        console.log("incomeSlab", incomeSlab);
//                        console.log("$scope.totalIncome", $scope.totalIncome);
////                        $scope.liabilities = 0;
//                        console.log("$scope.liabilities", $scope.liabilities);
//                        if (angular.isNumber($scope.liabilities)) {
//                            if ($scope.totalIncome >= incomeSlab.minRange && $scope.totalIncome <= incomeSlab.maxRange) {
//                                $scope.emi = (($scope.totalIncome * (incomeSlab.percentageDeduction / 100)) / 12) - $scope.liabilities;
//
//                            }
//                        } else {
//                            if ($scope.totalIncome >= incomeSlab.minRange && $scope.totalIncome <= incomeSlab.maxRange) {
//                                $scope.emi = ($scope.totalIncome * (incomeSlab.percentageDeduction / 100)) / 12;
//                            }
//                            ;
//                        }
//                        ;
////                        if ($scope.totalIncome >= incomeSlab.minRange && $scope.totalIncome <= incomeSlab.maxRange) {
////                            $scope.emi = (($scope.totalIncome * (incomeSlab.percentageDeduction / 100)) / 12) - $scope.liabilities;
////                        }
////                        ;
//
//                    });
//
//                    console.log("$scope.emi", $scope.emi);
//                });
//
//
//            };

            $scope.totalExpenditure = function () {
//                    $scope.result=parseInt(primaryIncome) + parseInt(additionalIncome);
//                $scope.expenditure = $scope.childEducation + $scope.marriage + $scope.vehicle;




                if (angular.isNumber($scope.childEducation || $scope.marriage || $scope.vehicle)) {
                    $scope.expenditure = $scope.childEducation + $scope.marriage + $scope.vehicle;
                    console.log("expenditure", $scope.expenditure);
                } else {
                    $scope.expenditure = "No Expenditure";
                }


            };
            $scope.totalContribution = function () {
//                    $scope.result=parseInt(primaryIncome) + parseInt(additionalIncome);
//                $scope.expenditure = $scope.childEducation + $scope.marriage + $scope.vehicle;




                if (angular.isNumber($scope.saving || $scope.liquid || $scope.retirement)) {
                    $scope.contribution = $scope.saving + $scope.liquid + $scope.retirement;
                    console.log("contribution", $scope.contribution);
                } else {
                    $scope.contribution = "No contribution";
                }


            };
//            $scope.bStage = 0;
//            $scope.bStage= $scope.totalIncome * (0.5);
//            console.log("bstage",$scope.bStage );

        });


