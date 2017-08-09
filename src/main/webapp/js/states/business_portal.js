angular.module("safedeals.states.business_portal", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.business_portal', {
                'url': '/business_portal/:userId/:companyName',
                'templateUrl': templateRoot + '/businessassociate/business_menu.html',
                'controller': 'BusinessPortalController'
            });
        })

        .controller('BusinessPortalController', function ($scope, $stateParams, BusinessService, UserService) {
            console.log("Hello Into business Portal");
            console.log("Are we getting stateparams here?? :%O", $stateParams);

            $scope.businessPortals = BusinessService.query({
                'offset': $scope.currentOffset
            }
            , function () {
                angular.forEach($scope.businessPortals, function (businessPortal) {
                    businessPortal.user = UserService.get({
                        'id': businessPortal.userId,
                        'companyName': businessPortal.nameOfCompany
                    });
                });
            });
        });