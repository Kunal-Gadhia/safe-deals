angular.module("safedeals.states.agent", [])
        .config(function ($stateProvider, templateRoot) {
            $stateProvider.state('admin.masters_agent', {
                'url': '/agent_master?offset',
                'templateUrl': templateRoot + '/masters/agent/list.html',
                'controller': 'AgentListController'
            });
            $stateProvider.state('admin.masters_agent.add', {
                'url': '/add',
                'templateUrl': templateRoot + '/masters/agent/form.html',
                'controller': 'AgentAddController'
            });
            $stateProvider.state('admin.masters_agent.edit', {
                'url': '/:agentId/edit',
                'templateUrl': templateRoot + '/masters/agent/form.html',
                'controller': 'AgentEditController'
            });
            $stateProvider.state('admin.masters_agent.delete', {
                'url': '/:agentId/delete',
                'templateUrl': templateRoot + '/masters/agent/delete.html',
                'controller': 'AgentDeleteController'
            });
        })
        .controller('AgentListController', function (AgentService, AgencyService, $scope, $stateParams, $state, paginationLimit) {
            if (
                    $stateParams.offset === undefined ||
                    isNaN($stateParams.offset) ||
                    new Number($stateParams.offset) < 0)
            {
                $scope.currentOffset = 0;
            } else {
                $scope.currentOffset = new Number($stateParams.offset);
            }

            $scope.nextOffset = $scope.currentOffset + 5;

            $scope.nextAgents = AgentService.query({
                'offset': $scope.nextOffset
            });

            $scope.agents = AgentService.query({
                'offset': $scope.currentOffset
            }
            , function (agents) {
                angular.forEach(agents, function (agent) {

                    agent.agency = AgencyService.get({
                        'id': agent.agencyId
                    });

                });
            });
            //console.log("$scope.franchises" , $scope.franchises);

            $scope.nextPage = function () {
                $scope.currentOffset += paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
            $scope.previousPage = function () {
                if ($scope.currentOffset <= 0) {
                    return;
                }
                $scope.currentOffset -= paginationLimit;
                $state.go(".", {'offset': $scope.currentOffset}, {'reload': true});
            };
        })
        .controller('AgentAddController', function (AgentService, AgencyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAgent = {};
            $scope.agencies = AgencyService.findAllAgencies();

            //console.log("hiiiiii",$scope.locationAreas);
            $scope.setAgency = function (agency) {
                //console.log("xyz", city);
                $scope.editableAgent.agencyId = agency.id;
                $scope.editableAgent.agency = agency;
                //console.log("$scope.editableFranchise.city ", $scope.editableFranchise.city);
            };

            $scope.saveAgent = function (agent) {
                //console.log("franchise :" + franchise);
                AgentService.save(agent, function () {
                    $state.go('admin.masters_agent', null, {'reload': true});
                });
            };
            $scope.$watch('editableAgent.name', function (name) {
                console.log("Name :" + name);
                AgentService.findByName({'name': name}).$promise.catch(function (response) {
                    console.log("Response :%O", response);
                    if (response.status === 500) {
                        $scope.editableAgent.repeatName = false;
                    } else if (response.status === 404) {
                        $scope.editableAgent.repeatName = false;
                    } else if (response.status === 400) {
                        $scope.editableAgent.repeatName = false;
                    }
                }).then(function (agent) {
                    if (agent.name !== null) {
                        $scope.editableAgent.repeatName = true;
                    }
                    ;
                });
            });
        })


        .controller('AgentEditController', function (AgentService, AgencyService, $scope, $stateParams, $state, paginationLimit) {
            $scope.agencies = AgencyService.findAllAgencies();


            $scope.editableAgent = AgentService.get({'id': $stateParams.agentId}, function () {

                $scope.editableAgent.agency = AgencyService.get({
                    id: $scope.editableAgent.agencyId
                });
            });


            $scope.setAgency = function (agency) {
                $scope.editableAgent.agencyId = agency.id;
                $scope.editableAgent.agency = agency;
            };



            $scope.saveAgent = function (agent) {
                agent.$save(function () {
                    $state.go('admin.masters_agent', null, {'reload': true});
                });
            };
        })
        .controller('AgentDeleteController', function (AgentService, $scope, $stateParams, $state, paginationLimit) {
            $scope.editableAgent = AgentService.get({'id': $stateParams.agentId});
            $scope.deleteAgent = function (agent) {
                agent.$delete(function () {
                    $state.go('admin.masters_agent', null, {'reload': true});
                });
            };
        }); /*AmenityDetailService, LocationService, CityService, */







