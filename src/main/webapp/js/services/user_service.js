angular.module("safedeals.services.user", []);
angular.module("safedeals.services.user")
        .factory('UserService', function ($resource, restRoot, contextPath) {
            console.log("Rest Root :" + restRoot);
            console.log("Context Path :" + contextPath);
            return $resource(restRoot + '/user/:id', {'id': '@id'}, {
                'login': {
                    'method': 'POST',
                    'url': contextPath + '/access/login',
                    'params': {
                        'username': '@username',
                        'password': '@password'
                    }
                },
                'logout': {
                    'method': 'POST',
                    'url': contextPath + '/access/logout'
                },
                'findByNameLike': {
                    'method': 'GET',
                    'url': restRoot + '/user/find/user_like',
                    'params': {
                        'username': '@username'
                    },
                    'isArray': true
                },
                'findByUsername': {
                    'method': 'GET',
                    'url': restRoot + '/user/find/username',
                    'params': {
                        'username': '@username'
                    },
                    'isArray': false
                },
                'findUnapprovedUser': {
                    'method': 'GET',
                    'url': restRoot + '/user/find/unapproved_user',
                    'isArray': true
                },
                'countUnapprovedUser': {
                    'method': 'GET',
                    'url': restRoot + '/user/count/unapproved_user',
                    'isArray': false
                }
            });
        });


